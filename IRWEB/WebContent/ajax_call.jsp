<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="nl.tudelft.ir.model.*"
	import="javax.servlet.http.HttpServletRequest" import="java.util.*"
	import="nl.tudelft.ir.types.*"
	import="nl.tudelft.ir.view.*"
	
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajax</title>
<link rel="stylesheet" type="text/css" media="screen" href="style.css"/>
</head>
<body>
	<div
		style="background: #f5f5f5; padding: 20px; color: #000; margin-left: -50%; margin-top: 30px; width: 200%;">
		<div></div>

		<div style="font-size: 10px;">
			<font style="font-weight:bold">Did you mean</font><br />
			<%
			String query = request.getParameter("q");
			String field = request.getParameter("fieldType");
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			View view = new View();
			ArrayList<Result> res = view.pageSearch(field, query, pageSize);
			for(int i = 0;i < 5;i++)
			{
				try{

					out.print(view.suggest()[i]+"&nbsp;&nbsp;");
	
				}catch(Exception e)
				{
					
				}
				
			}
			out.print("<br /><br /><b>Total Hits: </b>"+view.getNumOfHits());
			out.print("<br /><br /><b>Time Taken: </b>"+view.getTimeTaken() +" ms");


			%><br />
			<br />
			<br />
			<table align=center>
			<tr>
			<th>To </th>
			<th>From</th>
			<th>Subject</th>
			</tr>
	
			
			
			<%
				for (int i = 0; i < res.size(); i++) {
					out.print("<tr><td>"+ res.get(i).getTo() + "</td><td>"+ res.get(i).getFrom()+"</td><td><a href=read.jsp?q=" + res.get(i).getPath() + ">" +res.get(i).getSubject() +"</a></tr>");
				}
			%>
			
			</table>

		</div>

	</div>
</body>
</html>