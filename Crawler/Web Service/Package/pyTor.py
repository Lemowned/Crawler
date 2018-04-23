# -*- coding: utf-8 -*-
import requests
from bs4 import BeautifulSoup
from urlparse import urlparse, urlunparse
import re
import unicodedata
from selenium import webdriver
import apiutils
import json


stopwords_languages = ["en", "es"]
stopwords = []

for i in stopwords_languages:
	json_stopwords = requests.get("https://raw.githubusercontent.com/moewe-io/stopwords/master/dist/"+i+"/"+i+".json")
	#stopwords.append(json.loads(json_stopwords))
	for j in json.loads(json_stopwords.text):
		stopwords.append(j)
	
proxies = {
  "http": "http://127.0.0.1:8118",
  "https": "http://127.0.0.1:8118",
}

headers = {'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0'}


def visitTor(link):

	request = requests.get(link, proxies=proxies)
	status = str(request.status_code)
	soup = BeautifulSoup(request.text, "lxml")
	return [str(soup), status]

def getText(request):

	"""
	Agradecimientos a:
	http://guimi.net/blogs/hiparco/funcion-para-eliminar-acentos-en-python/
	http://programminghistorian.org/lessons/counting-frequencies
	https://raw.githubusercontent.com/moewe-io/stopwords/
	"""
	wordlist = []

	soup = BeautifulSoup(request, "lxml")

	for script in soup(["script", "style"]): # remove all javascript and stylesheet code
		script.extract()

	text = soup.get_text().lower()

	normalized = ''.join((c for c in unicodedata.normalize('NFD',unicode(text)) if unicodedata.category(c) != 'Mn'))
	

	wordList = re.compile(r'\W+', re.UNICODE).split(normalized)
	countList = [wordList.count(p) for p in wordList]

	diccionario = dict(zip(wordList, countList))

	tupleList = [(diccionario[key], key) for key in diccionario]
	tupleList.sort()
	tupleList.reverse()


	for i in tupleList[0:19]:

		if i[1] not in stopwords:

			wordlist.append({"num": i[0], "word": i[1]})

	return wordlist

def correctURLs (url):
	cambios = True
	url = str(url)
	while cambios:
		cambios=False
		if ('/../' in url):	
			url = url.replace("/../", "/")
			cambios=True
		if ('//' in url):	
			url = url.replace("//", "/")
			cambios=True

	url = url.replace("http:/", "http://")
	url = url.replace("https:/", "https://")
	return url
	

def getForms(request):
	soup = BeautifulSoup(request, "lxml")
	forms = []

	for form in soup.find_all("form"):
		aux = {}
		aux["action"] = form.get('action')
		inputs = form.findAll('input')
		aux["inputs"] = []
		for inp in inputs:
			aux["inputs"].append({"id": inp.get('id'), "name":inp.get('id')})
		forms.append(aux)

	return forms

def getLinks(request, link):
	soup = BeautifulSoup(request, "lxml")
	host = apiutils.getHostFromLink(link)
	links = []

	for link in soup.find_all(href=True):
		linkstr = link["href"]
		if urlparse(linkstr)[0] == '':
			if linkstr.startswith('/'):
				links.append(correctURLs(host+linkstr))
			else:
				links.append(correctURLs(host+"/"+linkstr))
		else:
			links.append(link["href"])

	for link in soup.find_all(src=True):
		linkstr = link["src"]
		if urlparse(linkstr)[0] == '':

			if linkstr.startswith('/'):
				links.append(correctURLs(host+linkstr))
			else:
				links.append(correctURLs(host+"/"+linkstr))
		else:
			links.append(link["src"])

	return links


def getHeaders(request):
	return request.headers

def getStatusCode(request):
	return request.status_code

def getEncoding(request):
	return reques.encoding



