package nl.tudelft.ir.synonym;

import java.io.*;
import java.util.*;


import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Version;
import org.apache.lucene.document.*;

public final class Synonyms
{
	private static PrintStream o = System.out;
	private static PrintStream err = System.err;
	private static String PROLOG = "c:/Users/Madalin/Dropbox/TU Delft/Information Retrieval/Project/prolog/wn_s.pl";
	private static Map word2Nums =new HashMap();
	private static Map num2Words = new HashMap();
	private static Map synMap = new HashMap();
	
		
	private static void makeMaps()
	{
		String fn = PROLOG;
		try{
			FileInputStream	fis = new FileInputStream(fn);
		    DataInputStream dis = new DataInputStream( fis);
		
			String line;
			// maps a word to all the "groups" it's in
			int ndecent =0; // number of rejected words

			//status output
			int mod = 1;
			int row = 1;		
			// parse prolog file
				while ( ( line = dis.readLine()) != null)
				{
					String oline = line;
					// syntax check
					if ( ! line.startsWith( "s("))
					{
						err.println( "OUCH: "+ line);
						System.exit( 1);
					}
					// parse line
					line = line.substring( 2);
					int comma = line.indexOf( ',');
					String num = line.substring( 0, comma);
					int q1 = line.indexOf( '\'');
					line = line.substring( q1+1);
					int q2 = line.indexOf( '\'');
					String word = line.substring( 0, q2).toLowerCase();
					// make sure is a normal word
					if ( ! isDecent( word))
					{
						ndecent++;
						continue; // don't store words w/ spaces
					}
					// 1/2: word2Nums map
					// append to entry or add new one
					List lis =(List) word2Nums.get( word);
					if ( lis == null)
					{
						lis = new LinkedList();
						lis.add( num);
						word2Nums.put( word, lis);
						//o.println("lists : " + lis);
					}
					else
						lis.add( num);
					// 2/2: num2Words map 
					lis = (List) num2Words.get( num);
					if ( lis == null)
					{
						lis =new LinkedList();
						lis.add( word);
						num2Words.put( num, lis); 
					}
					else
						lis.add( word);
				}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map getSynMap() 
	{
		makeMaps();
		// first add an empty list for each key in the map to avoid having lists of lists
		Iterator itw1 = word2Nums.entrySet().iterator();
		while(itw1.hasNext())
		{
			Map.Entry pairs = (Map.Entry)itw1.next();
			synMap.put(pairs.getKey(),new LinkedList());
			//o.println("1" + pairs.getKey()+ "+" + pairs.getValue());
		}
		// now adding content to each list
		Iterator itw2 = word2Nums.entrySet().iterator();
		while (itw2.hasNext())
		{
			//To iterate through the list of each element in word2Nums
			Map.Entry pairs = (Map.Entry)itw2.next();
			List lis = (List)pairs.getValue();
			Iterator itn = lis.iterator();
			List lst = new LinkedList();	
			while (itn.hasNext())
			{		
				lst = (List)synMap.get(pairs.getKey());
				List lst1 = (List)num2Words.get(itn.next()); // each entry is another list
				Iterator i = lst1.iterator();
				while (i.hasNext())
				{	
					String syn = (String)i.next();
					if (!lst.contains(syn))
						lst.add(syn);
				}
				synMap.put(pairs.getKey(),lst);			
			}
		}
		return synMap;
	}

	public int getNrSyns()
	{
		return synMap.size();
	}
	private static boolean isDecent( String s)
	{
		int len = s.length();
		for ( int i = 0; i < len; i++)
			if ( ! Character.isLetter( s.charAt( i)))
				return false;
		return true;
	}
	
	public static void writeHashMap()
	{
	      Map map = new HashMap();
	      map = getSynMap();
	      o.println("the size of the synmap is" + map.size());
	      
	      HashMap<String,List> hashmap = new HashMap<String, List>();
	      hashmap = (HashMap<String, List>) map;
	      
	      File file = new File("c:/Users/Madalin/Dropbox/TU Delft/Information Retrieval/Project/hash.txt");
	      try
	      {
	         BufferedWriter bw = new BufferedWriter(new FileWriter(file));
	         for(String p: hashmap.keySet() )
	         {
	            List lst = new LinkedList();
	            lst = hashmap.get(p);
	            Iterator it = lst.iterator();
	            bw.write(p + "-");
	            while(it.hasNext())
	            {
	            	bw.write(it.next() + ",");//hashmap.get(p));
	            }
	            bw.newLine();
	         }
	         bw.flush();
	         bw.close();
	      }catch (IOException e){
	    	  o.println("error");
	      }
     
	}
}
	