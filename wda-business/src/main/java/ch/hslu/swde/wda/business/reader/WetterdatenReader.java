package ch.hslu.swde.wda.business.reader;

import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetterdatensatz;
import ch.hslu.swde.wda.persister.impl.*;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class WetterdatenReader {
	static JPAPersister persister = new JPAPersister();

	public WetterdatenReader() {
	}

	public boolean fetchWetterdaten(Date dateFrom) {
		try {
			Calendar startdate = Calendar.getInstance();
			startdate.setTime(dateFrom);
			List<Ortschaft> ortschaften = persister.alleOrtschaften();
			for(Ortschaft ortschaft : ortschaften) {
				if (fetchWetterDatenByOrtschaftName(ortschaft.getName(), startdate) != true) {
					return false;
				}
			} 
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean fetchWetterDatenByOrtschaftName(String ortschaftName, Calendar startdate) {
		try {
			String response = ReaderUtil.getXMLResponse(wetterdatensatzRESTUrl(ortschaftName, startdate));
			List<Wetterdatensatz> wetterdatensaetze;
			wetterdatensaetze = parseWetterdaten(response);
			if (wetterdatensaetze != null) {
				System.out.println(wetterdatensaetze.toString());
			} else {
				return false;
			}

			if (persister.speichernListeWetterdatensatz(wetterdatensaetze) != false) {
				System.out.println("success");
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String wetterdatensatzRESTUrl(String ortschaftName, Calendar startdate) {
		StringBuilder resultURL = new StringBuilder("");
		resultURL.append("http://swde.el.eee.intern:8080/weatherdata-provider/rest/weatherdata/");
		resultURL.append(ortschaftName);
		resultURL.append("/since?");
		resultURL.append("year=" + String.valueOf(startdate.get(Calendar.YEAR)));
		resultURL.append("&month=" + String.valueOf(startdate.get(Calendar.MONTH)));
		resultURL.append("&day=" + String.valueOf(startdate.get(Calendar.DAY_OF_MONTH)));
		return resultURL.toString();
	}

	private static List<Wetterdatensatz> parseWetterdaten(String XMLString) {
		List<Wetterdatensatz> wetterdatensaetze = new ArrayList<Wetterdatensatz>();
		Document doc;

		try {
			doc = ReaderUtil.parseXmlFromString(XMLString);
			NodeList wetterdatenList = doc.getElementsByTagName("weatherdata");

			for (int i = 0; i < wetterdatenList.getLength(); i++) {
				Element wetterdatenElement = (Element) wetterdatenList.item(i);

				NodeList dataChildren = wetterdatenElement.getElementsByTagName("data");
				String data = dataChildren.item(0).getTextContent();

				Wetterdatensatz wetterdatensatz = createWetterdatensatz(data);
				wetterdatensaetze.add(wetterdatensatz);
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wetterdatensaetze;
	}

	private static Wetterdatensatz createWetterdatensatz(String rawData) {
		String[] splittedRawData = rawData.split("#");
		Wetterdatensatz wetterdatensatz = null;

		// Date
		String[] splittedDate = splittedRawData[0].split(":", 2);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			for (String s : splittedRawData) {
				System.out.println(s);
			}

			Date date = dateFormatter.parse(splittedDate[1]);
			// City
			String[] splittedCity = splittedRawData[3].split(":");
			String cityName = splittedCity[1];
			
			String possibleSubString = "/";
			if (cityName.contains(possibleSubString)) {
				String[] splittedFranceTrap = cityName.split(possibleSubString);
				cityName = splittedFranceTrap[0];
			}
			
			// PLZ
			String[] splittedPLZ = splittedRawData[2].split(":");
			int cityPLZ = Integer.parseInt(splittedPLZ[1]); // Integer.valueOf(splittedPLZ[1]);

			// Temperature
			String[] splittedTemperature = splittedRawData[9].split(":");
			float temperature = Float.valueOf(splittedTemperature[1]).floatValue();

			// Humidity
			String[] splittedHumidity = splittedRawData[11].split(":");
			float humidity = Float.valueOf(splittedHumidity[1]).floatValue();

			// Pressure
			String[] splittedPressure = splittedRawData[10].split(":");
			float pressure = Float.valueOf(splittedPressure[1]).floatValue();

			Ortschaft ortschaft = persister.findeOrtschaftByName(cityName);

			System.out.println(ortschaft.toString());

			wetterdatensatz = new Wetterdatensatz(temperature, humidity, date, ortschaft, pressure);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wetterdatensatz;
	}

}
