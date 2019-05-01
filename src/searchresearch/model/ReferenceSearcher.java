package searchresearch.model;

import java.util.HashMap;

/**
  * @author Rohit Saily
  * Class containing methods to search through the reference graph and 
  * find all connected papers. Depth first search method inspired by the implementation on 
  * page 544 of the Algorithms Fourth Edition Textbook by Robert Sedgewick and Kevin Wayne.
  */
public class ReferenceSearcher {
	
	private HashMap<Paper, Boolean> isMarked;
	private int count;
	
	/**
	  * Constructor for ReferenceSearcher. Finds all connected papers 
	  * to a particular paper, that is, finds all papers which reference a particular paper.
	  * @param g reference graph to search through
	  * @param start Paper to find number of papers which use it as reference.
	  */
	public ReferenceSearcher(ReferenceGraph g, Paper start) {
		isMarked = new HashMap<Paper, Boolean>();
		for (Paper p : g.getPapers()) {
			isMarked.put(p, false);
		}
		dfs(g, start);
	}
	
	//Depth first search method inspired by the implementation on 
	//page 544 of the Algorithms Fourth Edition Textbook by Robert Sedgewick and Kevin Wayne.
	private void dfs(ReferenceGraph g, Paper referenced) {
		isMarked.put(referenced, true);
		count++;
		for (Paper referrer : g.getReferrers(referenced)) {
			if (!isMarked.get(referrer)) {
				dfs(g, referrer);
			}
		}
	}
	
	/**
	  * Gets the number of papers connected to a particular paper.
	  * @return count Number of papers which are referrers to the start paper.
	  */
	public int getNumberOfConnectedPapers() {
		return count;
	}

}
