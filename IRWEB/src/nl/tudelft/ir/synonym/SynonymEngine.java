package nl.tudelft.ir.synonym;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import nl.tudelft.ir.model.CreateIndex;

public class SynonymEngine {
	
	String[] getSynonyms(String s) 
	{		
		//System.out.println("Size of hashmap " + hashmap.size());
		return CreateIndex.hashmap.get(s);
	}
}
