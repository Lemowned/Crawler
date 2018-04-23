# -*- coding: utf-8 -*-
from flask import Flask, request, flash, session, redirect
from apimultiprocessing import Manager
import apiutils
import apijson

app = Flask(__name__)
manager = Manager()

#########  Ciclo de vida del crawler  ##########



@app.route('/create', methods=['POST'])
def create():
    print "Invocado metodo create"
    if request.method=='POST':
        host = request.form['host']
        if host:     
            if host.endswith('/'):
                host = host[:-1]
            crawlId = apiutils.generateId(manager, host)
            via =  request.form['via']
            search = request.form['search']
            manager.create(crawlId, host, via, search)
            #return "Crawler Creado: " + crawlId
            return apijson.getScans()
        else:
            return "Necesito parametro host"


@app.route('/delete/<crawlid>', methods=['GET'])
def delete(crawlid):
	return "Status del crawler: " + crawlid


@app.route('/init/<crawlId>', methods=['GET'])
def init(crawlId):
    print "Invocado metodo init" 
    return manager.init(crawlId)



@app.route('/pause/<crawlId>', methods=['GET'])
def pause(crawlId):
    print "Invocado metodo pause"   
    return manager.pause(crawlId)



@app.route('/resume/<crawlId>', methods=['GET'])
def resume(crawlId):
    print "Invocado metodo resume"
    return manager.resume(crawlId)



@app.route('/stop/<crawlId>', methods=['GET'])
def stop(crawlId):
    print "Invocado metodo stop"
    return manager.stop(crawlId)



####### Otencion de los datos  ########


@app.route('/scans', methods=['GET'])
def get_status():
	return apijson.getScans()


@app.route('/root/<crawlId>', methods=['GET'])
def root(crawlId):
    print "Invocado metodo root"
    return apijson.getRoot(crawlId)

@app.route('/children', methods=['POST'])
def child():
    if request.method=='POST':
        crawlId = request.form['crawlId']
        if crawlId:     
            parent =  request.form['parent']
            if parent:
                print "Invocado metodo children con "+crawlId+" y "+parent
                return apijson.getChildren(crawlId, parent)
            else:
                return "Necesito parametro parent"
        else:
            return "Necesito parametro crawlId"
    return 

@app.route('/info', methods=['POST'])
def info():
    if request.method=='POST':
        crawlId = request.form['crawlId']
        if crawlId:     
            link =  request.form['link']
            if link:
                print "Invocado metodo getInfo con "+crawlId+" y "+link
                return apijson.getInfo(crawlId, link)
            else:
                return "Necesito parametro link"
        else:
            return "Necesito parametro crawlId"
    return 

@app.route('/updir', methods=['POST'])
def updir():
    if request.method=='POST':
        crawlId = request.form['crawlId']
        if crawlId:     
            parent =  request.form['parent']
            if parent:
                print "Invocado metodo upDir con "+crawlId+" y "+parent
                return apijson.upDir(crawlId, parent)
            else:
                return "Necesito parametro parent"
        else:
            return "Necesito parametro crawlId"
    return 

if __name__ == '__main__':
    app.run(debug=True, threaded=True, host='0.0.0.0', port=80)
