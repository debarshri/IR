package nl.tudelft.ir.main;

/**
 * 
 * Main class for Creating Indexes
 * Configuration are read from the config.properties residing in 
 * "/tmp/config.properties"
 * 
 * 
 */
import java.io.IOException;

import nl.tudelft.ir.model.CreateIndex;

public class IndexGen {
	public static void main(String[] args) {

		try {
			
			CreateIndex idx = new CreateIndex();
			idx.indexGenUtils();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
