package com.example.bbw.networktest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by bbw on 2017/6/25.
 */

public class ContentHandler extends DefaultHandler {

    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    private String nodeName;

    //获取两节点之间的内容
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if("id".equals(nodeName)){
            id.append(ch,start,length);
        }else if("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if ("version".equals(nodeName)){
            version.append(ch,start,length);
        }
    }

    //完成解析某节点调用
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if ("app".equals(nodeName)){
            Log.d("MainActivity","id is "+ id);
            Log.d("MainActivity","name is"+ name);
            Log.d("MainActivity","version is "+version);
        }
        id.setLength(0);
        name.setLength(0);
        version.setLength(0);
    }

    //开始XML解析调用
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        nodeName = localName;
    }

    //完成整个xml解析时调用
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    //开始xml解析调用
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }
}
