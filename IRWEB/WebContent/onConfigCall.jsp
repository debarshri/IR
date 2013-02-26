<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    
    import="nl.tudelft.ir.model.*"
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

String docsPath = request.getParameter("docsPath");
String indexPath = request.getParameter("indexPath");
boolean create = Boolean.parseBoolean(request.getParameter("create"));
boolean spellCheck = Boolean.parseBoolean(request.getParameter("spellCheck"));

Config conf = new Config();
conf.writeProp(docsPath, indexPath, create,spellCheck);

out.write(conf.getDocsPath());


%>
<div style="background:#000;color:#fff;padding:10px;font-weight:bold;font-size:18px;margin:5px;" > Enron Mail Search</div>

<div style="padding:100px;margin-left:30%;">
Configurations have been set
<br /><br />
Click <a style="background:#f5f5f5;padding:2px;color:#000;" href="writeIdx.jsp" >here</a> to create Indexes 

</div>

</body>
</html>