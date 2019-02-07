import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileNameExtensionFilter;

public class PedibusMain {

	public static void main(String[] args) {
		PrintWriter writer2;
		 if (args.length == 0) {
	            System.out.println("Enter filename");
	        } else {

	            String fileName = args[0];
	            String name = new File(fileName).getName();
	            String[] split = name.split(".dat");
	            
	            
	            java.util.Date date = new java.util.Date();
	            
	            try{
	    		    writer2 = new PrintWriter("Log"+name+".txt", "UTF-8");
	    		    
	    		    writer2.println(date);
		    	    System.out.println(date);
		    	Handler handler= new Handler();
		    	handler.setFilePath(fileName);
		    	handler.setNameFile(split[0]);
		    	handler.start();
		    	date = new java.util.Date();
		        System.out.println(date);
		        writer2.println(date);
		        writer2.close();
	    		} catch (IOException e) {
	    		   // do something
	    		}
	            
	        }
		
		// TODO Auto-generated method stub
		
	}

}
