package nl.tudelft.ir.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import nl.tudelft.ir.types.Result;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class SearchIndexController {
	
	
	public static ArrayList<Result> doPagingSearch(BufferedReader in,
			IndexSearcher searcher, Query query, int hitsPerPage,
			boolean interactive) throws IOException {

		ArrayList<Result> result = new ArrayList<Result>();

		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;

		System.out.println(numTotalHits + " total matching documents");

		int start=1;
	
		int end = Math.min(numTotalHits, hitsPerPage);

		while (true) {

			end = Math.min(hits.length, start + hitsPerPage);

			for (int i = start; i < end; i++) {
			

				Document doc = searcher.doc(hits[i].doc);
				String path = doc.get("path");
				if (path != null) {
					Result res = new Result();
					res.setTotalHitCount(numTotalHits);

					/**
					 * Result is an object with fields subject, to, from etc. We
					 * create an array list which is returned
					 */
					String subject = doc.get("subject");
					String to = doc.get("to");
					String from = doc.get("from");
					// String cc = doc.get("Cc");
					if (subject != null || to != null || from != null) {
						res.setPath(path);
						res.setTo(to);
						res.setFrom(from);
						res.setSubject(subject);

						result.add(res);
					}
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
