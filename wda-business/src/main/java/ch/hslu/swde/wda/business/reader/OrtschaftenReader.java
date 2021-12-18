package ch.hslu.swde.wda.business.reader;

import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.persister.impl.JPAPersister;

import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
import java.util.List;

public class OrtschaftenReader {

	static JPAPersister persister;

	public OrtschaftenReader() {
	}

	public boolean fetchOrtschaften() {
		try {
			String response = ReaderUtil
					.getXMLResponse("http://swde.el.eee.intern:8080/weatherdata-provider/rest/weatherdata/cities");
			System.out.println(response);

			List<Ortschaft> ortschaften;

			ortschaften = parseOrtschaften(response);
			if (ortschaften != null) {
				System.out.println(ortschaften.toString());
			} else {
				return false;
			}

			persister = new JPAPersister();
			if (persister.speichernListeOrtschaft(ortschaften) != false) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static List<Ortschaft> parseOrtschaften(String XMLString) {
		List<Ortschaft> ortschaften = new ArrayList<Ortschaft>();
		Document doc;
		try {
			doc = ReaderUtil.parseXmlFromString(XMLString);
			NodeList ortschaftenList = doc.getElementsByTagName("city");

			for (int i = 0; i < ortschaftenList.getLength(); i++) {
				Element ortschaftElement = (Element) ortschaftenList.item(i);

				NodeList plzChildren = ortschaftElement.getElementsByTagName("zip");
				int plz = Integer.parseInt(plzChildren.item(0).getTextContent());

				NodeList nameChildren = ortschaftElement.getElementsByTagName("name");
				String name = nameChildren.item(0).getTextContent();

				Ortschaft ortschaft = new Ortschaft(plz, name);
				ortschaften.add(ortschaft);
			}
			return ortschaften;

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
