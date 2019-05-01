package searchresearch.model;

/**
 * @author Rohit Saily, Donisius Wigie
 * A class to represent a paper object containing the title, summary,
 * authors, references, id and given rank of a paper.
 */
public class Paper implements Comparable<Paper>{
	private String title;
	private String summary;
	private String[] authors;
	private String[] references;
	private String id;
	private int rank;
	
	/**
	 * Constructor for the paper class.
	 * Takes as parameter the title, summary,
	 * authors, references, id and given rank of a paper
	 * and contructs the paper class with the given information.
	 * 
	 * @param title Title of the paper.
	 * @param summary Abstract of the paper.
	 * @param authors List of names who authored the paper.
	 * @param references List of IDs of papers the paper references.
	 * @param id ID of the paper.
	 */
	public Paper(String title, String summary, String[] authors, String[] references, String id) {
		this.title = title;
		this.summary = summary;
		this.authors = authors;
		this.references = references;
		this.id = id;
	}

	/**
	 * Getter for the title of the paper.
	 * @return title Title of the paper.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for the summary of the paper
	 * @return summary Abstract of the paper
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Getter for the list of authors who wrote the paper.
	 * @return list of author names who authored the paper.
	 */
	public String[] getAuthors() {
		return authors;
	}

	/**
	 * Getter for the references of the paper.
	 * @return List of IDs of the papers that the paper references.
	 */
	public String[] getReferences() {
		return references;
	}

	/**
	 * Getter for the ID of the paper.
	 * @return id ID of the paper.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Getter for the rank of the paper.
	 * @return rank Given rank of the paper, depends on the user input and number of papers
	 * which use the paper as reference.
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Setter for the rank of the paper.
	 * Sets the rank for the paper.
	 * @param rank the rank to be set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(Paper o) {
		return o.rank - this.rank;
	}
}
