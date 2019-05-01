package searchresearch.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Donisius Wigie, Rohit Saily
 * Class containing methods which sets a 
 */
public class PaperRanker {
	
	private static int MINIMUM_RANK = 3;
	private static int EXACT_IN_TITLE_SCORE = 5;
	private static int EXACT_IN_SUMMARY_SCORE = 4;
	private static int KEYWORD_IN_TITLE_SCORE = 3;
	private static int KEYWORD_IN_SUMMARY_SCORE = 2;
	private static double REFERENCE_SCORE_WEIGHT = 0.25;
	
	private ReferenceGraph referenceNetwork;
	private List<Paper> ranked;
	
	/**
	 * Constructor for the PaperRanker class.
	 * Takes in a sequence of words as input from the user and a list of papers to search through.
	 * Ranks each paper according to the number of other papers which use the paper as reference,
	 * and by the number of key word matches in the abstract and title of the paper based on the given 
	 * input by the user.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param pattern Sequence of words given as input from the user to the program.
	 * @param Papers List of papers to search through and give a ranking.
	 * 
	 */
	public PaperRanker(String pattern, List<Paper> Papers) throws FileNotFoundException, IOException {
		ranked = new ArrayList<Paper>();
		preliminaryRank(pattern, Papers);
		referenceNetwork = new ReferenceGraph(ranked);
		referenceRank();
	}
	
	private void referenceRank() {
		for (Paper p : ranked) {
			ReferenceSearcher referrerFinder = new ReferenceSearcher(referenceNetwork, p);
			p.setRank(p.getRank() + (int) ((referrerFinder.getNumberOfConnectedPapers() - 1) * REFERENCE_SCORE_WEIGHT));
		}
	}
	
	private void preliminaryRank(String pattern, List<Paper> papers) {
		pattern = pattern.toLowerCase();
		String[] keywords = pattern.split(" ");
		keywords = removeBlackListed(keywords);
		
		for (Paper p : papers) {
			String title = p.getTitle().toLowerCase();
			String summary = p.getSummary().toLowerCase();
			int rank = 0;
			if (title.contains(pattern)) {
				rank += EXACT_IN_TITLE_SCORE;
			}
			if (summary.contains(pattern)) {
				rank += EXACT_IN_SUMMARY_SCORE;
			}
			for (String k : keywords) {
				if (p.getTitle().contains(k)) {
					rank += KEYWORD_IN_TITLE_SCORE;
				}
				if (p.getSummary().contains(k)) {
					rank += KEYWORD_IN_SUMMARY_SCORE;
				}
			}
			if (rank >= MINIMUM_RANK) {
				p.setRank(rank);
				ranked.add(p);
			}
			if(ranked.size() > 10000) {
				break;
			}
		}	
	}
	
	/**
	 * Getter for the papers which have been ranked.
	 * @return Array of paper objects which have been given a rank
	 * above the minimum rank.
	 */
	public Paper[] getRankedPapers() {
		Paper[] papers = new Paper[ranked.size()];
		return ranked.toArray(papers);
	}

	private String[] removeBlackListed(String[] pattern) {
		ArrayList<String> newPattern = new ArrayList<String>(Arrays.asList(pattern));
		String BLwords = "the,be,to,of,and,a,in,that,have,I,it,for,not,on,with,he,as,you,do,at,this,but,his,by,from,they,we,say,her,she,or,will,an,my,one,all,would,there,their,what,so,up,out,if,about,who,get,which,go,when,me,make,can,like,time,no,just,him,know,take,person,into,year,your,good,some,could,them,see,other,than,then,now,look,only,come,its,over,think,also,back,after,use,two,how,our,work,first,well,way,even,new,want,because,any,these,give,day,most,us";
		String[] blackListedWords = BLwords.split(",");
	
		ArrayList<String> blackListedWordsList = new ArrayList<String>(Arrays.asList(blackListedWords));

		for(String s : blackListedWordsList) {
			newPattern.removeAll(Collections.singleton(s));
		}
			
		String[] patternFinal = new String[newPattern.size()];
		return newPattern.toArray(patternFinal);
	}
}
