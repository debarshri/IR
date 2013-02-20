package nl.tudelft.ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


import nl.tudelft.ir.types.Result;

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
	 * Returns a list of Matched hits
	 * 
	 * @param field
	 * @param queryString
	 * @param hitsPerPage
	 * @return
	 * @throws ParseException
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	
	static Config conf = new Config();
	
	public ArrayList<Result> Search(String field,String queryString, int hitsPerPage) throws ParseException, CorruptIndexException, IOException
	{
		
		conf.readProp();
		ArrayList<Result> result = new ArrayList<Result>();
		IndexReader reader = IndexReader
				.open(FSDirectory.open(new File(conf.getIndexPath())));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);

		BufferedReader in = null;
		
		QueryParser parser = new QueryParser(Version.LUCENE_31, field, analyzer);
		/**
		 *  Reference : From Lucene Demo - for reading the query  
		 */
		while (true) {	

			//Check this part
			String line = queryString != null ? queryString : in.readLine();

			if (line == null || line.length() == -1) {
				break;
			}

			line = line.trim();
			if (line.length() == 0) {
				break;
			}

			Query query = parser.parse(line);
			System.out.println("Searching for: " + query.toString(field));

             boolean raw = false;
             result = doPagingSearch(in, searcher, query, hitsPerPage, raw,
					queryString == null);

			if (queryString != null) {
				break;
			}
		}
		searcher.close();
		reader.close();
		return result;
	}
	
	/**
	 * 
	 * Returns a list of Paginated Results
	 * 
	 * @param in
	 * @param searcher
	 * @param query
	 * @param hitsPerPage
	 * @param raw
	 * @param interactive
	 * @return
	 * @throws IOException
	 */

	public static ArrayList<Result> doPagingSearch(BufferedReader in,
			IndexSearcher searcher, Query query, int hitsPerPage, boolean raw,
			boolean interactive) throws IOException {

		ArrayList<Result> result = new ArrayList<Result>();
		
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
	
		System.out.println(numTotalHits + " total matching documents");

		int start = 0;
		int end = Math.min(numTotalHits, hitsPerPage);

		while (true) {
			

			end = Math.min(hits.length, start + hitsPerPage);

			for (int i = start; i < end; i++) {
				if (raw) { // output raw format
					System.out.println("doc=" + hits[i].doc + " score="
							+ hits[i].score);
					continue;
				}

				Document doc = searcher.doc(hits[i].doc);
				String path = doc.get("path");
				if (path != null) {
                   Result res = new Result();
                   
               /**
                * Result is an object with fields subject, to, from etc.
                * We create an array list which is returned
                */
      				String subject = doc.get("subject");
       				String to = doc.get("to");
       				String from = doc.get("from");
       		//		String cc = doc.get("Cc");

                   res.setPath(path);
                   res.setTo(to);
                   res.setFrom(from);
                   res.setSubject(subject);
                  



					
					result.add(res);
				} else {
					System.out.println((i + 1) + ". "
							+ "No path for this document");
				}

			}

			if (!interactive || end == 0) {
				break;
			}

			if (numTotalHits >= end) {
				boolean quit = false;
				while (true) {
					System.out.print("Press ");
					if (start - hitsPerPage >= 0) {
						System.out.print("(p)revious page, ");
					}
					if (start + hitsPerPage < numTotalHits) {
						System.out.print("(n)ext page, ");
					}
					System.out
							.println("(q)uit or enter number to jump to a page.");

					String line = in.readLine();
					if (line.length() == 0 || line.charAt(0) == 'q') {
						quit = true;
						break;
					}
					if (line.charAt(0) == 'p') {
						start = Math.max(0, start - hitsPerPage);
						break;
					} else if (line.charAt(0) == 'n') {
						if (start + hitsPerPage < numTotalHits) {
							start += hitsPerPage;
						}
						break;
					} else {
						int page = Integer.parseInt(line);
						if ((page - 1) * hitsPerPage < numTotalHits) {
							start = (page - 1) * hitsPerPage;
							break;
						} else {
							System.out.println("No such page");
						}
					}
				}
				if (quit)
					break;
				end = Math.min(numTotalHits, start + hitsPerPage);
			}
		}
		
		return result;
		
	}

	
}