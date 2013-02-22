package nl.tudelft.ir.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;

public class CreateIndexController {

	public static void indexDocs(IndexWriter writer, File file)
			throws IOException {
		/**
		 * Recursively call till file found
		 * 
		 */

		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();

				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						indexDocs(writer, new File(file, files[i]));
					}
				}
			} else {

				FileInputStream fis;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException fnfe) {
					return;
				}

				try {

					Document doc = new Document();

					/**
					 * 
					 * Path is a field because we need the reference to the mail
					 * Stored so that we can use it in our hyperlinks for
					 *
					 * 
					 */

					String to = "";
					String sCurrentLine;
					String from = "";
					String subject = "";
					String date = "";
					String cc = "";
					String content="";

					BufferedReader br = new BufferedReader(
							new InputStreamReader(fis, "UTF-8"));
					try {
						while ((sCurrentLine = br.readLine()) != null) {

							if (sCurrentLine.startsWith("To:")) {
								to = sCurrentLine.substring(4);
							} else if (sCurrentLine.startsWith("From:")) {
								from = sCurrentLine.substring(6);
							} else if (sCurrentLine.startsWith("Subject:")) {
								subject = sCurrentLine.substring(9);
							} else if (sCurrentLine.startsWith("Date:")) {
								date = sCurrentLine.substring(8);
							} else if (sCurrentLine.startsWith("Cc:")) {
								cc = sCurrentLine.substring(4);
							}
							else if(sCurrentLine.startsWith("Mime") || sCurrentLine.startsWith("Content") || sCurrentLine.startsWith("X") || sCurrentLine.startsWith("Message"))
							{
						
								//Do nothing
								//Cleaning the text mail
							}
							else if(sCurrentLine.startsWith("-----Original Message-----"))
							{
								//Old message 
								//Should not be in the body
								
								break;
							}
							else{
								content = content + sCurrentLine;
							}
						

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					Field pathField = new Field("path", file.getPath(),
							Field.Store.YES, Field.Index.NOT_ANALYZED);
					pathField.setIndexOptions(IndexOptions.DOCS_ONLY);
					doc.add(pathField);
					Field toField = new Field("to", to, Field.Store.YES,
							Field.Index.ANALYZED);
					doc.add(toField);
					Field FromField = new Field("from", from, Field.Store.YES,
							Field.Index.ANALYZED);
					doc.add(FromField);
					Field ccField = new Field("cc", cc, Field.Store.YES,
							Field.Index.ANALYZED);
					doc.add(ccField);
					Field subField = new Field("subject", subject,
							Field.Store.YES, Field.Index.ANALYZED);
					doc.add(subField);
					Field dateField = new Field("date",date,  Field.Store.YES, Field.Index.ANALYZED_NO_NORMS);
					doc.add(dateField);

					doc.add(new Field("contents",  content, Field.Store.NO, Field.Index.ANALYZED));


					/**
					 * 
					 * Indexed by contents for search in contents Not Stored
					 * 
					 */
					

					/**
					 * Add if the index is new, Update if index already exists
					 * 
					 */
					if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
						System.out.println("adding " + file);
						writer.addDocument(doc);
					} else {

						System.out.println("updating " + file);
						writer.updateDocument(new Term("path", file.getPath()),
								doc);
					}

				} finally {
					fis.close();
				}
			}
		}
	}

}
