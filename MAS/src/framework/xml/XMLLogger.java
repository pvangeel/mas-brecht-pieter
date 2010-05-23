package framework.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * Logs Objects to an XML file, using XStream to create an XML representation of the object
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public abstract class XMLLogger {

	private final XStream xstream = new XStream(new PureJavaReflectionProvider(), new DomDriver());

	private final int persistenceTreshold;
	private final File file;
	public static final String startDelimeter = "<xxxxxx>";
	public static final String endDelimeter = "</xxxxxx>";

	private LinkedList<Object> objects = new LinkedList<Object>();

	public XMLLogger(String filename, int persistenceTreshold) {
		if (filename == null || persistenceTreshold < 1) {
			throw new IllegalArgumentException();
		}
		filename += "_" + getDateString() + ".xml";
		this.persistenceTreshold = persistenceTreshold;
		file = new File(filename);
	}

	private String getDateString() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	protected void addObject(Object object) {
		objects.add(object);
		if (objects.size() > persistenceTreshold) {
			persistAll();
		}
	}

	protected void persistAll() {
		try {
			FileWriter w = new FileWriter(file, true);
			for (Object object : objects) {
				w.write(startDelimeter + "\n");
				w.write(xstream.toXML(object) + "\n");
				w.write(endDelimeter + "\n");
			}
			w.close();
			objects.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
