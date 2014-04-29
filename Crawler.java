public class Crawler {
	public static void main(String[] args) {
		//System.out.println("Hello World!");
		/*System.out.println("Number of Args is "+args.length+" and they are:");
		for (String s: args) {
          System.out.println(s);
      	}*/
      	String seedfile = args[0];
      	int numpages = Integer.parseInt(args[1]);
      	int hopsaway = Integer.parseInt(args[2]);
      	String outputdir = args[3];
      	System.out.println("Seed File: "+seedfile+"\nNumber of Pages: "+numpages+"\nNumber of Hops Away: "+hopsaway+"\nOutput Directory: "+outputdir);
	}
	public static void processInput(String[] args) {
		;//
	}
}