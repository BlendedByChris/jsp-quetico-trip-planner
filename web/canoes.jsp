<%-- 
    Document   : editCanoes
    Created on : Apr 21, 2009, 1:10:44 AM
    Author     : cleblanc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="jsp_quetico_trip_planner.*" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.ListIterator" %>
<%@page import="java.sql.ResultSet" %>

<%!
    List canoes;
    ListIterator canoeIterator;

    List<String> selectedCanoes = new ArrayList<String>();
    ListIterator selectedCanoesIterator;

    ResultSet canoe;
%>
<%
    // Redirect if no trip information available
    if (session.getAttribute("tripEntered") == null)
        response.sendRedirect(
                "trip.jsp?flashError=Please+enter+trip+information+first.");
    else if (session.getAttribute("canoeRental").equals(""))
        response.sendRedirect(
                "trip.jsp?flashError=Please+check+canoe+rentals+first.");

    // Canoes list
    CanoeInformer canoeInformer = new CanoeInformer();
    canoes = canoeInformer.getAllCanoes();
    canoeIterator = canoes.listIterator();

    // Selected Canoes
    if (session.getAttribute("selectedCanoes") != null)
        selectedCanoes = (List) session.getAttribute("selectedCanoes");

    if (request.getMethod() == "POST") {
        selectedCanoes.add(request.getParameter("selectCanoes"));
        session.setAttribute("selectedCanoes", selectedCanoes);
    }

    if (selectedCanoes != null)
        selectedCanoesIterator = selectedCanoes.listIterator();


%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/screen.css">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Select Canoes</h1>
        <form method="post" action="canoes.jsp">
        <table>
                <tr>
                    <th><label for="txtGroupLeader">Group Leader:</label></th>
                    <td><input type="text" id="txtGroupLeader" name="txtGroupLeader"
                               value="<%=session.getAttribute("groupLeader") %>" 
                               readonly="readonly" disabled="disabled" /></td>
                </tr>
                <tr>
                    <th><label for="txtTotalGuests">Total Guests:</label></th>
                    <td><input type="text" id="txtTotalGuests" name="txtTotalGuests"
                               value="<%=session.getAttribute("totalGuests") %>"
                               readonly="readonly" disabled="disabled" /></td>
                </tr>
                <tr>
                    <th><label for="selectCanoes">Select Canoes:</label></th>
                    <td>
                        <% if (canoes != null) { %>
                        <select name="selectCanoes">
                            <% while(canoeIterator.hasNext()) { %>
                            <option><%=canoeIterator.next() %></option>
                            <% } %>
                        </select>
                        <% } %>                        
                    </td>
                </tr>
                <tr>
                    <td align="right" colspan="2"><input type="submit"
                        value="Calculate" />
                </tr>
        </table>
</form>
        <table>
                <tr>
                    <th><label for="selectCanoes">Canoe:</label></th>
                    <td>
                        <% if (selectedCanoes.size() > 0) { %>
                            <% while(selectedCanoesIterator.hasNext()) { %>
                            <tr>
                            <% canoe = new CanoeDb().getCanoeByCanoe((String) selectedCanoesIterator.next()); %>
                                <td><%=canoe.getString("clCanoe") %></td>
                                <td><%=canoe.getString("clManufacturer") %></td>
                                <td><%=canoe.getString("clLayup") %></td>
                                <td><%=canoe.getString("clWeight") %></td>
                                <td><%=canoe.getString("clCapacity") %></td>
                            <% } %>
                            </tr>
                        <% } %>
                    </td>
                </tr>
        </table>
    </body>
</html>
