package nl.tudelft.ir.synonym;


import java.io.Reader;
import java.util.Stack;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;
import java.io.IOException;


/**
 * This code implements a synonym analyzer; 
 * The code that I used comes from the book
 * @author Madalin
 */

public class SynonymAnalyzer extends Analyzer{
	private SynonymEngine engine;
	
	public SynonymAnalyzer (SynonymEngine engine)
	{
		this.engine = engine;
	}
	
	public String getVersion()
	{
		return "SynonymAnalyzer";
	}
	
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		TokenStream result = new SynonymFilter(
				new StopFilter(true,
						new LowerCaseFilter(
								new StandardFilter(
										new StandardTokenizer(
												Version.LUCENE_30,reader))),
												StopAnalyzer.ENGLISH_STOP_WORDS_SET),
				engine
				);
		return result;
	}
	
}


