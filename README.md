High level overview

The application will follow a MVC architecture.  The Model handles the data in the application.  It encapsulates the
data on which queries are applied to generate result instances which can be seen as states.  The 
Controller is a bridge between the Model and the View modules.  It receives commands from the View and changes 
the state of the Model.  Finally, the View changes its display according to the current state of the Model. 

Libraries that have to be added to the path:  

Lucene 3.6.2, SWT (http://www.eclipse.org/swt/  Releases section), Apache Commons FileUtils (http://archive.apache.org/dist/commons/io/)

<b>Project template

In the current status, the project contains the Model, View and Controller classes with some boilerplate code. The functionality
provided is similar to what we have to do for the IR project. I will proceed on describing it. 

You can type in the text field a path to a directory.  If you press "Go", in the left-down corner the list will be updated
with the files that are contained in that directory.  If you click on any of the entries in this list, it should
display its contents in the right half of the window.  

<Obtaining the project>
Either you download and import the exported project, either download sources and in eclipse use the "link source" option.

In either cases, check your build path to see if you have to also include the dependencies that are enumerated above.

Feel free to change stuff - like the directory path could be read from an environment variable that was established
prior to the GUI startup via a Python script, for example. 
