package nl.tudelft.ir.model;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

import nl.tudelft.ir.controller.SearchIndexController;
import nl.tudelft.ir.types.Result;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Responsible for Searching
 * 
 * 
 * @author debarshi
 * 
 */
public class IndexSearch {

/**
 * 
 * Fields for IndexSearch
 * 
 * 
 * 
 */
	private SpellChecker spellChecker;
	private ArrayList<Result> result = new ArrayList<Result>();
	private static int numOfhits;
	private String[] didYouMean;
	private long timeTaken;

	static Config conf = new Config();

	
	/**
	 * 
	 * 
	 * Search Method
	 * 
	 * @param field
	 * @param queryString
	 * @param hitsPerPage
	 * @return
	 */
	@SuppressWarnings("static-access")
	public ArrayList<Result> Search(String field, String queryString,
			int hitsPerPage) {
		try {
			
			conf.readProp();
			IndexReader reader = IndexReader.open(FSDirectory.open(new File(
					conf.getIndexPath())));

			/**
			 * 
			 * SpellCheck implementation Imported from SpellCheck jar from Contrib in
			 * Lucene 3.6.2
			 * 
			 */

			spellChecker = new SpellChecker(FSDirectory.open(new File(conf
					.getIndexPath())));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);

			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31,
					analyzer);

			spellChecker.indexDictionary(new LuceneDictionary(reader, field),
					iwc, false);

			String[] suggestions = spellChecker.suggestSimilar(queryString, 5);
			setDidYouMean(suggestions);

			BufferedReader in = null;

			QueryParser parser = new QueryParser(Version.LUCENE_31, field,
					analyzer);
			/**
			 * Reference : From Lucene Demo - for reading the query
			 */
			long startTime = System.currentTimeMillis(); 
			while (true) {

				// Check this part
				String line = queryString;

				if (line == null || line.length() == -1) {
					break;
				}

				line = line.trim();
				if (line.length() == 0) {
					break;
				}

				Query query = parser.parse(line);
				System.out.println("Searching for: " + query.toString(field));

				result = new SearchIndexController().doPagingSearch(in, searcher, query, hitsPerPage,
						queryString == null);

				setTimeTaken(System.currentTimeMillis()-startTime);
				if (queryString != null) {
					break;
				}
			}
			searcher.close();
			reader.close();
		} catch (Exception e) {

		}
		return result;
	}

	

	public int getNumOfhits() {
		return numOfhits;
	}

	@SuppressWarnings("static-access")
	public void setNumOfhits(int numOfhits) {
		this.numOfhits = numOfhits;
	}

	public String[] getDidYouMean() {
		return didYouMean;
	}

	public void setDidYouMean(String[] didYouMean) {
		this.didYouMean = didYouMean;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

}