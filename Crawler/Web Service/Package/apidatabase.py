from pymongo import MongoClient
from urlparse import urlparse, urlunparse
client = MongoClient()
crawlerDB = client.crawlerDB

def getNextLink(db, crawlId):
    query = db.crawlerDB.find({"crawlId":crawlId ,"finished": "False"}).sort("_id").limit(1)

    return query


def getNextCodeExtract(db, crawlId):
    query = db.crawlerCode.find({"crawlId":crawlId ,"extracted": "False"}).sort("_id").limit(1)
    
    print query.count()
    return query


def getNextCodeAnalyze(db, crawlId):
    query = db.crawlerCode.find({"crawlId":crawlId ,"analyzed": "False"}).sort("_id").limit(1)
    
    return query 


def saveNewCode(db, crawlId, mongoId, link, code):
	query = db.crawlerCode.insert({'crawlId':crawlId, 'parent':getParentFromLink(link),
		'host':getHostFromLink(link), 'link':link, 'code':code, 'extracted':"False", 'analyzed':"False"})
    
	db.crawlerDB.update({"_id":mongoId}, { '$set': {"finished":"True"}})  

	return 0


def saveNewLinkList(db, crawlId, mongoId, link, linkList):
	for link in linkList:
		if not linkVisited(db, crawlId, link):
			query = db.crawlerDB.insert({'crawlId':crawlId, 'parent':getParentFromLink(link),
				'host':getHostFromLink(link), 'link':link, 'finished':"False"})
	db.crawlerCode.update({"_id":mongoId}, { '$set': {"extracted":"True"}}) 

	return 0


def saveNewStatistics(db, crawlId, mongoId, link, statistics):
	query = db.crawlerStatistics.insert({'crawlId':crawlId, 'parent':getParentFromLink(link),
		'host':getHostFromLink(link), 'link':link, 'statistics':statistics, 'extracted':"False", 'analyzed':"False"})
	db.crawlerCode.update({"_id":mongoId}, { '$set': {"analyzed":"True"}})  

	return 0



def linkVisited(db, crawlId, link):
	query = db.crawlerDB.find({"crawlId":crawlId ,"link": link})

	return query.count() > 0


def getParentFromLink(link):
	direction = urlparse(link)
	dirList = direction[2].split("/")
	parent = ""
	for direct in dirList[1:-1]:
		parent += "/"+direct
	if len(parent)==0:
		return "/"
	return parent

def getHostFromLink(link):
	direction = urlparse(link)
	return direction[0]+"://"+direction[1]

