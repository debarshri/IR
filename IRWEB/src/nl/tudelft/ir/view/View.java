package nl.tudelft.ir.view;

import java.util.ArrayList;

import nl.tudelft.ir.model.CreateIndex;
import nl.tudelft.ir.model.IndexSearch;
import nl.tudelft.ir.types.Result;

public class View {
	private int numOfHits;
	private int currPage;
	private IndexSearch idx = new IndexSearch();
	private String timeTaken;

	public int getNumOfHits() {
		return numOfHits;
	}

	/**
	 * 
	 * 
	 * Call to controller
	 * 
	 * @param field
	 * @param queryString
	 * @param hitsPerPage
	 * @param start
	 * @param end
	 * @return
	 */

	
	public ArrayList<Result> pageSearch(String field, String queryString,
			int hitsPerPage) {

		ArrayList<Result> result = idx.Search(field, queryString, hitsPerPage);
		try{
		setNumOfHits(result.get(0).getTotalHitCount());
		if(Long.valueOf(idx.getTimeTaken()) != null)
		{

			setTimeTaken(Long.toString(idx.getTimeTaken()));
				
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Zero Hits");
		}
		return result;

	}

	/**
	 * 
	 * 
	 * 
	 * Create index Hook method
	 * 
	 * @param docsPath
	 * @param indexPath
	 * @param create
	 */
	
	
	public void createIndex() {
		CreateIndex idx = new CreateIndex();
		idx.indexGenUtils();
		setTimeTaken(idx.getTimeTaken());

	}

	/**
	 * 
	 * Getters and Setters of all the fields in View
	 * Suggest is for did you mean feature
	 * 
	 * @return
	 */
	public String[] suggest() {
		return idx.getDidYouMean();
	}

	public void setNumOfHits(int numOfHits) {
		this.numOfHits = numOfHits;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public String getTimeTaken() {
		return timeTaken;
	}

	
	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}

}
