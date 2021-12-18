package ch.hslu.swde.wda.business.reader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ReaderUtil {
    public static Document parseXmlFromString(String xmlString) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes());
        Document document = builder.parse(inputStream);
        return document;
    }
    
    public static String getXMLResponse(final String RESTurl) {
    	StringBuilder response = new StringBuilder("");   	
        try {
            URL url = new URL(RESTurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                response.append(output);
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in getXMLResponse:- " + e);
        }
        return response.toString();
    }
}
