/**
 * The Input class validates files and organizes them into lists
 * based on their file types
 * 
 * @author Abby Beizer
 * @author Jamie Tyler Walder
 */


import java.io.File;
import java.io.IOException;
import java.util.*;


public class Input {

	private static boolean verbose = false;
	private Analyzer analyzer;	
	private List<String> adaFiles; 	//a collection of Ada files
	private List<String> javaFiles;	//a collection of java files
	private List<String> cppFiles;	//a collection of c++ files
	private boolean uJava, uAda, uCPP;	//records the types of files the user wants to analyze
	
	/**
	 * Default Constructor
	 * Initializes each file list
	 */
	public Input() {
		adaFiles = new LinkedList<>();
		javaFiles = new LinkedList<>();
		cppFiles = new LinkedList<>();
		uJava = false;
		uAda = false;
		uCPP = false;
	} 
	
	
	
	
	///////////////////////////
	//	Process User Input   //
	//						 //
	///////////////////////////
	
	/**
	 * This is the main behavior of the SIT's user interface.
	 * Accepts user input and collects specified SIT language tags, exits the program if an invalid command is supplied. 
	 * Then validates directory and file paths, storing them in a list based on language, or exits the program if a path is invalid.
	 *
	 * @param args User input
	 */
	public void processInput(String[] args)
	{
		if (verbose)
		SIT.notifyUser("Verifing Files...");
		//If no arguments specified, process all files with known extensions
		//in the current directory
		if (args.length == 0) 
		{
			addFiles(getAllFilesInDirectory(System.getProperty("user.dir")));
		}
		//If one argument is specified, and it is a language tag,
		//process all files of that type in the current folder
		else if(args.length == 1)
		{
			switch (args[0]) 
			{
				//If the tag is not recognized, the program exits with a warning
				case "-j":
					addJavaFilesInDirectory(System.getProperty("user.dir"));
					break;
				case "-a":
					addAdaFilesInDirectory(System.getProperty("user.dir"));
					break;
				case "-c":
					addCppFilesInDirectory(System.getProperty("user.dir"));
					break;
				case "-v":
					verbose = true;
					break;
				case "-r":
					addFiles(getAllFilesInDirectoryAndSubDirectories(System.getProperty("user.dir")));
					break;
				case "-all":
					addFiles(getAllFilesInDirectory(System.getProperty("user.dir")));
					break;
				case "-help":
				case "?":
					SIT.displayHelp();
					break;
				default:
					if(args[0].startsWith("-"))
					{
						SIT.notifyUser("Error: unrecognized command: '" + args[0] + "'. Type -help for a list of valid commands.");
					}
					else
					{
						SIT.notifyUser("Error: At least one language must be specified. Type -help for a list of valid commands.");
					}
					
					System.exit(0);
			}//end switch
		}
		//If more than one argument is specified
		else
		{
			if( !(args[0].trim().startsWith("-")))
			{
				SIT.notifyUser("Error: At least one language must be specified. Type -help for a list of valid commands.");
			}
			
			//iterate through tag arguments and record which languages
			//the user wants to analyze for
			boolean tagZone = true;
			int nonTagsStart = 0;
			for (int i = 0; i < args.length && tagZone; i++) 
			{
				switch (args[i]) 
				{
					//By default, searches the current directory
					case "-j":
						uJava = true;
						break;
					case "-a":
						uAda = true;
						break;
					case "-c":
						uCPP = true;
						break;
					case "-v":
						verbose = true;
						break;
					case "-all":
						uJava = true;
						uAda = true;
						uCPP = true;
						break;
					case "-help":
					case "?":
						SIT.displayHelp();
						break;
					default:
						tagZone = false;
						nonTagsStart = i;
						break;
				}
			}
			//add explicit file or directory names to fileList, if the user supplied them
			if (!tagZone) 
			{
				boolean nameFragment = false;	//Flag for whether the current argument is part of a file or 
												//dir name (one that contains whitespace)
				boolean hasTag = false;		//Flag for whether the next argument is a modifying tag for the
											//current file or directory
				File f = new File("");
				
				//This starts after the tags are processed
				for (int i = nonTagsStart; i < args.length; i++) 
				{
					
					//If the previous run of the loop detected that this argument was a tag,
					//Move right to the next argument
					if(hasTag)
					{
						hasTag = false;
						nameFragment = false;
						continue;
					}
					
					//If the current argument is part of a file or dir name,
					//add the word to the rest of the file name, and skip the rest of the for loop
					//Else if the flag is set to false, then add the previous filename to the input and
					//process the current argument
					//Then proceed as normal
					if(nameFragment)	
					{
						File temp = new File(args[i]);
						//If temp is a file or directory on its own, then f is not an existing path
						//so display an error and terminate the program
						if(temp.isFile() || temp.isDirectory())
						{
								SIT.notifyUser("Error: Invalid path: " + f.getAbsolutePath());
								System.exit(0);
						}
						//if temp was not a file/dir on its own, concatenate the current argument to f
						else
						{
							//nameFragment remains true
							f = new File(f.getAbsolutePath() + " " + args[i]);
						}
						//If the newly-concatenated f is a file or directory,
						//add it to the input and set the nameFragment flag to false
						//If f is not a file or directory, and there are no more args to concatenate to f, then the path is invalid
						if(f.isFile() || f.isDirectory()) 
						{
							try
							{
								hasTag = verifyUserInputFileType(f.getAbsolutePath(), args[i + 1]);
							}
							catch(ArrayIndexOutOfBoundsException ar)
							{
								hasTag = verifyUserInputFileType(f.getAbsolutePath(), " ");
							}
							
							nameFragment = false;
							continue;
						}
						else if(i == args.length - 1)
						{
							SIT.notifyUser("Error: Invalid path: " + f.getAbsolutePath());
							System.exit(0);
						}
					}
					else
					{
						//Process only the current argument
						f = new File(args[i]);
					}
					
					//If f is a file or directory, then add it to the input and set the nameFragment flag to false
					//TODO: This if statement always returns false on the first loop
					if (f.isFile() || f.isDirectory()) 
					{
						try
						{
							hasTag = verifyUserInputFileType(f.getAbsolutePath(), args[i + 1]);
						}
						catch(ArrayIndexOutOfBoundsException ar)
						{
							hasTag = verifyUserInputFileType(f.getAbsolutePath(), " ");
						}
						
						nameFragment = false;
					} 
					else 
					{
						nameFragment = true;
					}//end if
				}//end for
				
				//If the user declared a language tag but did not supply any files for this tag,
				//then the tag will pull all of that language from the current directory
				if(uJava && javaFiles.size() == 0)
				{
					addJavaFilesInDirectory(System.getProperty("user.dir"));
				}
				if(uAda && adaFiles.size() == 0)
				{
					addAdaFilesInDirectory(System.getProperty("user.dir"));
				}
				if(uCPP && cppFiles.size() == 0)
				{
					addCppFilesInDirectory(System.getProperty("user.dir"));
				}
				
			}
		}//end if
	
		this.analyze();
	}
	
