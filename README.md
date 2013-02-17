The application will follow a MVC architecture.  The indexed data is governed by the Model.  It encapsulates the
data on which queries are applied, along with generating result instances which can be seen as states.  The 
Controller is a bridge between the Model and the View modules.  It receives commands from the View and changes 
the state of the Model.  Finally, the View changes its display according to the current state of the Model. 

Dependencies:  

*Lucene
*SWT - for the view 
