<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="style.css"/>
<title>Enron Mail Search</title>
<script>
</script>
</head>
<body>
<div style="background:#000;color:#fff;padding:10px;font-weight:bold;font-size:18px;margin:5px;" > Enron Mail Search</div>

	<form class="formstyle" name="input" style="width:40%;margin-left:30%;margin-top:50px;" action="onConfigCall.jsp" method="post"> 
       Document Path<br />
        <input  class="input-text" name="docsPath" type="text" />
            <br /><br />  Index Path<br />
              <input  class="input-text" name="indexPath" type="text"  />
<br /><br />Update
<select name="create" style="border-style:solid;border-width:1px;border-color:#eee;background:#f5f5f5;">
  <option value="true">True</option>
  <option value="false">False</option>

  
</select>
      <br /><br />
        <input class="Search-Button" type="Submit" value="Go"> 
       <br /><br /> 
    

</form>

</body>
</html>