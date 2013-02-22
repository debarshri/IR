package nl.tudelft.ir.model;

import nl.tudelft.ir.controller.CreateIndexController;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 
 * Class responsible for index creation
 * 
 * @author debarshi
 * 
 */

public class CreateIndex {

	/**
	 * 
	 * Used in the writeidx.jsp for UI Utility method for index creation
	 * 
	 * @param docsPath
	 * @param indexPath
	 * @param create
	 */
	private Config conf;
	private String timeTaken;
	public void indexGenUtils() {
		
		try{
		conf = new Config();
		conf.readProp();

		}catch (Exception e)
		{
			System.err.println("Config Not Found");
		}
		final File docDir = new File(conf.getDocsPath());
		if (!docDir.exists() || !docDir.canRead()) {
			System.out
					.println("Document directory '"
							+ docDir.getAbsolutePath()
							+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}
		Date start = new Date();
		try {
			System.out.println("Indexing to directory...");

			Directory dir = FSDirectory.open(new File(conf.getIndexPath()));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31,
					analyzer);

			if (conf.isCreate()) {
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}

			 iwc.setRAMBufferSizeMB(256.0);

			IndexWriter writer = new IndexWriter(dir, iwc);
			
			/**
			 * 
			 * Same as Mircea's Change state 
			 * 
			 */
			
			
			new CreateIndexController().indexDocs(writer, docDir);

			 writer.forceMerge(1);

			writer.close();

			Date end = new Date();
			setTimeTaken(Long.toString((end.getTime() - start.getTime())));
			System.out.println(end.getTime() - start.getTime()
					+ " total milliseconds");

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
		}
	}
	public String getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}

	/**
	 * Finds a file, reads it and sets fields
	 * 
	 * @param writer
	 * @param file
	 * @throws IOException
	 */

}