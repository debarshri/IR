<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="nl.tudelft.ir.*"
    import="javax.servlet.http.HttpServletRequest"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
try{
Config conf = new Config();
conf.readProp();
CreateIndex idx = new CreateIndex();
idx.indexGenUtils(conf.getDocsPath(), conf.getIndexPath(), conf.isCreate());
out.write("Creating Indexes");
}catch(Exception e)
{
	
e.printStackTrace();
}
%>

</body>
</html>