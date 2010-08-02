package framework.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * 
 * Buffered Reader for reading Events from an XML file
 * 
 * @author Bart Tuts and Jelle Van Gompel
 */

public abstract class BufferedXMLEventReader {

	private final XStream xstream = new XStream(new PureJavaReflectionProvider(), new DomDriver());

	public BufferedXMLEventReader(String filename, String startDelimeter, String endDelimeter) throws Exception {
		parse(filename, startDelimeter, endDelimeter);
	}

	private void parse(String filename, String startDelimeter, String endDelimeter) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String strLine;
		String string = "";
		boolean startFound = false;
		while ((strLine = br.readLine()) != null) {
			if (strLine.equals(startDelimeter)) {
				if (startFound) {
					throw new IllegalStateException("parse exception: encountered startdelimiter two times");
				}
				startFound = true;
			} else if (strLine.equals(endDelimeter)) {
				if (!startFound) {
					throw new IllegalStateException("parse exception: encountered enddelimiter before startdelimiter");
				}
				Object object = xstream.fromXML(string);
				startFound = false;
				string = "";
				objectRead(object);
			} else {
				string += strLine;
			}
		}
	}

	protected abstract void objectRead(Object object);

}
