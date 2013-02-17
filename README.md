High level overview

The application will follow a MVC architecture.  The Model handles the data in the application.  It encapsulates the
data on which queries are applied to generate result instances which can be seen as states.  The 
Controller is a bridge between the Model and the View modules.  It receives commands from the View and changes 
the state of the Model.  Finally, the View changes its display according to the current state of the Model. 

Libraries that have to be added to the path:  

Lucene 3.6.2, SWT (http://www.eclipse.org/swt/  Releases section), Apache Commons FileUtils (http://archive.apache.org/dist/commons/io/)

Questions : 

<b>What would be an adequate GUI for this application?<\b> 
I can think of the following items that should be included: List of names of result
files after a query, a text area where the content of the e-mail will be displayed, a text box for writing the query.  
