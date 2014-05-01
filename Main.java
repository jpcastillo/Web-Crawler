//import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
//import java.io.UnsupportedEncodingException;
//import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.net.MalformedURLException;
//import java.io.IOException;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.BufferedWriter;
import java.util.Arrays;
import java.util.regex.*;
import java.io.*;

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
  	String good_gtld = "edu";//generic top level domains
  	String good_files = "htm";

  	public Crawler(String _seedfile, String _outputdir) {
  		seedfile=_seedfile;
  		outputdir=_outputdir;
  	}
	public void fetchURL(String target_url) {
		try {
			//URL String Requires protcol! HTTPS/HTTP
			URL url = new URL(target_url);
			URLConnection connection = url.openConnection();
			BufferedReader buffin = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String html_src = "", cur_line;
			while ((cur_line = buffin.readLine()) != null) {
			   html_src += cur_line + System.getProperty("line.separator");
			}

			String url_str = url.getHost() + url.getPath();

			//Remove all illegal filename characters and replace with underscore
		  	HTMLWriter writer = new HTMLWriter( outputdir+"/"+url_str.replaceAll("[^a-zA-Z0-9\\.\\-\\_]","_") );
		  	writer.write(html_src);
		  	writer.close();

			/* 
				Some testing of Java URL methods to learn what they do 
				and how i may use them.
			*/
			/*
			System.out.println("URL is " + url.toString());
			System.out.println("protocol is "
			                        + url.getProtocol());
			System.out.println("authority is "
			                        + url.getAuthority());
			System.out.println("file name is " + url.getFile());
			System.out.println("host is " + url.getHost());
			System.out.println("path is " + url.getPath());
			System.out.println("port is " + url.getPort());
			System.out.println("default port is "
			                       + url.getDefaultPort());
			System.out.println("query is " + url.getQuery());
			System.out.println("ref is " + url.getRef());
			*/
		  	getLinksFromPage(html_src,url_str);
		}
		catch(MalformedURLException mue) {
			System.out.println("Bad URL: "+mue.getMessage());
		}
		catch(IOException ioe) {
			System.out.println("IOException: "+ioe.getMessage());
		}
	}
	public String processURL(String crawled, String parent) {
		/*  -- Cases of crawled URL --
			has protocol and domain name
			has protocol but no domain name
			no protocol but has domain name
			no protocol and no domain name

			Need to implement check on file extensions. Currently
				incomplete. Need more rigorous checks.
			Also add support for listings of good gTLDs and file
				extensions. Currently only supports one of each.
		*/
		if (crawled.length()==0||parent.length()==0) {
			return "";
		}
		boolean has_domain_name = Pattern.matches("^(.*)(\\.){1}"+good_gtld+".*$",crawled);//hmmmm
		boolean has_protocol = Pattern.matches("^(https?://){1}.*$",crawled);

		if (has_protocol) {
			if (has_domain_name) {
				return crawled;
			}
			else {
				//protocol but no domain name. probably relative path.
				//let's strip protocol and pass to generateURL
				String tmp = crawled.replaceAll("https?://","");
				//System.out.println("tmp: "+tmp);
				return fixURL(tmp,parent);
			}
		}
		else {
			if (has_domain_name) {
				//URL is mostly good. just needs protocol prepended so that
				//we may fetch the html file without error
				crawled = "http://"+crawled;
				return crawled;
			}
			else {
				//probably a relative path. let's pass to generateURL
				return fixURL(crawled,parent);
			}
		}
	}
	public String fixURL(String crawled, String parent) {
		//need to handle relative directories "../"
		//need to handle document root "/"
		//reminder that excessive "../" will halt at web root
		//explode parent into array delimited by "/"?
		boolean valid_parent = Pattern.matches("^((https?://)|(www)){1}.*$", parent);
		if (!valid_parent) {
			return "";
		}

		int numDotDot = countOccurrencesOf("\\.\\./",crawled);
		int numDocRoot = countOccurrencesOf("^/",crawled);
		//crawled page is an html file in parent directory
		if (numDotDot==0&&numDocRoot==0&&Pattern.matches("^(.*)(\\.){1}"+good_files+"(l?)$",crawled)) {
			int slash = parent.lastIndexOf("/");
			String sub = parent.substring(0,slash);
			return sub+"/"+crawled;
		}

		String[] splitpar = parent.split("/").clone();

		//Need to find which index of array the domain name is at
		int domain_index = -1;
		boolean parent_is_file = false;
		for (int i = 0; i < splitpar.length; ++i) {
			//if parent element matches pattern of domain name save index
			if ( Pattern.matches("^(.*)(\\.){1}"+good_gtld+"$",splitpar[i]) ) {
				domain_index = i;
			}
			//check to see if parent url a directory or absolute path to file
			if (Pattern.matches("^(.*)(\\.){1}"+good_files+"(l?)$",splitpar[i])) {
				parent_is_file = true;
			}
		}

		int allowableParentUps = (parent_is_file) ? splitpar.length - domain_index - 2 : splitpar.length - domain_index - 1;
		//System.out.println(Arrays.toString(splitpar));
		String newUrl = "";
		if (numDocRoot==0&&numDotDot>0) {
			int control = 0;
			if (numDotDot > allowableParentUps) {
				control = domain_index+1;
			}
			else {
				control = (parent_is_file) ? splitpar.length-numDotDot-1 : splitpar.length-numDotDot;
			}
			for (int i = 0; i < control; ++i) {
				//empty element is double slash
				if (splitpar[i].length()==0) {
					newUrl+="//";
				}
				if (i>domain_index) {
					newUrl+="/";
				}
				newUrl += splitpar[i];
			}
			//strip ../ from crawled url and append everything after to new url
			String tmp = crawled.replaceAll("\\.\\./","");
			newUrl+="/"+tmp;
		}
		else if(numDocRoot>0&&numDotDot==0) {
			//we encountered a document root path so let us build new url
			//from parent url up to domain name
			for (int i = 0; i < domain_index+1; ++i) {
				//empty element is double slash
				if (splitpar[i].length()==0) {
					newUrl+="//";
				}
				if (i>domain_index) {
					newUrl+="/";
				}
				newUrl += splitpar[i];
			}
			//we now append crawled url to new url
			newUrl+=crawled;
		}
		else {
			//weird url. return empty.
			newUrl="";
		}
		
		return newUrl;
	}

	// a helper function for counting the number of occurrences of a regex in
	// any given string.
	public int countOccurrencesOf(String regex, String str) {
	    Matcher m = Pattern.compile(regex).matcher(str);
	    int count;
	    for (count = 0; m.find(); count++);
	    return count;
	}

	// fileContents is the HTML source code
	// fileURL is the URL associated with fileContents -- later known 
	// as parent URL to crawled links
	public void getLinksFromPage(String fileContents, String fileURL) {
		System.out.println("fileURL: "+fileURL);
		//Let's first construct regex patterns

		// patterns are case insensitive
		String atag_pattern = "(?i)<a([^>]+)>(.+?)</a>";
		//(?i)href\s*=\s*(\"([^\"]*\")|'[^']*'|([^'\">\s]+))
		String href_pattern = "(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";
		Pattern atagP = Pattern.compile(atag_pattern);
		Pattern hrefP = Pattern.compile(href_pattern);
		
		// create a java matcher for searching through page contents - returns boolean
		Matcher atag_match = atagP.matcher(fileContents);

        while (atag_match.find()) {

            // the regex used for aTags has two groups
            // first group is the attributes of the aTag
            // second group is the text that is wrapped by the aTag or
            // the anchor text

			// Let's get the contents the aTag. This includes attributes
			// like href, title, id, etc.
            String attr = atag_match.group(1);

            // let's get the anchor text of the aTag
            String anchorText = atag_match.group(2);

            // let's build a java matcher for the href attribute
            Matcher href_match = hrefP.matcher(attr);
            while (href_match.find()) {

            	// let's capture the link here. group 1 contains the link enclosed
            	// by double quotes. so we have to strip these quotes out.
                String crawled_link = href_match.group(1).replaceAll("\"","");

                // We must eliminate bookmarks from URL or discard URL if only this
				if (crawled_link.charAt(0)=='#' || crawled_link.charAt(0)=='?') {
					continue;
				}
				int hashMarkIndex = crawled_link.indexOf('#');
				if (hashMarkIndex != -1) {
					crawled_link = crawled_link.substring(0, hashMarkIndex);
				}
				hashMarkIndex = crawled_link.indexOf('?');
				if (hashMarkIndex != -1) {
					crawled_link = crawled_link.substring(0, hashMarkIndex);
				}

                //System.out.println("LinkE: "+anchorText);
                //System.out.println("LinkL: "+crawled_link);
                String fixedlink = processURL(crawled_link,"http://www.ucr.edu/");
                if (fixedlink.length()>0) {
                	System.out.println("LinkL: "+fixedlink);
                }
            }
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
      	//System.out.println(pme);

      	Logger log = new Logger(crawlerlog);
      	log.write(pme);

      	Crawler spidey = new Crawler(seedfile,outputdir);
      	//http://www.ucr.edu/students/computer.html
      	spidey.fetchURL("http://www.ucr.edu/students/computer.html");//http://www.ucr.edu/

      	String seed_url = "http://www.ucr.edu/one/two/three/four/computer.html";
      	String crawled_url = "../../../../hey/index.html";
      	System.out.println("SeedURL: "+seed_url);
      	System.out.println("CrawedURL: "+crawled_url);
      	System.out.println("New URL: "+spidey.processURL(crawled_url,seed_url));
      	//spidey.getLinksFromPage("<a href=\"http://campusmap.ucr.edu/\">Campus Map</a>");
	}
}



