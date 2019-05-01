package tests;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.naming.NameNotFoundException;

import com.sun.jdi.InvalidTypeException;

import searchresearch.model.Paper;
import searchresearch.model.searchresearch;
/**
 * 
 * @author Michael Ilao
 *	A simulation of searching for a paper by topic
 *	and searching for papers by author 
 */
public class Simulation {

	
	public static void main(String[] args) throws FileNotFoundException, IOException, InvalidTypeException, NameNotFoundException {
		
		Paper[] papers = searchresearch.findPaper("Cells", searchresearch.DataCategory.Biology);
		System.out.println("\n___SEARCHING FOR Cells IN BIOLOGY___\n");
		for(int i = 0; i<papers.length;i++)
		{
			System.out.println(papers[i].getTitle());
		}
		
		System.out.println("\n___SEARCHING FOR Reza Samavi IN Software___\n");
		papers = searchresearch.findAuthor("Reza Samavi", searchresearch.DataCategory.Software);
		for(int i = 0; i<papers.length;i++)
		{
			System.out.println(papers[i].getTitle());
		}
}
}
