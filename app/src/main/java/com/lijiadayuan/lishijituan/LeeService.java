package com.lijiadayuan.lishijituan;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.lijiadayuan.service.XmlParserHandler;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zhaoyi on 16/5/5.
 */
public class LeeService extends IntentService {
    public LeeService() {
        super("com.lenovo.robin.test.MyIntentService");
    }

    public LeeService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("LeeService","wo lai le ");
        try {
            InputStream input = getAssets().open("pca.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = XmlParserHandler.getInstance();
            parser.parse(input, handler);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
