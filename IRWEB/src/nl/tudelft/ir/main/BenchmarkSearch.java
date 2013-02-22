package nl.tudelft.ir.main;

import nl.tudelft.ir.model.IndexSearch;

public class BenchmarkSearch {
	/**
	 * 
	 * 
	 * Benchamarking for Report
	 * 
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			IndexSearch idx = new IndexSearch();
			String[] field =  {"contents","subject","to","from"};
			String[] query = {"(agreement) AND (hello)", "anne","an*e","(rubeli) AND (hi)"};
			
			for(int i=0;i < field.length;i++)
			{
				for(int j=0;j < query.length;j++)
				{
					System.out.println("Query "+field[i]+" "+query[j]);
					idx.Search(field[i], query[j], 10);
                    System.out.println(idx.getTimeTaken());
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