	/**
	 * If the file extensions match the SIT language tags supplied by the user, then those files are added to the appropriate file list.
	 * If a file extension does not match the type of files the user wishes to analyze, then an error is displayed and the program is terminated.
	 * If a directory is followed by the -r SIT command, then all files conforming to the specified SIT language tags are added to the appropriate list
	 * from within that directory and all of its sub-directories
	 * @param filename The name of the file currently being processed
	 * @param argument The argument following the current filename
	 * @return True if argument is tag -r or -all
	 */
	private boolean verifyUserInputFileType(String filename, String argument)
	{	
		File f = new File(filename);
	
		switch (argument) {
		//Default is if the next argument is not a recursive tag for this file
		//Tags may only follow Directory names
		case "-r":
		{
			if(f.isFile())
			{
				SIT.notifyUser("Invalid command following " + filename);
				System.exit(0);
			}
			else if (f.isDirectory())
			{
				List<String> files = (getAllFilesInDirectoryAndSubDirectories(filename));
				for(String file : files)
				{
					if(uJava && isJavaFile(file))
					{
						this.javaFiles.add(file);
					}
					else if(uAda && isAdaFile(file))
					{
						this.adaFiles.add(file);
					}
					else if(uCPP && isCppFile(file))
					{
						this.cppFiles.add(file);
					}
				}//end for
			}
			else
			{
				SIT.notifyUser("Invalid file or directory name: " + filename);
				System.exit(0);
			}
			return true;
		}
		case "-all":
		{
			if(f.isFile())
			{
				SIT.notifyUser("Invalid command following " + filename);
			}
			else if (f.isDirectory())
			{
				if(uJava)
				{
					addJavaFilesInDirectory(filename);
				}
				if(uAda)
				{
					addAdaFilesInDirectory(filename);
				}
				if(uCPP)
				{
					addCppFilesInDirectory(filename);
				}
			}
			else
			{
				SIT.notifyUser("Invalid file or directory name: " + filename);
				System.exit(0);
			}
			return true;
		}
		default:
			if(argument.startsWith("-"))
			{
				SIT.notifyUser("The program has encountered an invalid argument following filepath " + filename + "\n");
				System.exit(0);
			}
			else
			{
				if(f.isFile())
				{
					if(isJavaFile(filename) && uJava)
					{
						javaFiles.add(filename);
					}
					else if(isAdaFile(filename) && uAda)
					{
						adaFiles.add(filename);
					}
					else if(isCppFile(filename) && uCPP)
					{
						cppFiles.add(filename);
					}
					else
					{
						SIT.notifyUser("Error: file " + filename + " does not match a user-specified language.");
						System.exit(0);
					}
				}	
				else if(f.isDirectory())
				{
					if(uJava)
					{
						addJavaFilesInDirectory(filename);
					}
					if(uAda)
					{
						addAdaFilesInDirectory(filename);
					}
					if(uCPP)
					{
						addCppFilesInDirectory(filename);
					}
				}
				else
				{
					SIT.notifyUser("Invalid file or directory name: " + filename);
					System.exit(0);
				}//end if	
			}//end if
			return false;
		}//end switch
	}//end verifyUserInputFileType
	
	
	
