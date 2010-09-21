<%
  final String queryString = request.getQueryString();
  final String contextRoot = request.getContextPath();
  final String target      = contextRoot + "/sa/home.jsf";
  
  if (queryString != null && queryString.length() > 0) {
    response.sendRedirect(target + "?" + queryString);
  } 
  else {
    response.sendRedirect(target);
  }
%>
