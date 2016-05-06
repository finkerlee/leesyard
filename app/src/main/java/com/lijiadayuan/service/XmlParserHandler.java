package com.lijiadayuan.service;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.lijiadayuan.model.CityModel;
import com.lijiadayuan.model.DistrictModel;
import com.lijiadayuan.model.ProvinceModel;

public class XmlParserHandler extends DefaultHandler {

	private static String currentTag = "";

	private static XmlParserHandler xmlParserHandler;


	/**
	 * 存储所有的解析对象
	 */
	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

	/**
	 * 私有化构造函数
	 */
	private XmlParserHandler() {

	}

	/**
	 * 获取当前唯一的实例
	 * @return
	 */
	public static XmlParserHandler getInstance(){
		if (null == xmlParserHandler)
			xmlParserHandler = new XmlParserHandler();
		return xmlParserHandler;
	}

	public List<ProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	ProvinceModel provinceModel = new ProvinceModel();
	CityModel cityModel = new CityModel();
	DistrictModel districtModel = new DistrictModel();

	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {

		System.out.println("localName:    "+localName);
		System.out.println("qName:      "+qName);

		// 当遇到开始标记的时候，调用这个方法
		if (qName.equals("Province")) {
			currentTag = "Province";
			provinceModel = new ProvinceModel();
			provinceModel.setId(attributes.getValue(0));
			provinceModel.setName(attributes.getValue(1));
			provinceModel.setCityList(new ArrayList<CityModel>());
		} else if (qName.equals("City")) {
			currentTag = "City";
			cityModel = new CityModel();
			cityModel.setId(attributes.getValue(0));
			cityModel.setName(attributes.getValue(1));
			cityModel.setDistrictList(new ArrayList<DistrictModel>());
		} else if (qName.equals("Area")) {
			System.out.println("start");
			currentTag = "Area";
			districtModel = new DistrictModel();
			districtModel.setId(attributes.getValue(0));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals("Area")) {
			System.out.println("end");
			cityModel.getDistrictList().add(districtModel);
		} else if (qName.equals("City")) {
			provinceModel.getCityList().add(cityModel);
		} else if (qName.equals("Province")) {
			provinceList.add(provinceModel);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (!"".equals(currentTag)) {
			if ("Area".equals(currentTag)) {
				System.out.println("reading");
				String data = new String(ch, start, length);
				if (!("".equals(data.trim()) || null == data))
					districtModel.setName(data);
			}
		}
	}

}
