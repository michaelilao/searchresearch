package searchresearch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.NameNotFoundException;

/**
 * 
 * @author Donisius Wigie
 * Class to search for papers written by a particular author.
 *
 */
public class AuthorSearcher {

	private HashMap<String, ArrayList<Paper>> authorToPaper;

	
	/**
	 * Constructor for AuthorSearcher.
	 * Takes in a list of papers to search from by author name.
	 * @param paperList List of paper objects to search through by author.
	 */
	public AuthorSearcher(List<Paper> paperList) {
		authorToPaper = new HashMap<String, ArrayList<Paper>>();
		for (Paper p : paperList) {
			for (String a : p.getAuthors()) {
				if (!a.isBlank()) {
					if (!authorToPaper.containsKey(a)) {
						authorToPaper.put(a, new ArrayList<Paper>());
						authorToPaper.get(a).add(p);
					} else {
						authorToPaper.get(a).add(p);
					}
				}

			}
		}

	}
	
	/**
	 * Getter function for AuthorSearcher
	 * Returns all the papers written by a particular author.
	 * @throws NameNotFoundException
	 * @param author Name of the author to search through the list of papers for.
	 * @return Array of papers written by the input author name.
	 */
	public Paper[] getPapers(String author) throws NameNotFoundException {
		if(!authorToPaper.containsKey(author)) {
			throw new NameNotFoundException("Could not find author name: " + author);
		}
		ArrayList<Paper> authors = authorToPaper.get(author);
		Paper[] papers = new Paper[authors.size()];
		return authors.toArray(papers);
	}
	
	
}