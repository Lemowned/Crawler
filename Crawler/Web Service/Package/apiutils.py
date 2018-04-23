# -*- coding: utf-8 -*-
from base64 import b64encode
from os import urandom
import pymongo
import time
import hashlib
from urlparse import urlparse, urlunparse

f= open ("file-debug.txt", 'w');
  
def generateId(manager, host):
    key = hashlib.sha224(host.encode('utf-8')+":"+str(time.time())+":"+str(urandom(9))).hexdigest()
    return key

def getNextLink(db, crawlId):
    query = db.crawlerDB.find({"crawlId":crawlId ,"finished": "False"}).sort("_id").limit(1)

    return query

def saveNewCode(db, crawlId, mongoId, link, code):
	query = db.crawlerCode.insert({'crawlId':crawlId, 'parent':getParentFromLink(link),
		'host':getHostFromLink(link), 'link':link, 'code':code, 'extracted':"False", 'analyzed':"False"})
    
	db.crawlerDB.update({"_id":mongoId}, { '$set': {"finished":"True"}})  

	return 0

def rejectCode(db, crawlId, mongoId, link, code):
    
	db.crawlerDB.update({"_id":mongoId}, { '$set': {"finished":"True"}})  

	return 0

def getNextCodeExtract(db, crawlId):
    query = db.crawlerCode.find({"crawlId":crawlId ,"extracted": "False"}).sort("_id").limit(1)
    
    return query

def saveDirectoryPaths(db, crawlId, mongoId, link, host):
	direction = urlparse(link)

	path = direction.path


	dirs = path.split("/")

	if len(dirs) > 1:
		interm = getHostFromLink(link)
		print "INTERM - " + interm
		for i in range(1, len(dirs)):
			directory=dirs[i]
			newLink = interm + "/" + directory
			if not linkVisited(db,crawlId,newLink) and (getHostFromLink(link) == host):
				db.crawlerDB.insert({'crawlId':crawlId, 'parent':getParentFromLink(newLink),
					'host':getHostFromLink(link), 'link':newLink, 'finished':"False"})


				print "###################################################"
				print "Almaceno en BD el intermedio " + newLink
				print "###################################################"

				db.crawlerData.insert({'crawlId':crawlId, 'parent':getParentFromLink(newLink),
				'host':getHostFromLink(newLink), 'link':newLink,'type':"dir", 'path':getPathFromLink(newLink)})

				db.crawlerData.update( {"crawlId":crawlId, "link": newLink}, {"$pushAll" : {'internalLinks' : []}}, False, True)
				db.crawlerData.update( {"crawlId":crawlId, "link": newLink}, {"$pushAll" : {'externalLinks' : []}}, False, True)
				db.crawlerData.update( {"crawlId":crawlId, "link": newLink}, {"$pushAll" : {'forms' : []}}, False, True)
				db.crawlerData.update( {"crawlId":crawlId, "link": newLink}, {"$pushAll" : {'wordlist' : []}}, False, True)


				print "Directorio intermedio guardado:"
				print "Link: " + newLink
				print "Padre: " +getParentFromLink(newLink)+"\n"
			interm = newLink




def saveNewLinkList(db, crawlId, mongoId, link, linkList):
	host = getHostFromLink(link)
	for link in linkList:
		if (not linkVisited(db, crawlId, link)) and (getHostFromLink(link) == host):
			query = db.crawlerDB.insert({'crawlId':crawlId, 'parent':getParentFromLink(link),
				'host':getHostFromLink(link), 'link':link, 'finished':"False"})


	db.crawlerCode.update({"_id":mongoId}, { '$set': {"extracted":"True"}}) 

	return 0

def saveData(db, crawlId, mongoId, linkInitial, linkList, forms, wordlist, code):
	host = getHostFromLink(linkInitial)
	for link in linkList:

		saveDirectoryPaths(db, crawlId, mongoId, link, host)
		if (not linkVisited(db, crawlId, link)) and (getHostFromLink(link) == host):
			query = db.crawlerDB.insert({'crawlId':crawlId, 'parent':getParentFromLink(link),
				'host':getHostFromLink(link), 'link':link, 'finished':"False"})

	db.crawlerCode.update({"_id":mongoId}, { '$set': {"extracted":"True"}})

	print "CCOMPRUEBO SI ESTA ALMACENADO YA"


	if dataSaved(db, crawlId, linkInitial):

		print "ESTA ALMACENADO, VOY A ACTUALIZAR"

		db.crawlerData.update({"crawlId":crawlId, "link":linkInitial}, { '$set': {'type':"file", 'status':"200"}}) 
		
	else:
		db.crawlerData.insert({'crawlId':crawlId, 'parent':getParentFromLink(linkInitial),
				'host':getHostFromLink(linkInitial), 'link':linkInitial, 'type':"file", 'status':"200", 'path':getPathFromLink(linkInitial)})

	internalLinks = []
	externalLinks = []

	for link in linkList:
		if getHostFromLink(link) == host:
			internalLinks.append(link.strip())
		else:
			externalLinks.append(link.strip())


	db.crawlerData.update( {"crawlId":crawlId, "link":linkInitial}, {"$pushAll" : {'externalLinks' : externalLinks}}, False, True)
	db.crawlerData.update( {"crawlId":crawlId, "link":linkInitial}, {"$pushAll" : {'internalLinks' : internalLinks}}, False, True)
	db.crawlerData.update( {"crawlId":crawlId, "link":linkInitial}, {"$pushAll" : {'forms' : forms}}, False, True)
	db.crawlerData.update( {"crawlId":crawlId, "link":linkInitial}, {"$pushAll" : {'wordlist' : wordlist}}, False, True)



	f.write("VOY A GUARDAR DATOS\n\n");
	print "-----------------------------"
	f.write("LINK: "+linkInitial+"\n\n");
	print linkList
	print forms
	print wordlist
	print "............................."

	

	return 0

def saveFailData(db, crawlId, link, status):

	path = urlparse(link)[2]

	if '.' in path:

		db.crawlerData.update({"crawlId":crawlId, "link":link}, { '$set': {'type':"file", 'status':status}}) 

	else:
		db.crawlerData.update({"crawlId":crawlId, "link":link}, { '$set': {'type':"dir", 'status':status}}) 


def linkVisited(db, crawlId, link):
	query = db.crawlerDB.find({"crawlId":crawlId ,"link": link})

	return query.count() > 0

def dataSaved(db, crawlId, link):
	query = db.crawlerData.find({"crawlId":crawlId ,"link": link})

	return query.count() > 0


def getParentFromLink(link):
	direction = urlparse(link)
	dirList = direction.path.split("/")
	parent = ""
	for direct in dirList[1:-1]:
		parent += "/"+direct
	if len(parent)==0:
		return getHostFromLink(link)+"/"
	return getHostFromLink(link)+parent

def getHostFromLink(link):
	direction = urlparse(link)
	return direction.scheme+"://"+direction.netloc

def getPathFromLink(link):
	direction = urlparse(link)
	path = direction[2]
	if path=='':
		return '/'
	else:
		ultimo = path.split('/')
		return '/'+ultimo[-1]



def correctExtension(link):

	incorrectExtension = ["png","jpg","css","js","pdf","doc", "docx", "svg"]
	direction = urlparse(link)

	for i in incorrectExtension:
		if (direction.path.endswith(i)):
			return False
	return True
