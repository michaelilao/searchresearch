package searchresearch.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NameNotFoundException;

import com.sun.jdi.InvalidTypeException;

import edu.princeton.cs.algs4.QuickBentleyMcIlroy;

/**
 * 
 * @author Rohit Saily
 * Controller used by the user to manipulate the modules which manage the dataset, logic, and rules of 
 * the program.
 *
 */
public class searchresearch {

	/***************************************************************************
	 * Constants
	 ***************************************************************************/

	private final static String[] DATA_GROUPS = { "Biology", "English", "Math", "Software" };
	private final static String[] DATA_FILE_PATHS = { "data/BIOLOGY.xls", "data/ENGLISH.xls", "data/MATH.xls",
			"data/SOFTWARE.xls" };
	@SuppressWarnings("serial")
	private final static HashMap<DataCategory, String> DATA_FILE_PATHS_BY_CATEGORY = new HashMap<DataCategory, String>() {
		{
			put(DataCategory.Biology, "data/BIOLOGY.xls");
			put(DataCategory.English, "data/ENGLISH.xls");
			put(DataCategory.Math, "data/MATH.xls");
			put(DataCategory.Software, "data/SOFTWARE.xls");
		}
	};

	/**
	 * 
	 * Enumerated type representing the four categories that the dataset is split up into.
	 *
	 */

	public static enum DataCategory {
		Biology, English, Math, Software;
	}

	/***************************************************************************
	 * Persistent for running time + init
	 ***************************************************************************/

	private static HashMap<DataCategory, List<Paper>> papersByCategory = new HashMap<DataCategory, List<Paper>>();
	private static HashMap<DataCategory, AuthorSearcher> authourSearcherByCategory = new HashMap<DataCategory, AuthorSearcher>();

	/***************************************************************************
	 * searchresearch API
	 ***************************************************************************/


	/**
	 * Method to obtain the data path of the category specified by the user as input.
	 * 
	 * @param c Category to obtain file path of.
	 * @return File path of the category specified by the user as input.
	 */
	public static String getDataFile(DataCategory c) {
		return DATA_FILE_PATHS_BY_CATEGORY.get(c);
	}
	/**
	 * Method to find papers based on the sequence of words provided as input by the user and 
	 * the category to search through.
	 * 
	 * @param pattern Sequence of words provided by the user as input.
	 * @param c Data category to search papers through.
	 * @return List of papers which are likely to be relevant to the user input.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidTypeException
	 */
	public static Paper[] findPaper(String pattern, DataCategory c)
			throws FileNotFoundException, IOException, InvalidTypeException {
		if (!papersByCategory.containsKey(c)) {
			String path = DATA_FILE_PATHS_BY_CATEGORY.get(c);
			XLSTable paperData = new XLSTable(path);
			papersByCategory.put(c, initPapers(paperData));

		}
		PaperRanker ranker = new PaperRanker(pattern, papersByCategory.get(c));
		Paper[] rankedPapers = ranker.getRankedPapers();
		QuickBentleyMcIlroy.sort(rankedPapers);
		return rankedPapers;
	}

	/**
	 * Method to find all papers that a particular author has been involved with in a 
	 * specified data category.
	 * @param author Name of the author to search for.
	 * @param c Data category to search papers through.
	 * @return List of all papers which the specified author is involved with in a given data category.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidTypeException
	 * @throws NameNotFoundException
	 */
	public static Paper[] findAuthor(String author, DataCategory c)
			throws FileNotFoundException, IOException, InvalidTypeException, NameNotFoundException {
		if (!authourSearcherByCategory.containsKey(c)) {
			String path = DATA_FILE_PATHS_BY_CATEGORY.get(c);
			XLSTable paperData = new XLSTable(path);
			List<Paper> papers = initPapers(paperData);
			AuthorSearcher a = new AuthorSearcher(papers);
			authourSearcherByCategory.put(c, a);

		}
		return authourSearcherByCategory.get(c).getPapers(author);
	}

	/***************************************************************************
	 * Helper functions
	 ***************************************************************************/

	private static List<Paper> initPapers(XLSTable source) throws InvalidTypeException {
		ArrayList<Paper> papers = new ArrayList<Paper>();
		for (int row = 1; row <= source.getMaxRow(); row++) {
			String title = source.getStringCellContent(row, 0).stripLeading().stripTrailing();
			String summary = source.getStringCellContent(row, 1).stripLeading().stripTrailing();
			String[] authors = formatListedString(source.getStringCellContent(row, 2));
			String[] references = formatListedString(source.getStringCellContent(row, 3));
			String id = source.getStringCellContent(row, 4).stripLeading().stripTrailing();
			papers.add(new Paper(title, summary, authors, references, id));
		}
		return papers;
	}

	private static String[] formatListedString(String list) {
		String formattingCharsToReplace = "[\\[\\'\\]]";
		String[] elements = list.replaceAll(formattingCharsToReplace, "").split(",");
		if (!elements[0].isBlank()) {
			for (int i = 0; i < elements.length; i++) {
				elements[i] = elements[i].stripLeading().substring(1).stripTrailing();
			}
		}
		return elements;
	}

}
