<%-- 
    Document   : MaintainTrip
    Created on : Apr 15, 2009, 7:03:02 PM
    Author     : cleblanc
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="jsp_quetico_trip_planner.*" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.ListIterator" %>

<%!
    List errors;
    ListIterator errorIterator;
%>

<%
    TripValidator validator = new TripValidator(request);
    TripCalculator calculator = new TripCalculator(request);

    errors = validator.getErrors();

    if (request.getMethod().equals("POST")) {
        validator.validate();    

        if (!validator.hasErrors()) {
            calculator.calculate();

            // Store the session variables
            session.setAttribute("groupLeader", validator.getParameter("txtGroupLeader"));
            session.setAttribute("totalGuests", validator.getParameter("txtTotalGuests"));
            session.setAttribute("adults", validator.getParameter("txtAdults"));
            session.setAttribute("children", calculator.getTripChildren());
            session.setAttribute("startDate", calculator.getFormattedStartDate());
            session.setAttribute("endDate", calculator.getFormattedEndDate());
            session.setAttribute("tow", validator.getParameter("chkTow"));
            session.setAttribute("canoeRental", validator.getParameter("chkCanoeRental"));
            session.setAttribute("payment", validator.getParameter("radioPayment"));
            session.setAttribute("tripTotal", calculator.getTripTotal());
            session.setAttribute("tripDuration", calculator.getTripDuration());
        }
    }
%>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="css/screen.css">
    </head>
    <body>
        <h1>New/Edit Trip</h1>
        <table>
            <% if (validator.hasErrors()) { %>
            <ul id="error-summary">
                <% errorIterator = errors.listIterator(); %>
                <% while(errorIterator.hasNext()) { %>
                <li><%=errorIterator.next() %></li>
                <% } %>
            </ul>
            <% } %>
            <form method="post" action="trip.jsp">
                <tr>
                    <th><label for="txtGroupLeader">Group Leader Name:</label></th>
                    <td><input type="text" id="txtGroupLeader"
                               name="txtGroupLeader"
                               value="<%=validator.getParameter("txtGroupLeader") %>" /></td>
                </tr>
                <tr>
                    <th><label for="txtTotalGuests">Total Guests:</label></th>
                    <td><input type="text" id="txtTotalGuests" 
                               name="txtTotalGuests"
                               value="<%=validator.getParameter("txtTotalGuests") %>" /></td>
                </tr>
                <tr>
                    <th><label for="txtAdults">Adults:</label></th>
                    <td><input type="text" id="txtAdults" name="txtAdults" 
                               value="<%=validator.getParameter("txtAdults") %>" /></td>
                </tr>
                <tr>
                    <th><label for="txtChildren">Children:</label></th>
                    <td><input type="text" id="txtChildren" name="txtChildren"
                               value="<%=calculator.getTripChildren() %>" readonly="readonly" disabled="disabled" /></td>
                </tr>
                <tr>
                    <th><label for="txtStartDateMonth">Start Date:</label></th>
                    <td>
                        <input type="text" id="txtStartDateMonth"
                               name="txtStartDateMonth" 
                               value="<%=validator.getParameter("txtStartDateMonth") %>"
                               maxlength="2" size="2" /> /
                        <input type="text" id="txtStartDateDay"
                               name="txtStartDateDay" maxlength="2"
                               value="<%=validator.getParameter("txtStartDateDay") %>"
                               size="2" /> /
                        <input type="text" id="txtStartDateYear"
                               name="txtStartDateYear"
                               maxlength="4" size="4"
                               value="<%=validator.getParameter("txtStartDateYear") %>" />
                    </td>
                </tr>
                <tr>
                    <th><label for="txtEndDate">End Date:</label></th>
                    <td>
                        <input type="text" id="txtEndDateMonth"
                               name="txtEndDateMonth" maxlength="2"
                               size="2" 
                               value="<%=validator.getParameter("txtEndDateMonth") %>" /> /
                        <input type="text" id="txtEndDateDay"
                               name="txtEndDateDay" maxlength="2"
                               size="2"
                               value="<%=validator.getParameter("txtEndDateDay") %>" /> /
                        <input type="text" id="txtEndDateYear"
                               name="txtEndDateYear"
                               maxlength="4" size="4"
                               value="<%=validator.getParameter("txtEndDateYear") %>" />                    </td>
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
                    <td><input type="text" id="txtTripDuration" name="txtTripDuration" 
                               value="<%=calculator.getTripDuration() %>" 
                               readonly="readonly" disabled="disabled" /></td>
                </tr>
                <tr>
                    <th><label for="txtTripTotal">Total Camping Fees:</label></th>
                    <td><input type="text" id="txtTripTotal" name="txtTripTotal"
                               value="<%=calculator.getTripTotal() %>"
                               readonly="readonly" disabled="disabled" /></td>
                </tr>

                <tr>
                    <td align="right"><input type="submit" value="Calculate" />
                    <td align="left"><input type="reset" value="Reset" />
                </tr>

            </form>
        </table>
    </body>
</html>
