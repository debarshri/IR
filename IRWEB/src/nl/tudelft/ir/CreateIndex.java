package nl.tudelft.ir;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;


public class CreateIndex {
  
  public void indexGenUtils(String docsPath, String indexPath,boolean create)
  {
	    final File docDir = new File(docsPath);
	    if (!docDir.exists() || !docDir.canRead()) {
	      System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
	      System.exit(1);
	    }
	  Date start = new Date();
	    try {
	      System.out.println("Indexing to directory '" + indexPath + "'...");
	      
	      Directory dir = FSDirectory.open(new File(indexPath));
	      Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
	      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_31, analyzer);

	      if (create) {
	        iwc.setOpenMode(OpenMode.CREATE);
	      } else {
	        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	      }

	     
	      // iwc.setRAMBufferSizeMB(256.0);

	      IndexWriter writer = new IndexWriter(dir, iwc);
	      indexDocs(writer, docDir);

	   
	      // writer.forceMerge(1);

	      writer.close();

	      Date end = new Date();
	      System.out.println(end.getTime() - start.getTime() + " total milliseconds");

	    } catch (IOException e) {
	      System.out.println(" caught a " + e.getClass() +
	       "\n with message: " + e.getMessage());
	    }
  }
 


  static void indexDocs(IndexWriter writer, File file)
    throws IOException {
   
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

          
          Field pathField = new Field("path", file.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
          pathField.setIndexOptions(IndexOptions.DOCS_ONLY);
          doc.add(pathField);

      
          doc.add(new Field("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));

          if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
            System.out.println("adding " + file);
            writer.addDocument(doc);
          } else {
           
            System.out.println("updating " + file);
            writer.updateDocument(new Term("path", file.getPath()), doc);
          }
          
        } finally {
          fis.close();
        }
      }
    }
  }
}