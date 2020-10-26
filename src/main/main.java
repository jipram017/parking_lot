package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.exception.ErrorCode;
import main.exception.ParkingException;
import main.handler.InputHandler;

public class main {

	public static void main(String[] args) {
		InputHandler handler = new InputHandler();
		BufferedReader reader = null;
		String input = null;
		
		try {
			switch(args.length) {
			    case 0: // Interactive command line
			    {
			    	System.out.println("Please Enter 'exit' to end execution");
			        System.out.println("Input:");
			        while(true) {
			        	try {
			        		reader = new BufferedReader(new InputStreamReader(System.in));
			        		input = reader.readLine().trim();
			        		if(input.equalsIgnoreCase("exit")) {
			        			break;
			        		}
			        		else {
			        			if(handler.validate(input)) {
			        				try {
			        					handler.handle(input.trim());
			        				}
			        				catch(Exception e) {
			        					System.out.println(e.getMessage());
			        				}
			        			}
			        			else {
			        				System.out.println("Invalid input string");
			        			}
			        		}
			        		
			        	}
			        	catch(Exception e){
			        		throw new ParkingException(ErrorCode.INVALID_REQUEST.getMessage(), e);
			        	}
			        }
			        break;
			    }
			    
			    case 1:  // File input / output
			    {
			    	File file = new File(args[0]);
			    	try {
			    		reader = new BufferedReader(new FileReader(file));
			    		int line = 0;
			    		while(reader.readLine() != null) {
			    			input = input.trim();
			    			if(handler.validate(input)){
			    				try {
			    					handler.handle(input);
			    				}
			    				catch(Exception e){
					    			e.printStackTrace();
					    			System.out.println(e.getMessage());
			    				}
			    			}
			    			else {
			    				System.out.println("Incorrect Command Found at line: " + line + " ,Input: " + input);
			    			}
			    			line++;
			    		}
			    	}
			    	catch(Exception e) {
			    		throw new ParkingException(ErrorCode.INVALID_FILE.getMessage(), e);
			    	}
			    	break;
			    }
			    
			    default:
			    	System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");	   
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch(IOException e) {
				
			}
		}
	}

}
