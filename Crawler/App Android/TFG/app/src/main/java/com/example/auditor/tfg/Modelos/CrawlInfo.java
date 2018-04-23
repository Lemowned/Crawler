package com.example.auditor.tfg.Modelos;

import java.util.Hashtable;



public class CrawlInfo {

    // Modelo para la información general de un objeto crawler

    private String crawlId, parent, host, path, link, type, status;
    private String [] internalLinks, externalLinks;
    private FormModel[] forms;
    private WordModel[] wordlist;

    public CrawlInfo(String crawlId, String parent, String host, String path, String link, String type,
                     String status, String[] internalLinks, String[] externalLinks, FormModel[] forms, WordModel[] wordlist) {
        this.crawlId = crawlId;
        this.parent = parent;
        this.host = host;
        this.path = path;
        this.link = link;
        this.type = type;
        this.status = status;
        this.internalLinks = internalLinks;
        this.externalLinks = externalLinks;
        this.forms = forms;
        this.wordlist = wordlist;
    }

    public String[] getInternalLinks() {
        return internalLinks;
    }

    public String[] getExternalLinks() {
        return externalLinks;
    }

    public FormModel[] getForms() {
        return forms;
    }

    public WordModel[] getWordlist() {
        return wordlist;
    }

    public String getLink() {
        return link;
    }
}
