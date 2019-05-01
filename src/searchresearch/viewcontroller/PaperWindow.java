package searchresearch.viewcontroller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import searchresearch.model.Paper;
/**
 * Tutorial from https://www.youtube.com/watch?v=3LiSHPqbuic
 * @author Michael Ilao
 * Libraries used in the UI com.ibm* and org.eclipse*
 */
@SuppressWarnings("serial")
public class PaperWindow extends JFrame {
	
	/**
	 * Creates a new PaperWindow object that consists of a jtable with each paper's data
	 * @param papers an array of ranked papers returned by the back-end
	 *
	 */
	public PaperWindow(Paper[] papers) {
		super("Results");
		Object rowData[][] = new Object[papers.length][4];

		for (int i = 0; i < papers.length; i++) {
			String Authors = "";
			for (int j = 0; j < papers[i].getAuthors().length; j++) {
				Authors = Authors + ", " + papers[i].getAuthors()[j];
			}
			rowData[i][0] = papers[i].getTitle();
			rowData[i][1] = Authors;
			rowData[i][2] = papers[i].getRank();
			rowData[i][3] = papers[i].getSummary();
		}
		String columnHeaders[] = { "Title", "Author", "Score", "Abstract" };
		JTable table = new JTable(rowData, columnHeaders);

		table.setFont(new java.awt.Font("Arial Unicode MS", 0, 20));
		table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JTextField()));
		JScrollPane pane = new JScrollPane(table);
		getContentPane().add(pane);
		setSize(1000, 600);
		table.getTableHeader().setFont(new java.awt.Font("Arial Unicode MS", 0, 20));
		for (int i = 0; i < papers.length; i++)
			table.setRowHeight(i, 25);

	}

}

@SuppressWarnings("serial")
/**
 * Sets the button to opaque and sets the button label to the abstract
 */
class ButtonRenderer extends JButton implements TableCellRenderer {

	public ButtonRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean focused, int row,
			int col) {
		setText((obj == null) ? "" : obj.toString());
		return this;
	}

}

@SuppressWarnings("serial")
/**
 * Edits the buttons and allows them when clicked to open a window 
 */
class ButtonEditor extends DefaultCellEditor {
	protected JButton btn;
	private String lbl;
	private boolean clicked;

	public ButtonEditor(JTextField txt) {
		super(txt);

		btn = new JButton();
		btn.setOpaque(true);
		// when button is clicked
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int col) {
		lbl = (obj == null) ? "" : obj.toString();
		btn.setText(lbl);
		clicked = true;

		return btn;
	}

	@Override
	public Object getCellEditorValue() {
		if (clicked) {
			int lengthSentance = 0;
			String summary = "Abstract \n      ";
			for (int i = 0; i < lbl.length(); i++) {
				summary = summary + lbl.substring(i, i + 1);
				lengthSentance++;
				if (lbl.substring(i, i + 1).equals(" ") && lengthSentance > 80) {
					summary = summary + "\n";
					lengthSentance = 0;
				}

			}
			JOptionPane.showMessageDialog(btn, summary);
		}
		clicked = false;
		return new String(lbl);
	}

	@Override
	public boolean stopCellEditing() {
		clicked = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

}
