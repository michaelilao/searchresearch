package searchresearch.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
  * @author Rohit Saily
  * Directed Graph to represent the connections between papers in terms of references.
  */
public class ReferenceGraph {

	private HashMap<Paper, List<Paper>> referrersByReferenced;
	private HashMap<String, Paper> paperById;
	
	/**
	  * Constructor for the directed reference graph, initializes the 
	  * referrersByReferenced HashMap and paperById HashMap
	  */
	public ReferenceGraph() {
		referrersByReferenced = new HashMap<Paper, List<Paper>>();
		paperById = new HashMap<String, Paper>();
	}
	
	/**
	  * Constructor for the directed reference graph.
	  * @param papers List of papers to create the graph from.
	  */
	public ReferenceGraph(List<Paper> papers) {
		referrersByReferenced = new HashMap<Paper, List<Paper>>();
		paperById = new HashMap<String, Paper>();
		for (Paper referrer : papers) {
			paperById.put(referrer.getId(), referrer);
			for (String referencedId : referrer.getReferences()) {
				if (paperById.containsKey(referencedId)) {
					addReference(referrer, paperById.get(referencedId));
				}
			}
			referrersByReferenced.put(referrer, new ArrayList<Paper>());
		}
	}
	
	/**
	  * Adds an edge from the references paper to the referrer.
	  * @param referrer Paper which refers to the referenced paper.
	  * @param referenced Paper which the referrer refers to.
	  */
	public void addReference(Paper referrer, Paper referenced) {
		referrersByReferenced.get(referenced).add(referrer);
	}
	
	/**
	  * Gets the number of referrer for a particular paper.
	  * @param referenced Paper to get the number of referrer from.
	  * @return number of referrer from a given referenced paper.
	  */
	public int getNumberOfReferrers(Paper referenced) {
		return referrersByReferenced.get(referenced).size();
	}
	
	/**
 	  * Gets the papers in the graph
 	  * @return keys of the referrersByReferenced HashMap.
	  */
	public Collection<Paper> getPapers() {
		return referrersByReferenced.keySet();
	}
	
	/**
	  * Gets the adjacent papers to a given referenced paper.
	  * @param referenced The paper to get adjacent papers for.
	  * @return referrers of the given referenced paper.
	  */
	public Collection<Paper> getReferrers(Paper referenced) {
		return referrersByReferenced.get(referenced);
	}

}
