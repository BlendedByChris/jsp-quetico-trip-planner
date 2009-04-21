<%-- 
    Document   : editCanoes
    Created on : Apr 21, 2009, 1:10:44 AM
    Author     : cleblanc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="jsp_quetico_trip_planner.*" %>
<%
    // Redirect if no trip information available
    if (session.getAttribute("tripInformation") == null)
        response.sendRedirect(
                "trip.jsp?flash=Please+enter+trip+information+first.");
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%=session.getAttribute("groupLeader") %></h1>
    </body>
</html>
