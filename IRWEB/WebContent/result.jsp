<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="nl.tudelft.ir.*"
    import="javax.servlet.http.HttpServletRequest"
    import="java.util.*"
    import="nl.tudelft.ir.types.*"
    
    
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String query = request.getParameter("q");
String queryType = request.getParameter("queryType");
String field = request.getParameter("Field");
int pageSize = Integer.parseInt(request.getParameter("pageSize"));

IndexSearch idx = new IndexSearch();
ArrayList<Result> res = idx.Search(field, query, pageSize);
for (int i = 0; i < res.size(); i++) {
	out.print(res.get(i).getPath()+"<br />");
}


%>
</body>
</html>