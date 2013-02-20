<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="style.css"/>
<title>Enron Mail Search</title>
<script>

function showHint(str)
{
var xmlhttp;
var queryType = document.getElementById("querySelect").value;
var fieldType = document.getElementById("fieldSelect").value;
var pageSize = document.getElementById("pageSizeSelect").value;


if (str.length==0)
  { 
  document.getElementById("txtHint").innerHTML="";
  return;
  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET","ajax_call.jsp?q="+str+"&queryType="+queryType+"&fieldType="+fieldType+"&pageSize="+pageSize,true);
xmlhttp.send();
}


</script>
</head>
<body>
<div style="background:#000;color:#fff;padding:10px;font-weight:bold;font-size:18px;margin:5px;" > Enron Mail Search</div>

	<form class="formstyle" name="input" style="width:40%;margin-left:30%;margin-top:50px;" action="result.jsp" method="post"> 
        <input  class="input-text" name="q" type="text" id="txt1" onkeyup="showHint(this.value)" onfocus="showHint(this.value)" />
        <input class="Search-Button" type="Submit" value="Go"> 
       <br /><br /> 
        <select id="querySelect" name="queryType" style="border-style:solid;border-width:1px;border-color:#eee;background:#f5f5f5;">
  <option value="boolean">Boolean Query</option>
  <option value="Phrase">Phrase Query</option>
  <option value="Wildcard">Wild Card Query</option>
  <option value="Multiterm">Multi Term Query</option>
</select>


<select id="fieldSelect" name="Field" style="border-style:solid;border-width:1px;border-color:#eee;background:#f5f5f5;" >
  <option value="contents">Contents</option>
  <option value="inbox">Inbox</option>
  <option value="sent_item">Sent Items</option>
  <option value="del_item">Deleted Items</option>
    <option value="to">To</option>
      <option value="from">From</option>
        <option value="subject">Subject</option>
          <option value="date">Date</option>
  
</select>

<select id="pageSizeSelect" name="pageSize" style="border-style:solid;border-width:1px;border-color:#eee;background:#f5f5f5;width:12%;">
  <option value="10">10</option>
  <option value="20">20</option>
  <option value="50">50</option>
  <option value="100">100</option>
  
</select>
<br />
<br />
<div id="txtHint" ></div>

</form>

</body>
</html>