import java.io.File;
import java.util.*;


public class SWint {
	static Input input;
	public SWint() {
		
		
	}

	public static void main(String[] args) {
		Reader r=new Reader();
		if(args.length>0) {
			//open current directory and view files
			if(args[0].charAt(0)=='-') {
				File folder = new File(System.getProperty("user.dir"));
				File[] listOfFiles = folder.listFiles();
				//add file names to list 
				List<String> fileNames=new LinkedList<>();
				for(File f:listOfFiles) {
					fileNames.add(f.getAbsolutePath());
				}
				//sort that list by extension
				r.sortByType(fileNames);
			}
			//depending on the specification open the appropriate group
		if(args[0].equals("-j")) {
			//TODO get all java files
			r.openJava();
		}
		else if(args[0].equals("-a")) {
			//TODO get all ada Files
			r.openAda();
		}
		else if(args[0].equals("-c")) {
			//TODO get all c++ files
			r.openCpp();
		}
		else if(args[0].equals("-all")) {

			r.openCpp();
			r.openAda();
			r.openJava();


		}
		//then is should be a file name 
		else {
			
			r.openSingleFile(args[0]);
		}
		}
		else {
			//my baby
			//input=new Input();
			//input.run();
			//default is to check all
			r.openCpp();
			r.openAda();
			r.openJava();
			System.out.println("Invalid Input.");
		}
		
	}
	//TODO create method that allows us to notify the user, link all printlines to this
	public static void notifyUser(String message) {
		System.out.println(message);
	}
	public static String getResponse(String message) {
		Scanner scanner=new Scanner(System.in);
		System.out.println(message);
		return scanner.nextLine();
	}

}
