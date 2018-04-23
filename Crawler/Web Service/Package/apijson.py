# -*- coding: utf-8 -*-
from base64 import b64encode
from os import urandom
from pymongo import MongoClient
import pymongo
import time
import hashlib
from urlparse import urlparse, urlunparse
from bson import json_util


def getScans():
	client = MongoClient()
	db = client.crawlerDB
	query = db.crawlerScans.find().sort([("_id",pymongo.DESCENDING)])
	query = json_util.dumps(query)

	return query

def getRoot(crawlId):
	client = MongoClient()
	db = client.crawlerDB
	query = db.crawlerScans.find({"crawlId":crawlId}).sort("_id").limit(1)
	host = query[0]["host"]
	query = db.crawlerData.find({"crawlId":crawlId, "parent": host+"/"}).sort("_id")
	query = json_util.dumps(query)

	return query

def getChildren(crawlId, parent):
	client = MongoClient()
	db = client.crawlerDB
	query = db.crawlerData.find({"crawlId":crawlId, "parent": parent}, {"parent": 1,"crawlId": 1,"link": 1, "type": 1, "status": 1, "path": 1 }).sort("link")
	query = json_util.dumps(query)

	return query

def getInfo(crawlId, link):
	client = MongoClient()
	db = client.crawlerDB
	query = db.crawlerData.find({"crawlId":crawlId, "link": link}).sort("link")
	query = json_util.dumps(query)

	return query

def upDir(crawlId, link):

	client = MongoClient()
	db = client.crawlerDB
	print "LINK : "+link
	parent = db.crawlerData.find({"crawlId":crawlId, "link": link}).sort("_id")[0]["parent"]



	print "PARENT : "+parent
	return getChildren(crawlId, parent)