	////////////////////////////////
	//	Adding and sorting files  //
	//							  //
	////////////////////////////////
	
	/**
	 * Add any number of files to the input class.
	 * Calls the sortByType function
	 * @param filenames A list of files to add
	 */
	private void addFiles(List<String> filenames)
	{
		//sort files depending on their extension
		//and add files to the appropriate linked list
		sortByType(filenames);
	}
	
	/**
	 * Separate files by extension and add each file to its appropriate file list
	 * If the extension is not recognized, do not add it to any list.
	 * @param filenames Names of the that need to be sorted
	 */
	//TODO move these extensions into an enum class
	private void sortByType(List<String> filenames) {
		//check each file's extension
		for (String s : filenames)
		{
			//Validate the file before sorting
			if(validFile(s))
			{
				//checks for java extensions
				if(isJavaFile(s)) 
				{
					javaFiles.add(s);
				}
				//checks for c++ extensions
				else if(isCppFile(s)) 
				{
					cppFiles.add(s);
				}
				//checks for Ada extensions
				else if(isAdaFile(s)) 
				{
					adaFiles.add(s);
				}
			}	
		}
	}
	
	/**
	 * Collects the filenames of all files in current directory.
	 * The files are then sorted into their appropriate lists
	 * @param dir The directory to gather all files from
	 * @return a reader that contains the files
	 */
	//TODO: Make this function private to the Input class. Replace its usage in main
	private List<String> getAllFilesInDirectory(String dir) {
		//gather information on folder
		File folder = new File(dir);
		
		//gather individual
		File[] listOfFiles = folder.listFiles();
		
		//add file names to list 
		List<String> fileNames = new LinkedList<>();
		
		for(File f : listOfFiles) 
		{
			if(f.isFile())
			{
				fileNames.add(f.getAbsolutePath());
			}
			
		}
		//addFiles(fileNames);
		
		//sort that list by extension
	
		return fileNames;
	}
	
	
	/**
	 * Helper method for addAllFilesInDirectoryAndSubDirectories, this method gathers all directories in a directory, much in the same way 
	 * addAllFilesInDirectory does
	 * @param dir the directory you wish to call this method on
	 * @return a list of directories in the given directory
	 */
	private List<String> getAllDirectoriesInDirectory(String dir){
		//create a new file for director
		File folder=new File(dir);
		//get file names
		File[] listOfFiles = folder.listFiles();
		//create new list to hold directory names
		List<String> fileNames = new LinkedList<>();
		//collect all directories
		for(File f : listOfFiles) 
		{
			if(f.isDirectory())
			{
				fileNames.add(f.getAbsolutePath());
			}
			
		}
		return fileNames;
	}
	
