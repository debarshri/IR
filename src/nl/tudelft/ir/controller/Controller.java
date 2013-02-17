package nl.tudelft.ir.controller;

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
	
	
	/**
	 * Public constructor for the Controller.
	 * Receives a reference to the Model as parameter.
	 */
	public Controller(Model m){
		
		//assign the model reference
		this.m=m;
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
	 */
	
	public void query(String query){
		
	}
	
	public void inspectFile(String path){
		m.readFile(path);
	}
	
}
