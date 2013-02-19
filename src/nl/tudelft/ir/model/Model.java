package nl.tudelft.ir.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.CanReadFileFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import nl.tudelft.ir.types.Result;
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
	
	/*
	 * This is a reference to the object that encapsulates the 
	 * configuration 
	 */
	Config c; 
	
	/*
	 * This is the reference to the IndexReader that is used to manipulate
	 * the index.
	 */
	IndexReader indexReader;
	
	/*
	 * This is a reference to the IndexSearcher object that will be used
	 * to submit queries. 
	 */
	IndexSearcher searcher;
	
	/*
	 * This is the reference to the Analyzer object used to analyze tokens.
	 */
	Analyzer analyzer;
	
	/*
	 * This is the path to the index files.
	 */
	String path;
	
	/**
	 * Public constructor for Model class.
	 */
	public Model(View v){
		
		//initialize arraylist that maintains state
		textFiles = new ArrayList<File>();
		
		//assign the collaborating view reference 
		this.v=v;
		
		c = new Config();
		
		try {
			c.readProp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
			System.out.println("Error reading the properties file!");
			
			e.printStackTrace();
		}
		
		try {
			indexReader = IndexReader.open(FSDirectory.open(new File
					(c.getIndexPath())));
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in index reading!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error in index reading!");
			e.printStackTrace();
		}
		
		searcher = new IndexSearcher(indexReader);
		
		analyzer = new StandardAnalyzer(Version.LUCENE_31);
		
		
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
		
		this.path = path;
		
		/*
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
		*/
	}

	/**
	 * This method changes the state of the Model.  
	 */
	public void changeState(Query _query){
		
		textFiles.clear();
		
		

		TopDocs results = null;
		try {
			results = searcher.search(_query, 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ScoreDoc[] hits = results.scoreDocs;

		for(int i=0;i<hits.length;i++){
			Document doc = null;
			try {
				doc = searcher.doc(hits[i].doc);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String path = doc.get("path");
			if (path != null) {
				Result res = new Result();
				res.setPath(path);
				System.out.println(path);
				textFiles.add(new File(path));
				
			}
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

		
		List<String> lines = null;
		//try return the string with the contents of the file
		try {
			lines = FileUtils.readLines(fileToRead);
		} catch (IOException e) {

			//if reading didn't succeed, return error message
			e.printStackTrace();
			content =  "error reading file at "+path;
		}
		
		for(String s:lines){
			content+=s;
			content+="\n";
		}
		System.out.println(content);

		v.updateResults(content);
		
	}

}
