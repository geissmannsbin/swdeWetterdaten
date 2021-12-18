package ch.hslu.swde.wda.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;



class UITest {

	private static OutputStream out = null;
    private static PipedInputStream pin = null;
    private static PipedOutputStream pout = null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		 out = new ByteArrayOutputStream();

	        pin = new PipedInputStream();
	        pout = new PipedOutputStream();
	        PrintStream ps = new PrintStream(out);

	        System.setOut(ps);
	        pout.connect(pin);
	        System.setIn(pin);
	    }
	

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}
	
	@Disabled
	void testEingabeOrtschaftKorrekt() {
		try {
			pout.write("Baden\n".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String test = UI.eingabeOrtschaft();
			
		
		assertEquals("Baden", test);
	}
	
	@Disabled
	void testEingabeOrtschaftKorrektKlein() {
		try {
			pout.write("baden\n".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String test = UI.eingabeOrtschaft();
			
		
		assertEquals("Baden", test);
	}
	
	
	@Disabled
	void testStartDatumIsValidKorrekt() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormatter.parse("2020-08-11");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pout.write("2020-08-11\n".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date test = UI.startDatumIsValid();
			
		
		assertEquals(date, test);
	}
	
	@Disabled
	void testEndDatumIsValidKorrekt() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date date2 = null;
		try {
			date = dateFormatter.parse("2020-08-12");
			date2 = dateFormatter.parse("2020-08-13");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pout.write("2020-08-13\n".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date test = UI.endDatumIsValid(date);
			
		
		assertEquals(date2, test);
	}

}
