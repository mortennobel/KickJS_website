<html>
<body>
<%
String id = request.getParameter("id");
if (id != null){
try{
    String shortS = org.kickjs.shared.URLShortener.getShortURL(id);
    out.print(shortS);
}catch (Exception e){
    e.printStackTrace();
    out.print(e);
}
}
%>
<form>
<input name="id" value="http://www.google.com/"><input type="submit">
</form>
</body>
</html>