	/**
	 * This method is used to recursively call a directory and get all files within that Directory as well as all files inside directories within those 
	 * directories
	 * @param dir the path of the directory you wish to call the method on 
	 * @return all files within that directory and it's subdirectories
	 */
	public List<String> getAllFilesInDirectoryAndSubDirectories(String dir) {
		//gather all files in this directory
		List<String> allFiles=getAllFilesInDirectory(dir);
		//gather all directories within this directory
		List<String> allDirectories=getAllDirectoriesInDirectory(dir);
		//for each subdirectory, gather all files within that directory and it's subdirectories and then add them to the list of 
		//files from the directory that came before it 
		for(String subDir : allDirectories) {
			//the recursive call will continue until it hits the bottom of the directory tree, at which case get all Directories in directory will return zero 
			//and this for loop wont run 
			allFiles.addAll(getAllFilesInDirectoryAndSubDirectories(subDir));
		}
		return allFiles;
	}
	
	/**
	 * Search through directory and collect all Java files
	 * @param dir The directory to pull files from
	 */
	//TODO: Make this function private to the Input class. Replace its usage in main
	private void addJavaFilesInDirectory(String dir) 
	{
		List<String> filenames = getAllFilesInDirectory(dir);
		for(String name:filenames) 
		{
			if(isJavaFile(name)) 
			{
				javaFiles.add(name);
			}
		}
	}
	
	/**
	 * Search through directory and collect all Ada files
	 * @param dir The directory to pull files from
	 */
	//TODO: Make this function private to the Input class. Replace its usage in main
	private void addAdaFilesInDirectory(String dir) 
	{
		List<String> filenames = getAllFilesInDirectory(dir);
		for(String name:filenames) 
		{
			if(isAdaFile(name)) 
			{
				adaFiles.add(name);
			}
		}
	}
	
	/**
	 * Search through directory and collect all C++ files
	 * @param dir The directory to pull files from
	 */
	//TODO: Make this function private to the Input class. Replace its usage in main
	private void addCppFilesInDirectory(String dir) {
		List<String> filenames =  getAllFilesInDirectory(dir);
		for(String name:filenames) {
			if(isCppFile(name)) {
				cppFiles.add(name);
			}
		}
	}
	
	/**
	 * Validates whether the given file exists and has a file extension
	 * that can be handled by the SIT.
	 * @param name A filename
	 * @return true if the file exists and has a valid extension
	 */
	//TODO: Decide if we need this function --> extension validation is done in other methods
	//We could use isFile() rather than having another method call.
	private boolean validFile(String name) {
		
		boolean valid = false;
	
		//try to open the file with the given path
		//if it fails then it will throw an exception and notify the user that it does not exist
		//Return true if we make it through this line and false if not
		File f = new File(name);
		if(f.isFile())
		{
			valid = true;
		}
		else
		{
			SIT.notifyUser(name + " not found.");
		}
		//TODO: validate file extension
		return valid;
	}
	
	/**
	 * Validate whether a file is a Java file
	 * @param filename Name of file to validate
	 * @return true if the file is a Java file
	 */
	private boolean isJavaFile(String filename) {
		//split the file basses on a .
		String[] temp = filename.split("\\.");
		//if there is an extension(the splits leads to more than one)
		//Use the last split to evaluate the extension (covers absolute paths)
		
		if(temp.length>1) {
			int split = temp.length - 1;
			return temp[split].equals("java");
		}
		return false;
	}
	/**
	 * Validate whether a file is an Ada file
	 * @param filename Name of file to validate
	 * @return true if the file is an Ada file
	 */
	private boolean isAdaFile(String filename) {
		
		//split the file bases on a .
		String[] temp=filename.split("\\.");
		//if there is an extension(the splits leads to more than one)
		//Use the last split to evaluate the extension (covers absolute paths)
		if(temp.length > 1) {
			int split = temp.length - 1;
			return temp[split].equals("adb")||temp[split].equals("ada")||temp[split].equals("ada");
		}
		return false;
		
	}
	/**
	 * Validate whether a file is a C++ file
	 * @param filename Name of file to validate
	 * @return true if the file is a C++ file
	 */
	//TODO: Accept header files in this method or in a separate method
	private boolean isCppFile(String filename) {
		
		//split the file basses on a .
		String[] temp=filename.split("\\.");
		//if there is an extension(the splits leads to more than one)
		//Use the last split to evaluate the extension (covers absolute paths)
		if(temp.length > 1) {
			int split = temp.length - 1;
			return temp[split].equals("cpp")||temp[split].equalsIgnoreCase("cxx")||temp[split].equalsIgnoreCase("C")
					||temp[split].equalsIgnoreCase("cc")||temp[split].equalsIgnoreCase("c++");
		}
		return false;
	}
	
	
	
	
	
