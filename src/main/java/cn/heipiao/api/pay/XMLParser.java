package cn.heipiao.api.pay;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * xml解析类
 * 
 * @author wzw
 * @date 2016年7月18日
 * @version 1.0
 */
public class XMLParser {
	
    //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
    public static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static Map<String,String> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {
    	
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, String> map = new TreeMap<String, String>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;
    }

    private static InputStream getStringStream(String sInputString) throws UnsupportedEncodingException {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes("utf-8"));
        }
        return tInputStringStream;
    }

    /**
     * map转xml
     * @param map
     * @return
     */
    public static String converterMapToXml(Map<String,Object> map){
    	StringBuffer sb = new StringBuffer("<xml>");
    	for (Entry<String, Object> ent : map.entrySet()) {
    		if(ent.getValue() == null)
    			continue;
    		sb.append("<" + ent.getKey() + "><![CDATA[" + ent.getValue() + "]]></" + ent.getKey() + ">\r\n");
		}
    	return sb.append("</xml>").toString();
    }
    
}
