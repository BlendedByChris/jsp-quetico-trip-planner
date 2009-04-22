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
    TripValidator validator = new TripValidator(request, session);

    if (request.getParameter("flashError") != null)
        validator.setError(request.getParameter("flashError"));

    if (request.getMethod().equals("POST")) {
        validator.validate();    

        if (!validator.hasErrors()) {
            validator.calculate();

            // Store the session variables
            session.setAttribute("txtGroupLeader", validator.getParameter("txtGroupLeader"));
            session.setAttribute("txtTotalGuests", validator.getParameter("txtTotalGuests"));
            session.setAttribute("txtAdults", validator.getParameter("txtAdults"));
            session.setAttribute("txtChildren", validator.getTripChildren());

            session.setAttribute("txtStartDateMonth", validator.getParameter("txtStartDateMonth"));
            session.setAttribute("txtStartDateDay", validator.getParameter("txtStartDateDay"));
            session.setAttribute("txtStartDateYear", validator.getParameter("txtStartDateYear"));

            session.setAttribute("txtEndDateMonth", validator.getParameter("txtEndDateMonth"));
            session.setAttribute("txtEndDateDay", validator.getParameter("txtEndDateDay"));
            session.setAttribute("txtEndDateYear", validator.getParameter("txtEndDateYear"));

            session.setAttribute("startDate", validator.getStartDate());
            session.setAttribute("endDate", validator.getEndDate());
            session.setAttribute("chkTow", validator.getParameter("chkTow"));
            session.setAttribute("chkCanoeRental", validator.getParameter("chkCanoeRental"));
            session.setAttribute("radioPayment", validator.getParameter("radioPayment"));
            session.setAttribute("txtTripTotal", validator.getTripTotal());
            session.setAttribute("txtTripDuration", validator.getTripDuration());
            session.setAttribute("tripEntered", true);
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="css/screen.css">
        <script language="JavaScript">
            function validate(form) {
                rpChecked = false;
                for (var i=0; i < form.radioPayment.length; i++) {
                    if (form.radioPayment[i].checked) {
                        rpChecked = true;
                    }
                }
                if (rpChecked == false) {
                    alert("You must specify a payment type.");
                    return false;
                }
                return true;
            }
        </script>
    </head>
    <body>
        <h1>New/Edit Trip</h1>
        <table>
            <% if (validator.hasErrors()) { %>
            <ul id="error-summary">
                <% errorIterator = validator.getErrors().listIterator(); %>
                <% while(errorIterator.hasNext()) { %>
                <li><%=errorIterator.next() %></li>
                <% } %>
            </ul>
            <% } %>
            <form method="post" action="trip.jsp" onsubmit="return validate(this);">
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
                               value="<%=validator.getTripChildren() %>" readonly="readonly" disabled="disabled" /></td>
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
                                   value="<%=validator.getParameter("txtTripDuration") %>"
                               readonly="readonly" disabled="disabled" /></td>
                </tr>
                <tr>
                    <th><label for="txtTripTotal">Total Camping Fees:</label></th>
                    <td><input type="text" id="txtTripTotal" name="txtTripTotal"
                               value="<%=validator.getParameter("txtTripDuration") %>"
                               readonly="readonly" disabled="disabled" /></td>
                </tr>

                <tr>
                    <td align="right"><a href="index.jsp"><< Back</a></td>
                    <td><input type="submit" value="Calculate &amp; Save" /></td>
                </tr>

            </form>
        </table>
    </body>
</html>
