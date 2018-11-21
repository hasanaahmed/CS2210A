/**
 * 
 * @author hasanahmed Student ID: 250897473
 * CS2210: Asn 4 
 * this class implements the user interface and it contains the main method dictionary, declared with the usual
 * method dictionary header
 *
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UserInterface {

	/**
	 * 
	 * @param args
	 * @throws DictionaryException
	 * @throws MultimediaException
	 * Usage: main method that runs
	 */
    public static void main(String[] args) throws DictionaryException, MultimediaException {
        //initialize
    	StringReader keyboard = new StringReader();
        SoundPlayer soundPlayer = new SoundPlayer();
        PictureViewer pictureViewer = new PictureViewer();
        OrderedDictionary dictionary = new OrderedDictionary();
        //set filename to argument inputted
        String filename = args[0];		
        //check arg size to just one file
        if (args.length != 1) {
        	System.out.println("Usage: Java UI <filename>");
        	System.exit(0);
        }
        
        //main procedure if checks are good so far
        try {
            //read in file
        	BufferedReader in = new BufferedReader(new FileReader(filename));
            String word = in.readLine();
            String line;
            //used to store dictionary entries properly
            try {
            	//make sure that end of file isn't hit
                while (word != null) {
                    try {
                    	//read in line
                    	line = in.readLine();
                        //for images
                    	if (line.contains(".gif") || (line.contains(".jpg"))) {
                            dictionary.put(new Record(new Pair(word, "image"), ""));
                        }
                        //for sound
                    	else if (line.contains(".wav") || line.contains(".mid")) {
                            dictionary.put(new Record(new Pair(word, "audio"), ""));
                        }
                        //for text
                    	else{
                            dictionary.put(new Record(new Pair(word, "text"), line));;
                        }
                        //to iterate to next line
                    	word = in.readLine();
                    } 
                    catch (Exception e) {
                        System.out.println("Value input error due to failure");
                    }
                }
            } 
            catch (Exception e) {
                System.out.println("Putting values error due to failure");
            }
            //close file
            finally {
    			in.close();
            }
            in.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be opened: " + filename);
        } 
        catch (IOException e) {
        	System.out.println("Cannot open file: " + filename);
        }
    
        //entering statement 
        System.out.println("Welcome to my Multimedia Dictionary! \n get: retrieve from dictionary \n put: insert into dictionary \n delete: remove from dictionary \n list: find all text with prefix \n smallest: retrieve smallest key in dictionary \n largest: retrieve the largest key in dictionary \n finish: end program \n");
        do {
            String command = keyboard.read("Enter command: ");
            String commandParsed[] = command.toLowerCase().split("\\s");
            String stringCombined = "";

            for (int i = 3; i < commandParsed.length; i++) {
                stringCombined += commandParsed[i] + " ";
            }

            switch (commandParsed[0]) { 
            	//used to insert into dictionary
                case "put":
                    Pair key = new Pair(commandParsed[1], commandParsed[2]);
                    Record record = new Record(key, stringCombined);
                    try {
                        dictionary.put(record);
                    } 
                    catch (DictionaryException e) {
                        System.out.println(("A record with the given key (" + commandParsed[1] + " ," + commandParsed[2] + ")" + " is already in the ordered dictionary."));
                    }
                    System.out.println();
                    break;
                //used to get from dictionary
                case "get":
                    boolean exists = false;
                        if (dictionary.get(new Pair(commandParsed[1], "text")) != null) {
                            System.out.println(dictionary.get(new Pair(commandParsed[1], "text")).getData());
                            exists = true;
                        }
                        if (dictionary.get(new Pair(commandParsed[1], "audio")) != null) {
                            try {
                                soundPlayer.play(commandParsed[1] + ".wav");
                            } 
                            catch (MultimediaException e){
                                try {
                                    soundPlayer.play(commandParsed[1] + ".mid");
                                } 
                                catch (MultimediaException ignore) {
                                }
                            }
                            exists = true;
                        }
                        if (dictionary.get(new Pair(commandParsed[1], "image")) != null) {
                            try {
                                pictureViewer.show(commandParsed[1] + ".gif");
                            } 
                            catch (MultimediaException e) {
                                try {
                                    pictureViewer.show(commandParsed[1] + ".jpg");
                                } 
                                catch (MultimediaException ignore) {
                                }
                            }
                            exists = true;
                        }
                        if (!exists) {
                            System.out.println("The word " + commandParsed[1] + " is not in the ordered dictionary");
                            dictionary.put(new Record(new Pair(commandParsed[1], "text"), "text"));
                            System.out.println();
                            dictionary.preOrder();
                            System.out.println();
                            Record pre = dictionary.predecessor(new Pair(commandParsed[1], "text"));
                            Record post = dictionary.successor(new Pair(commandParsed[1], "text"));
                            System.out.println("Preceding word: " + pre.getKey().getWord());
                            System.out.println("Following word: " + post.getKey().getWord());
                            dictionary.remove(new Pair(commandParsed[1], "text"));
                        }
                    break;
                //used to delete from dictionary
                case "delete":
                    Pair keyDelete = new Pair(commandParsed[1], commandParsed[2]); // added this
                    try {
                        dictionary.remove(keyDelete);
                    } 
                    catch (DictionaryException e){
                        System.out.println("No record in the ordered dictionary has key (" + commandParsed[1] +", " + commandParsed[2] + ")");
                    }

                    System.out.println();
                    dictionary.preOrder();
                    break;
                //used to find smallest key in dictionary
                case "smallest":
                    System.out.println(dictionary.smallest().getKey().getWord() + ", " + dictionary.smallest().getKey().getType());
                    break;
                //used to find largest in dictionary
                case "largest":
                	System.out.println(dictionary.largest().getKey().getWord() + ", " + dictionary.largest().getKey().getType());
                    break;
                //used to search dictionary for prefix
                case "list":
                	String prefix = commandParsed[1];
					Record w = dictionary.successor(new Pair(prefix, "text"));
					String r = w.getKey().getWord();

					while(r.startsWith(prefix) && (!r.isEmpty())){
						System.out.println(r);
						w = dictionary.successor(new Pair(r, "text"));
						r = w.getKey().getWord();
					}
                    break;
                //used to exit program
                case "finish":
                    System.out.println("Thanks! Have a good day.");
                	return;
                //used to find wrong commands
                default:
                    System.out.println("That is an invalid command, please try again");
                    System.out.println();
                    break;
            }
        }
        while (true);
    }
}
	
	