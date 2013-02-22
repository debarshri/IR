package nl.tudelft.ir.types;

/**
 * 
 * This Class is a Result object used in the JSP pages to display is a
 * user-friendly format. We obtain a ArrayList of Results as output in
 * result.jsp
 * 
 * @author debarshi
 * 
 */

public class Result {

	private String subject = null;
	private String to = null;
	private String from = null;
	private String path = null;
	private int totalHitCount;
	private long timeTaken;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getTotalHitCount() {
		return totalHitCount;
	}

	public void setTotalHitCount(int totalHitCount) {
		this.totalHitCount = totalHitCount;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

}
