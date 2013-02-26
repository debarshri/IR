<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="nl.tudelft.ir.view.View"
    import="javax.servlet.http.HttpServletRequest"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Enron Mail Search</title>
</head>
<body>
<%
try{
View view = new View();
view.createIndex();
out.write("Indexes Created <br /><br />");
}catch(Exception e)
{
e.printStackTrace();
}
%>

</body>
</html>