package ch.hslu.swde.wda.ui;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardOpenOption.CREATE_NEW;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import ch.hslu.swde.wda.business.api.BusinessLogik;
import ch.hslu.swde.wda.domain.Wetterdatensatz;


public class Export {
	
	public Export() {
		
	}

	
	/*For Testing purposes
	 * public static void main(String[] args) throws Exception {
		Export exp = new Export();
		BusinessLogik busi = new BusinessLogikImpl();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = dateFormatter.parse("2020-08-11");
		Date date2 = dateFormatter.parse("2020-08-15");
		//Map<String, Float> m = busi.a3("Rotkreuz",date1, date2);
		List<Wetterdatensatz> list = busi.a2("Rotkreuz",date1, date2);
		//String str = exp.mapFloatToJSON(m);
	
		
		
		String str = exp.listToJSON(list);
		exp.toFile(str, "json");
		System.out.println(str);
	}*/
	
	
	
	public void toFile(String input, String outputFormat) throws IOException {
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss");  
		String timestamp = dateFormat.format(date); 
		String file = System.getProperty("user.home") + File.separator + "Documents" +
				File.separator + "Wetterdatensatz_"+ timestamp +"." + outputFormat;
		
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file),Charset.forName("UTF-8"))));
		pw.println(input);
		pw.flush();
		pw.close();
		
	
		}
		
		
	public String mapWetterdatensatzToJSON(Map<String,Wetterdatensatz> m) {
		String jsonString=null;
		ObjectMapper mapper=new ObjectMapper();
		
		try {
			jsonString = mapper.writeValueAsString(m);
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		return jsonString;
			    
		
		
		
	}
	
	public String mapFloatToJSON(Map<String,Float> m) {
		String jsonString=null;
		ObjectMapper mapper=new ObjectMapper();
		
		try {
			jsonString = mapper.writeValueAsString(m);
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		return jsonString;
		
		
		
	}
	public String listToJSON(List<Wetterdatensatz> list) {
		String jsonString=null;
		ObjectMapper mapper=new ObjectMapper();
		
		try {
			jsonString = mapper.writeValueAsString(list);
			
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		return jsonString;
		
	}
	
}
