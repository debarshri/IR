package nl.tudelft.ir.main;

import java.io.IOException;

import nl.tudelft.ir.Config;
import nl.tudelft.ir.CreateIndex;


public class IndexGen {
	public static void main(String[] args) {

		try {

			Config conf = new Config();
			conf.readProp();

			if (conf.getDocsPath() == null) {
				System.exit(1);
			}

			CreateIndex idx = new CreateIndex();
			idx.indexGenUtils(conf.getIndexPath(), conf.getDocsPath(),
					conf.isCreate());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}