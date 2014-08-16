<%@ page import="java.util.*" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<head>
<title>A Search Page</title>
<style type="text/css">
body {
	text-align: center;
  background-color:#F2FADC; 
	min-width: 600px;
}

#wrapper {
	margin:0 auto;  
	width:600px;
	text-align: center;
  padding-top: 50px;
  padding-bottom: 50px; 
  background-color: white; 
}
</style>
</head>
<body>
<div id="wrapper">
<%! 
  class SearchEngine 
  {
    String name, pre, post; 
    public SearchEngine(String name, String pre, String post) 
    {
      this.name = name; 
      this.pre = pre; 
      this.post = post; 
    }
  }
  SearchEngine [] engines = 
  {
    new SearchEngine("ask", "http://www.ask.com/web?q=", "") , 
    new SearchEngine("gigablast", "http://gigablast.com/search?q=", ""), 
    new SearchEngine("dmoz", "http://search.dmoz.org/cgi-bin/search?search=",""), 
    new SearchEngine("live", "http://search.live.com/results.aspx?q=","&go=Search"),
    new SearchEngine("yahoo", "http://search.yahoo.com/search?p=",""), 
    new SearchEngine("exalead", "http://www.exalead.com/search/results?q=", ""),
    new SearchEngine("netscape", "http://search.netscape.com/search/search?invocationType=topsearchbox.webhome&query=","")
  };
  /*String [] engines = { "ask", "live", 
  "dmoz", "gigablast", "mozDex", "yahoo", 
  "exalead"}; */
%>
<applet code="SearchApplet.class" width=600 height=100>
  <b>This example requires a Java-enabled browser.</b>
</applet>

</div>
<%
if (request.getParameter("search") != null) 
{
%>


<%
String [] frames = {"engine1", "engine2", "engine3"};
for (String f : frames) 
{
  for (SearchEngine engine : engines) 
  {  
    if (request.getParameter(f) == null 
      || (request.getParameter(f) != null && 
        !engine.name.equals(request.getParameter(f)) )) continue; 
  %>
    <p>Search results from <%=engine.name %> </p>
    <iframe
    src ="<%=engine.pre%> 
        <%= URLEncoder.encode(request.getParameter("search"))%>
        <%=engine.post%>"
          width="100%"
          height="500px">
          </iframe>
  <%
  }
}
%>

<%}%>

</body>
</html>
