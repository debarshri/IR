package nl.tudelft.ir.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;

import nl.tudelft.ir.view.View;

/**
 * The Model class maintains the application state that will be displayed
 * on the View. Right now the states are the files that are located in the
 * input directory.  For the IR project the files will be the indexed
 * e-mails. 
 * @author mcadariu
 *
 */

public class Model{

	/*
	 * List of text files that are currently in the state.
	 */
	private Collection<File> textFiles;
	
	
	/*
	 * This is a reference to the View object where state encapsulated
	 * in the Model will be displayed. 
	 */
	View v;
	
	
	/**
	 * Public constructor for Model class.
	 */
	public Model(View v){
		
		//initialize arraylist that maintains state
		textFiles = new ArrayList<File>();
		
		//assign the collaborating view reference 
		this.v=v;
	}

	/**
	 * This method sets the path to the input source
	 */
	public void setPath(String path){
		
		if(path == null)
			return;
		
		if(path == "")
			return;
				
		//covert path string to abstract path 
		File baseDirectory = new File(path);
		
		if(!baseDirectory.exists())
			return;
		
		if(!baseDirectory.isDirectory())
			return;

		//save the contents of the directory in the state; only readable
		//files are considered
		textFiles = FileUtils.listFiles(baseDirectory, 
				CanReadFileFilter.CAN_READ, 
				CanReadFileFilter.CAN_READ);
		
		if(textFiles.size()==0)
		{
			//no files, return
			return;
		}
		
		File[] files = new File[textFiles.size()];
		textFiles.toArray(files);
		
		String[] fileNames = new String[textFiles.size()];
		int i=0;
		for(File f:files){
			fileNames[i++]=f.toString();
		}
		
		v.updateList(fileNames);
	}

	/**
	 * This method changes the state of the Model.  
	 */
	public void changeState(String path){
				
	}
	
	/**
	 * This method reads the contents of one file and returns the String
	 * representing the content. 
	 */
	public void readFile(String path){
		
		if(path == null)
			return;
		
		//create abstract path from concrete path
		File fileToRead = new File(path);
		
		if(!fileToRead.isFile())
			return;
		
		if(!fileToRead.canRead())
			return;
		
		String content = "";
				
		//try return the string with the contents of the file
		try {
			content =  FileUtils.readFileToString(fileToRead);
		} catch (IOException e) {
			
			//if reading didn't succeed, return error message
			e.printStackTrace();
			content =  "error reading file at "+path;
		}
		
		v.updateResults(content);
		
	}

}
