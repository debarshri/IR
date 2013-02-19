package nl.tudelft.ir.view;

import nl.tudelft.ir.controller.Controller;
import nl.tudelft.ir.model.Model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.CCombo;

/**
 * This is the View class.  It encapsulates the GUI where the user can
 * input queries, select query type and submit them to the controller. 
 * @author mcadariu
 *
 */
public class View {

	protected Shell shlEmailSearch;
	private Text queryInput;
	private Text resultDisplay;
	private Controller c;
	private Model m;
	private List resultList;
	private Combo queryType;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			View window = new View();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update the results text area
	 */
	public void updateResults(String text){
		
		if(text == null)
			return;
		
		resultDisplay.setText(text);
		
	}
	
	/**
	 * Update the list in the down-left corner.
	 */
	public void updateList(String[] results){
		
		if(results == null)
			return;
		
		if(results.length == 0)
			return;
		
		resultList.removeAll();
		
		for(String s:results){
			resultList.add(s);
		}
		
		resultList.redraw();
	}
	

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlEmailSearch.open();
		shlEmailSearch.layout();
		while (!shlEmailSearch.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		m = new Model(this);
		final Controller c = new Controller(m);
		
		shlEmailSearch = new Shell();
		shlEmailSearch.setSize(713, 439);
		shlEmailSearch.setText("Email search");
		shlEmailSearch.setLayout(null);
		
		Label lblQueryInput = new Label(shlEmailSearch, SWT.NONE);
		lblQueryInput.setBounds(10, 10, 121, 15);
		lblQueryInput.setText("Query Input:");
		
		queryInput = new Text(shlEmailSearch, SWT.BORDER);
		queryInput.setBounds(10, 31, 174, 25);
		
		Button btnSubmit = new Button(shlEmailSearch, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if(queryInput.getText()==null)
					return;
				if(queryInput.getText()=="")
					return;
				if(queryType.getSelectionIndex()<0)
					return;
							
				c.query(queryInput.getText(), 
					queryType.getSelectionIndex());
				
			}
		});
		btnSubmit.setBounds(275, 31, 79, 27);
		btnSubmit.setText("Submit");
		
		resultList = new List(shlEmailSearch, SWT.BORDER|SWT.V_SCROLL);
		resultList.setBounds(10, 91, 344, 309);
			
		resultList.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		        String[] selections = resultList.getSelection();
		        
		        if(selections.length > 1)
		        	return;
		        
		        c.inspectFile(selections[0]);
		        
		      }
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Label lblResultList = new Label(shlEmailSearch, SWT.NONE);
		lblResultList.setBounds(10, 70, 57, 15);
		lblResultList.setText("Result list:");
		
		Label label = new Label(shlEmailSearch, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(376, 10, 2, 390);
		
		Label label_1 = new Label(shlEmailSearch, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(10, 62, 368, 2);
		
		resultDisplay = new Text(shlEmailSearch, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		resultDisplay.setLayoutData(new GridData(GridData.FILL_BOTH));
		resultDisplay.setBounds(384, 10, 317, 390);
		
		Label lblQueryType = new Label(shlEmailSearch, SWT.NONE);
		lblQueryType.setBounds(190, 10, 79, 15);
		lblQueryType.setText("Query type:");
		
		queryType = new Combo(shlEmailSearch, SWT.NONE);
		queryType.setItems(new String[] {"Boolean", "Phrase"});
		queryType.setBounds(190, 31, 79, 27);

	}
}
