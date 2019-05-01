/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.sun.jdi.InvalidTypeException;

import searchresearch.model.Paper;

/**
 * Test cases for the back-end of Search Research
 * @author Michael Ilao
 * 
 */
class searchresearchTest {

	/**
	 * Test method for {@link searchresearch.model.searchresearch#getDataFile(searchresearch.model.searchresearch.DataCategory)}.
	 * Standard tests for functionality
	 */
	@Test
	void testGetDataFile() {
		assert(searchresearch.model.searchresearch.getDataFile(searchresearch.model.searchresearch.DataCategory.Biology) 
				== "data/BIOLOGY.xls");
		assert(searchresearch.model.searchresearch.getDataFile(searchresearch.model.searchresearch.DataCategory.English) 
				== "data/ENGLISH.xls");
		assert(searchresearch.model.searchresearch.getDataFile(searchresearch.model.searchresearch.DataCategory.Math) 
				== "data/MATH.xls");
		assert(searchresearch.model.searchresearch.getDataFile(searchresearch.model.searchresearch.DataCategory.Software) 
				== "data/SOFTWARE.xls");
	}

	/**
	 * Test method for {@link searchresearch.model.searchresearch#findPaper(java.lang.String, searchresearch.model.searchresearch.DataCategory)}.
	 * @throws InvalidTypeException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * Standard tests for functionality 
	 * Then test for boundary case where the searcher should ommit common words like "the, and, of etc"
	 */
	@Test
	void testFindPaper() throws FileNotFoundException, IOException, InvalidTypeException {
		try {
			Paper[] paper = searchresearch.model.searchresearch.findPaper("shakespeare", searchresearch.model.searchresearch.DataCategory.English);
			assert(paper[0].getTitle().equals("Shakespearean spatial rules"));
			assert(paper[0].getRank() == 5);
			assert(paper[1].getTitle().equals("Detecting the evolution of semantics and individual beliefs through statistical analysis of language use"));
			assert(paper[1].getRank() == 4);
			Paper[] paper1 = searchresearch.model.searchresearch.findPaper("the and be to her she", searchresearch.model.searchresearch.DataCategory.English);
			assert(paper1.length == 0);
			
			
		} catch (Exception e) {
			fail("Paper Not Found");
		}
		
	}

	/**
	 * Test method for {@link searchresearch.model.searchresearch#findAuthor(java.lang.String, searchresearch.model.searchresearch.DataCategory)}.
	 * Standard test for functionality 
	 * Test for boundary case where an author that does not exist is searched.
	 */
	@Test
	void testFindAuthor() {
		try {
			Paper[] paper = searchresearch.model.searchresearch.findAuthor("Reza Samavi", searchresearch.model.searchresearch.DataCategory.Software);
			assert(paper[0].getTitle().equals("A privacy framework for the personal web"));
			
		} catch (Exception e) {
			fail("Paper Not Found");
		}
		
		try {
			Paper[] paper1 = searchresearch.model.searchresearch.findAuthor("Not a real author", searchresearch.model.searchresearch.DataCategory.Software);
			fail("An exception should happen if the author is not called");
			
		} catch (Exception e) {
			assert(true);

		}
	}

}
