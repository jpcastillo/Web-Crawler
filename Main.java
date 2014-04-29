import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

/*
	Class to be used for writing to files. This class overwrites
	existing files and create non-existing files
*/
class HTMLWriter {
	PrintWriter writer;
	String file;
	public HTMLWriter(String _file) {
		file = _file;
		try {
			writer = new PrintWriter(file,"UTF-8");
		}
		catch(FileNotFoundException fnfe) {
			System.out.println("File not found: " + fnfe.getMessage());
		}
		catch(UnsupportedEncodingException uee) {
			System.out.println("Unsupported Encoding Exception: " + uee.getMessage());
		}
	}
	public void close() {
		writer.close();
	}
	public void write(String str) {
		writer.println(str);
	}
}

/*
	Class for crawling the web
*/
class Crawler {
  	String seedfile, outputdir;

  	public Crawler(String _seedfile, String _outputdir) {
  		seedfile=_seedfile;
  		outputdir=_outputdir;
  	}
	public void start() {
		try {
			URL url = new URL("http://www.ucr.edu");
			URLConnection connection = url.openConnection();
			BufferedReader buffin = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String html = "", line;
			while ((line = buffin.readLine()) != null) {
			   html += line + System.getProperty("line.separator");
			}
			//System.out.println(html);
		  	HTMLWriter writer = new HTMLWriter(outputdir+"/"+"www.ucr.edu");
		  	writer.write(html);
		  	writer.close();
		}
		catch(MalformedURLException mue) {
			System.out.println("Bad URL: "+mue.getMessage());
		}
		catch(IOException ioe) {
			System.out.println("IOException: "+ioe.getMessage());
		}
	}
}

/*
	This class is set up to append strings to files. Perfect for
	keeping a history of crawled pages.
*/
class Logger {
	BufferedWriter fout;
	File dir;
	String abspath, file;
	FileWriter fstream;

	public Logger(String _file) {
		file = _file;
		try {
			dir = new File(".");
			abspath = dir.getCanonicalPath() + File.separator + file;
			fstream = new FileWriter(abspath, true);
			fout = new BufferedWriter(fstream);
		}
		catch(IOException ioe) {
			System.out.println("IOException: "+ioe.getMessage());
		}
	}
	public void close() {
		try {
			fout.close();
		}
		catch(IOException ioe) {
			System.out.println("IOException: "+ioe.getMessage());
		}
	}
	public void write(String str) {
		try {
			fout.write(str);
			fout.newLine();
		}
		catch(IOException ioe) {
			System.out.println("IOException: "+ioe.getMessage());
		}
	}
}

/*
	Main class
*/
public class Main {
	public static void main(String[] args) {
		/*
		System.out.println("Number of Args is "+args.length+" and they are:");
		for (String s: args) {
          System.out.println(s);
      	}
      	*/
      	String crawlerlog = "filescrawled.txt";
      	String seedfile = args[0];
      	int numpages = Integer.parseInt(args[1]);
      	int hopsaway = Integer.parseInt(args[2]);
      	String outputdir = args[3];

      	String pme = "Seed File: "+seedfile+"\nNumber of Pages: "+numpages+"\nNumber of Hops Away: "+hopsaway+"\nOutput Directory: "+outputdir;
      	System.out.println(pme);

      	Logger log = new Logger(crawlerlog);
      	log.write(pme);

      	Crawler spidey = new Crawler(seedfile,outputdir);
      	spidey.start();
	}
}



