# -*- coding: utf-8 -*-
import multiprocessing as mp
from pymongo import MongoClient
import apitor
import apiutils
import time
import pyTor



class Manager:
    
    workerList = {}
    client = MongoClient()
    crawlerDB = client.crawlerDB


    def __init__(self):
        self.workerList = {}

    def create(self, crawlId, host, via, search):

        

        if len(self.workerList) <50:
            worker = {}
            shared = mp.Value('i',0)
            worker['shared']= shared
            worker['visit']= mp.Process(target=self.visitPath, args=(crawlId, host, via, search, shared,))
            worker['extract']= mp.Process(target=self.extractFromCode, args=(crawlId, host, via, search, shared,))
            worker['analyze']= mp.Process(target=self.analyzeCode, args=(crawlId, host, via, search, shared,))
            self.workerList[crawlId] = worker

        else:
            return "Se ha llegado al numero maximo de escaneos"

        return self.crawlerDB.crawlerScans.insert({'crawlId':crawlId, 'host':host, 'status':'pendant'})

    def init(self, crawlId):
        if crawlId in self.workerList.keys():
            for p in self.workerList[crawlId].values()[1:]:
                p.start()
                print p
            self.crawlerDB.crawlerScans.update({"crawlId":crawlId}, { '$set': {'status':'running'}})
            return "Todo iniciado"
            
        else:
            return "No hay ningun crawl con ese identificador "+crawlId+str(self.workerList)
          
    def pause(self, crawlId):
        if crawlId in self.workerList.keys():
            self.workerList[crawlId]['shared'].value = 1 
            self.crawlerDB.crawlerScans.update({"crawlId":crawlId}, { '$set': {"status":"paused"}})
            return "El valor del entero compartido ahora es " + str(self.workerList[crawlId]['shared'].value)
            
        else:
            return "No hay ningun crawl con ese identificador "+crawlId+str(self.workerList)
	
    def resume(self, crawlId):
        if crawlId in self.workerList.keys():
            self.workerList[crawlId]['shared'].value = 0 
            self.crawlerDB.crawlerScans.update({"crawlId":crawlId}, { '$set': {"status":"running"}})
            return "El valor del entero compartido ahora es " + str(self.workerList[crawlId]['shared'].value)
            
        else:
            return "No hay ningun crawl con ese identificador "+crawlId+str(self.workerList)
    
    def stop(self, crawlId):
        if crawlId in self.workerList.keys():
            self.workerList[crawlId]['shared'].value = 2 
            self.crawlerDB.crawlerScans.update({"crawlId":crawlId}, { '$set': {"status":"stopped"}})
            return "El valor del entero compartido ahora es " + str(self.workerList[crawlId]['shared'].value)
            
        else:
            return "No hay ningun crawl con ese identificador "+crawlId+str(self.workerList)
    

    def visitPath(self, crawlId, host, via, search, shared):
        client = MongoClient()
        crawlerDB = client.crawlerDB
        crawlerDB.crawlerDB.insert({'crawlId':crawlId, 'parent':"/",
                'host':host, 'link':host, 'finished':"False"})
        i=0
        while 1:
            if (shared.value == 2):
                return 2
            else:
                
                newURL = apiutils.getNextLink(crawlerDB, crawlId)
                while((shared.value == 1) or (newURL.count() == 0)):
                    if (shared.value == 0):
                        newURL = apiutils.getNextLink(crawlerDB, crawlId)
                    time.sleep(2)  
                
                mongoId = newURL[0]["_id"]
                link = newURL[0]["link"]

                print "Voy a visitar: " + link
                visit = pyTor.visitTor(link)
                code = visit[0]
                status = visit[1]

                print status

                if (str(status)=="200"):
                    print "He obtenido el codigo de: " + link
                    apiutils.saveNewCode(crawlerDB, crawlId, mongoId, link, code)

                    print "He almacenado el codigo de: " + link
                else:
                    apiutils.rejectCode(crawlerDB, crawlId, mongoId, link, code)
                    apiutils.saveFailData(crawlerDB, crawlId, link, status)


                


                  

    def extractFromCode(self, crawlId, host, via, search, shared):
        client = MongoClient()
        crawlerDB = client.crawlerDB

        f = open('file-debug2.txt', 'w')
        while 1:
            if (shared.value == 2):
                return 2
            else:
                
                newCode = apiutils.getNextCodeExtract(crawlerDB, crawlId)
                while((shared.value == 1) or (newCode.count() == 0)):
                    if (shared.value == 0):
                        newCode = apiutils.getNextCodeExtract(crawlerDB, crawlId)
                    time.sleep(2)  
                

                mongoId = newCode[0]["_id"]
                code = newCode[0]["code"]
                link = newCode[0]["link"]

                links = []
                forms = []
                wordlist = []

                if (apiutils.correctExtension(link)):

                    f.write("LINK ANALIZADO: "+link+"\n\n")
                    print "Voy a analizar: " + link
                    links = pyTor.getLinks(code, link)
                    f.write("LINKS : \n")
                    f.write(str(links) + "\n")

                    forms = pyTor.getForms(code)
                    wordlist = pyTor.getText(code)



                print "He obtenido los enlaces de: " + link
                print links
                #apiutils.saveNewLinkList(crawlerDB, crawlId, mongoId, link, links, forms)
                apiutils.saveData(crawlerDB, crawlId, mongoId, link, links, forms, wordlist, code)

                print "He almacenado los links de: " + link
                #print links

        


