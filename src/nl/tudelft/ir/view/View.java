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

public class View {

	protected Shell shlEmailSearch;
	private Text directoryDisplay;
	private Text queryInput;
	private Text resultDisplay;
	private Controller c;
	private Model m;
	private List resultList;

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
	 * Update the list 
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
		
		directoryDisplay = new Text(shlEmailSearch, SWT.BORDER);
		directoryDisplay.setBounds(10, 29, 174, 25);
		
		Label lblNewLabel = new Label(shlEmailSearch, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 174, 15);
		lblNewLabel.setText("Input Selection:");
		
		Button btnBrowse = new Button(shlEmailSearch, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				
				
			}
		});
		btnBrowse.setBounds(190, 27, 79, 27);
		btnBrowse.setText("Browse");
		
		Button btnIndex = new Button(shlEmailSearch, SWT.NONE);
		btnIndex.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
							
				c.setDirectoryPath(directoryDisplay.getText());
				
			}
		});
		btnIndex.setBounds(275, 27, 79, 27);
		btnIndex.setText("Index");
		
		Label lblQueryInput = new Label(shlEmailSearch, SWT.NONE);
		lblQueryInput.setBounds(10, 79, 121, 15);
		lblQueryInput.setText("Query Input:");
		
		queryInput = new Text(shlEmailSearch, SWT.BORDER);
		queryInput.setBounds(10, 100, 174, 25);
		
		Button btnSubmit = new Button(shlEmailSearch, SWT.NONE);
		btnSubmit.setBounds(190, 100, 79, 27);
		btnSubmit.setText("Submit");
		
		resultList = new List(shlEmailSearch, SWT.BORDER);
		resultList.setBounds(10, 191, 344, 209);
		
		
		
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
		lblResultList.setBounds(10, 170, 57, 15);
		lblResultList.setText("Result list:");
		
		Label label = new Label(shlEmailSearch, SWT.SEPARATOR | SWT.VERTICAL);
		label.setBounds(376, 10, 2, 390);
		
		Label label_1 = new Label(shlEmailSearch, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setBounds(10, 150, 368, 2);
		
		resultDisplay = new Text(shlEmailSearch, SWT.BORDER);
		resultDisplay.setBounds(384, 10, 317, 390);

	}
}
