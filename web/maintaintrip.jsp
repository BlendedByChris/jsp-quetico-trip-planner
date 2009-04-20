<%-- 
    Document   : MaintainTrip
    Created on : Apr 15, 2009, 7:03:02 PM
    Author     : cleblanc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="jsp_quetico_trip_planner.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.ListIterator" %>

<%!
    List errors;
    ListIterator errorIterator;
%>

<%
    ValidatorTrip validator = new ValidatorTrip(request);

    if (request.getMethod().equals("POST")) {
        validator.validate();
        errors = validator.getErrors();
        errorIterator = errors.listIterator();
    }
%>



<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="css/screen.css">
    </head>
    <body>
        <table>
            <% if (validator.hasErrors()) { %>
            <ul id="error-summary">
                <% while(errorIterator.hasNext()) { %>
                <li><%=errorIterator.next() %></li>
                <% } %>
            </ul>
            <% } %>
            <form method="post" action="">
                <tr>
                    <th><label for="txtGroupLeader">Group Leader Name:</label></th>
                    <td><input type="text" id="txtGroupLeader" name="txtGroupLeader" value="" /></td>
                </tr>
                <tr>
                    <th><label for="txtTotalGuests">Total Guests:</label></th>
                    <td><input type="text" id="txtTotalGuests" name="txtTotalGuests" value="" /></td>
                </tr>
                <tr>
                    <th><label for="txtAdults">Adults:</label></th>
                    <td><input type="text" id="txtAdults" name="txtAdults" value="" /></td>
                </tr>
                <tr>
                    <th><label for="txtChildren">Children:</label></th>
                    <td><input type="text" id="txtChildren" name="txtChildren" value="" /></td>
                </tr>
                <tr>
                    <th><label for="txtStartDate">Start Date:</label></th>
                    <td><input type="text" id="txtStartDate" name="txtStartDate" value="" /></td>
                </tr>
                <tr>
                    <th><label for="txtEndDate">End Date:</label></th>
                    <td><input type="text" id="txtEndDate" name="txtEndDate" value="" /></td>
                </tr>
                <tr>
                    <th>Additional Equipment:</th>
                    <td>
                        <input type="checkbox" id="chkTow" name="chkTow" value="1" /><label for="chkTow">Tow</label><br />
                        <input type="checkbox" name="chkCanoeRental" value="1" /><label for="chkCanoeRental">Canoe Rental</label>
                    </td>
                </tr>
                <tr>
                    <th>Payment Type:</th>
                    <td>
                        <input type="radio" name="radioPayment" value="0" /><label>Cash</label>
                        <input type="radio" name="radioPayment" value="1" /><label>Credit Card</label><br />
                        <input type="radio" name="radioPayment" value="2" /><label>Check</label>
                        <input type="radio" name="radioPayment" value="3" /><label>Other</label><br />
                    </td>
                </tr>
                <tr>
                    <th><label for="txtTripDuration">Trip Duration:</label></th>
                    <td><input type="text" id="txtTripDuration" name="txtTripDuration" value="" readonly="readonly" disabled="disabled" /></td>
                </tr>
                <tr>
                    <th><label for="txtTotalCampingFees">Total Camping Fees:</label></th>
                    <td><input type="text" id="txtTotalCampingFees" name="txtTotalCampingFees" value="" readonly="readonly" disabled="disabled" /></td>
                </tr>

                <tr>
                    <td align="right"><input type="submit" value="Calculate" />
                    <td align="left"><input type="reset" value="Reset" />
                </tr>

            </form>
        </table>
    </body>
</html>
