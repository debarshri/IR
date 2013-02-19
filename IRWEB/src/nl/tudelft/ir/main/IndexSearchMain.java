package nl.tudelft.ir.main;

import java.io.IOException;
import nl.tudelft.ir.IndexSearch;

/**
 * 
 * Main Class for Searching Index
 * 
 * @author debarshi
 *
 */
public class IndexSearchMain {
	public static void main(String[] args) throws Exception {
		try {
			IndexSearch idx = new IndexSearch();
			idx.Search(args[0], args[1], Integer.parseInt(args[2]));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	
	}

}
