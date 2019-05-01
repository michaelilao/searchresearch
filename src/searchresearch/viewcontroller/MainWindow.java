package searchresearch.viewcontroller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.naming.NameNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.sun.jdi.InvalidTypeException;

import searchresearch.model.Paper;
import searchresearch.model.searchresearch;
import searchresearch.model.searchresearch.DataCategory;
/**
 * MainWindow is the launchable UI of searchresearch that allows
 * users to search for reseasrch papers by database, topics and authors.
 * @author Michael Ilao
 * Libraries used in the UI com.ibm* and org.eclipse*
 *
 */
public class MainWindow {

	/**
	 *	Creates a shell for the UI
	 */
	protected Shell shell;
	
	/**
	 *	The selected category by the user
	 */
	private String categoryName;
	
	/**
	 *	The topic searched by the user
	 */
	private String searchPhrase;
	
	/**
	 *	A boolean to decide whether to open a window or not(based on if errors occured)
	 */
	private boolean openWindow;

	/***************************************************************************
	 * Main
	 ***************************************************************************/
	
	/**
	 *	The main method that opens the window
	 *	@param args is unused as their is no console arguments
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		openWindow = false;
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		shell.close();
		System.exit(0);
	}

	/**
	 * Create contents of the window. Sets labels for the search bars.
	 * Creates a drop down bar for the data bases
	 * Creates a a button to call the backend of code that searches and returns the papers
	 */
	protected void createContents() {
		
		shell = new Shell();
		shell.setSize(615, 411);
		shell.setText("searchresearch 1.0");
		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI Emoji", 16, SWT.BOLD));
		lblNewLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/data/searchbutton.jpg"));
		lblNewLabel.setBounds(185, 20, 203, 66);
		lblNewLabel.setText("searchresearch");
		
		Button searchButton = new Button(shell, SWT.NONE);
		searchButton.setBounds(233, 230, 90, 30);
		searchButton.setText("Search!");
		
		
		Text topics = new Text(shell, SWT.BORDER);
		topics.setBounds(204, 187, 155, 26);
		
		Label lblDataBases = new Label(shell, SWT.NONE);
		lblDataBases.setBounds(104, 121, 76, 20);
		lblDataBases.setText("Category:");
		
		Label lblTopics = new Label(shell, SWT.NONE);
		lblTopics.setBounds(144, 190, 45, 20);
		lblTopics.setText("Topic:");
		
		Label lblAuthors = new Label(shell, SWT.NONE);
		lblAuthors.setBounds(125, 155, 70, 20);
		lblAuthors.setText("Author:");
		
		Text Author = new Text(shell, SWT.BORDER);
		Author.setBounds(204, 155, 155, 26);

		Combo combo = new Combo(shell, SWT.NONE);
		combo.setBounds(204, 118, 155, 28);
		for(DataCategory c : searchresearch.DataCategory.values()) {
			combo.add(c.name());
		}
        combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int idx = combo.getSelectionIndex();
                categoryName = combo.getItem(idx);
            }
        });
		
		searchButton.addListener(SWT.Selection, new Listener() {
			/**
			 * When the button is clicked get the input from the search bars, check if
			 * the inputs are all valid, if they are open up a paperwindow containing a 
			 * table of all the papers ranked. If not open an error window.
			 * @param event is the event of the button being clicked
			 */
            public void handleEvent(Event event) {
            	searchPhrase = topics.getText();
                String author = Author.getText();
                if (categoryName == null || categoryName.isEmpty()) {
                	openWindow = false;
                	
                    String errorMsg = null;
                    MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
 
                    messageBox.setText("Alert");
                    if (categoryName == null || categoryName.isEmpty()) {
                        errorMsg = "Please enter Data bases";
                
                    if (errorMsg != null) {
                        messageBox.setMessage(errorMsg);
                        messageBox.open();
                        
                    }
                    }
                }
                else {

                    
                  
                    Paper[] papers = new Paper[0];
        
                    try {
                    	if (author.isBlank() && searchPhrase.isBlank()) {
                    		MessageBox errorBox = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
                    		String errorMsg = "Please Enter Topic or Author";
                    		errorBox.setMessage(errorMsg);
                    		errorBox.open();
                    		openWindow = false;
                    		
                    	} else if (!searchPhrase.isBlank() ) {
                    		MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_WORKING);
                            messageBox.setText("Info");
                            messageBox.setMessage("Searching for " + searchPhrase + " in " + categoryName);
                            messageBox.open();
                            
                    		openWindow = true;

                    		papers = searchresearch.findPaper(searchPhrase, searchresearch.DataCategory.valueOf(categoryName));
                    		
						} else if (!author.isBlank()){
							MessageBox messageBox = new MessageBox(shell, SWT.OK | SWT.ICON_WORKING);
                            messageBox.setText("Info");
                            messageBox.setMessage("Searching for " + author + " in " + categoryName);
                            messageBox.open();
                            
                    		openWindow = true;

                    		papers = searchresearch.findAuthor(author, searchresearch.DataCategory.valueOf(categoryName));

                    		
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (NameNotFoundException e) {
						;
					} catch (InvalidTypeException e) {
						e.printStackTrace();
					}
            		
                    if(openWindow)
                    {
                        PaperWindow bc = new PaperWindow(papers);
                		bc.setVisible(true);
                    }

                }
            }
        });

}

}