	///////////////////////////
	//	 Analysis of Files   //
	//						 //
	///////////////////////////
	
	/**
	 * Analyzes sorted lists of files for vulnerabilities
	 */
	public void analyze() {
		LinkedList<String> fileNames=new LinkedList<>();
		fileNames.addAll(javaFiles);
		fileNames.addAll(cppFiles);
		fileNames.addAll(adaFiles);
		Report.filesAnalyzed(fileNames);
		if (verbose)
		SIT.notifyUser("Files Collected.");
		if(javaFiles.size()>0)
			analyzeJava();
		if(adaFiles.size()>0)
			analyzeAda();
		if(cppFiles.size()>0)
			analyzeCpp();
		Report.writeReport();
		//TODO special case for other
	}
	
	/**
	 * Analyzes Java files for vulnerabilities by invoking an Analyzer object
	 */
	private void analyzeJava() {
		//notify user of amount of files in list
		if (verbose)
		SIT.notifyUser(javaFiles.size() + " Java File(s) Found");
		
		//set the analyzer to the appropriate type
		analyzer = new JavaAnalyzer();
		
		//analyze each file
		File f = null;		
		try {
		    for(String filename : javaFiles) {
		    	f = new File(filename);
				if (verbose)
		    	SIT.notifyUser("Analyzing "+f.getCanonicalPath()+"...");
			    analyzer.analyze(filename);
				if (Input.getVerbose())
			    SIT.notifyUser(f.getCanonicalPath()+" has been analyzed.");
		    }
		}
		
		catch (SecurityException | IOException ex) {
		    SIT.notifyUser("Could not get file path.");
		}
		catch (NullPointerException npe) {
		    SIT.notifyUser("File path was null.");
		}
		finally {System.out.println("all java files read");}
	}

	/**
	 * Analyzes Ada files for vulnerabilities by invoking an Analyzer object
	 */
	private void analyzeAda() {
		//notify user of amount of files in list
		SIT.notifyUser(adaFiles.size()+" Ada File(s) Found");
		
		//set the analyzer to the appropriate type
		analyzer = new AdaAnalyzer();
		
		//analyze each file
		File f = null;
		try {
		    for(String filename : adaFiles) {
		    	f = new File(filename);
				if (verbose)
		    	SIT.notifyUser("Analyzing "+f.getCanonicalPath()+"...");
			    analyzer.analyze(filename);
				if (Input.getVerbose())
			    SIT.notifyUser(f.getCanonicalPath()+" has been analyzed.");
			    //TODO analyze
		    }
		}
		catch (SecurityException | IOException ex) {
		    SIT.notifyUser("Could not get file path.");
		}
		catch (NullPointerException npe) {
		    SIT.notifyUser("File path was null.");
		    npe.printStackTrace();
		}
		finally {SIT.notifyUser("all ada files read");}
	}
	
	
	/**
	 * Analyzes C++ files for vulnerabilities by invoking an Analyzer object
	 */
	private void analyzeCpp() {
		//notify user of amount of files in list
		if (verbose)
		SIT.notifyUser(cppFiles.size() + " C++ File(s) Found");
		
		//set the analyzer to the appropriate type
		analyzer= new CppAnalyzer();
		
		//analyze each file
		File f = null;
		try {   
		    for(String filename : cppFiles) {
		    	f = new File(filename);
				if (verbose)
		    	SIT.notifyUser("Analyzing "+f.getCanonicalPath()+"...");
			    analyzer.analyze(filename);
			    if (verbose)
			    SIT.notifyUser(f.getCanonicalPath()+" has been analyzed.");
			    //TODO analyze
		    }
		}
		catch (SecurityException | IOException ex) {
		    SIT.notifyUser("Could not get file path.");
		}
		catch (NullPointerException npe) {
		    SIT.notifyUser("File path was null.");
		}
		finally {SIT.notifyUser("all c++ files read");}
	}
	

	public static boolean getVerbose() {
		return verbose;
	}

}
