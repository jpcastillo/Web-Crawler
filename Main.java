import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;

/*
	Class to be used for writing to files, logging files crawled
*/
class Logger {
	PrintWriter log_writer;
	String logfile;
	public Logger(String _logfile) {
		logfile = _logfile;
		try {
			log_writer = new PrintWriter(logfile,"UTF-8");
		}
		catch(FileNotFoundException fnfe) {
			System.out.println("File not found: " + fnfe.getMessage());
		}
		catch(UnsupportedEncodingException uee) {
			System.out.println("Unsupported Encoding Exception: " + uee.getMessage());
		}
	}
	public void close() {
		log_writer.close();
	}
	public void write(String str) {
		log_writer.println(str);
	}
}

/*
	Main class
*/
public class Main {
	public static void main(String[] args) {
		/*System.out.println("Number of Args is "+args.length+" and they are:");
		for (String s: args) {
          System.out.println(s);
      	}*/
      	String crawlerlog = "filescrawled.txt";
      	String seedfile = args[0];
      	int numpages = Integer.parseInt(args[1]);
      	int hopsaway = Integer.parseInt(args[2]);
      	String outputdir = args[3];

      	String pme = "Seed File: "+seedfile+"\nNumber of Pages: "+numpages+"\nNumber of Hops Away: "+hopsaway+"\nOutput Directory: "+outputdir;
      	System.out.println(pme);

      	// Let's test the logging class and methods
      	Logger log = new Logger(crawlerlog);
      	log.write(pme);
      	log.close();
	}
}



