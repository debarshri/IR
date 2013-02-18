package nl.tudelft.ir.controller;

import java.util.HashMap;

import nl.tudelft.ir.model.Model;

/**
 * The Controller class receives commands from the View and changes the
 * state of the Model accordingly. 
 * @author mcadariu
 *
 */
public class Controller {
	
	/*
	 * This is the path to the target directory where the input files 
	 * are located. 
	 */
	private String directoryPath; 
	
	/*
	 * This is a reference to the Model object
	 */
	private Model m;
	
	/*
	 * This is a data structure that contains the correlation between
	 * integer values (the index of the element that is selected in the
	 * view regarding the query type), and Query objects from the 
	 * Lucene library.  It is used in the query method.
	 */
	private HashMap<Integer,Object> dictionaryQueries;
	
	/**
	 * Public constructor for the Controller.
	 * Receives a reference to the Model as parameter.
	 */
	public Controller(Model m){
		
		//assign the model reference
		this.m=m;
		
		//todo:initialize the hash map with objects from the Lucene
		//library.  so if in the view, Boolean is the first element
		//in the drop-down, we do:
		// dictionaryQueries.add(0,new BooleanQuery()) or something along
		// these lines
	}
		
	/**
	 * This method sets the path to the input data that will be indexed.
	 */
	public void setDirectoryPath(String path){
		m.setPath(path);
		m.toString();
	}
	
	/**
	 * This method indexes the dataset. 
	 */
	public void createIndexes(){
		
	}
	
	/**
	 * This method determines the model's state to change as a result
	 * to a query constructed in the View. 
	 * 
	 * Parameters : query text, query code. The query code is for deciding
	 * on which query object to instantiate from the Lucene library for 
	 * querying the model (which means specifying how the state will change
	 * ). 
	 */
	public void query(String query, int code){
		//check if query is well formulated
		
		//first get the query object from the dictionary, then 
		//issue it to the model. 
			
		System.out.println("In controller received query="+query+
				"code="+code);
	}
	
	/**
	 * This method issues the command to the model to read a file at a
	 * path.
	 * @param path
	 */
	public void inspectFile(String path){
		m.readFile(path);
	}
	
}
