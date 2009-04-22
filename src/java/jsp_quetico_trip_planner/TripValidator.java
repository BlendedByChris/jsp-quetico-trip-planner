package jsp_quetico_trip_planner;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * TripValidator
 *
 * @author cleblanc
 */
public class TripValidator {

    public HttpServletRequest request;
    public HttpSession session;
    public List<String> errors = new ArrayList<String>();

    // Per durations
    private long tripDuration = -1; // Hack for null
    private long tripTotal = -1; // Hack for null
    private int tripChildren = -1; // Hack for null

    // Per night fees
    private static long PERNIGHTFEEADULT = 20;
    private static long PERNIGHTFEECHILDREN = 8;

    // Date parser
    public SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * TripValidator
     * 
     * Takes a request object to be validated.
     * 
     * @param request
     */
    public TripValidator(HttpServletRequest request, HttpSession session) {
        this.request = request;
        this.session = session;
    }

    /**
     * Has Errors
     *
     * Returns whether the validator found an error.
     *
     * @return boolean
     */
    public boolean hasErrors() {
        if (errors.size() > 0)
            return true;
        else
            return false;
    }

    public void setError(String error)
    {
        errors.add(error);
    }

    /**
     * Get Errors
     *
     * Returns a collection of all the errors for the request.
     * @return
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Get Parameter
     *
     * Returns the request parameter or an empty string.
     *
     * @param parameter
     * @return parameter
     */
    public String getParameter(String parameter) {
        if (request.getParameter(parameter) != null)
            return request.getParameter(parameter);
        else if (session.getAttribute(parameter) != null)
            return (String) session.getAttribute(parameter);
        else
            return "";           
    }


    /**
     * Validate
     *
     * Calls each validation check method.
     */
    public void validate() {

        // Check for valid errors
        checkTxtGroupLeader();
        checkTxtTotalGuests();
        checkTxtAdults();
        checkTxtStartDateMonth();
        checkTxtStartDateDay();
        checkTxtStartDateYear();
        checkTxtEndDateMonth();
        checkTxtEndDateDay();
        checkTxtEndDateYear();
        checkRadioPayment();
        
        // Check for computational errors
        checkTripStartDate();
        checkTripEndDate();
    }

    private void checkTxtGroupLeader() {
        if (request.getParameter("txtGroupLeader").length() < 5)
            setError("Group leader must be greater than 5 characters.");
    }

    private void checkTxtTotalGuests() {
        try {
            int totalGuests = Integer.parseInt(
                    request.getParameter("txtTotalGuests"));
            if (totalGuests <= 1 || totalGuests >= 9)
                setError("Total guests must be between 1 and 9, inclusive.");
        } catch (NumberFormatException e) {
            setError("Total guests must be numeric.");            
        }
    }

    private void checkTxtAdults() {
        try {
            int totalGuests = Integer.parseInt(
                    request.getParameter("txtTotalGuests"));
            int adults = Integer.parseInt(
                    request.getParameter("txtAdults"));
            if (adults <= 1 || adults > totalGuests)
                setError("Adult must be between 1 and " + totalGuests +
                        " guests.");                
        } catch (NumberFormatException e) {
            setError("Adults &amp; total guests must be numeric.");            
        }
    }

    private void checkTxtStartDateMonth() {
        try {
            int txtStartDateMonth = Integer.parseInt(
                    request.getParameter("txtStartDateMonth"));
            if (txtStartDateMonth < 1 || txtStartDateMonth > 12)
                setError("Starting month must be between 1 and 12");
        } catch (NumberFormatException e) {
            setError("Starting month must be numeric.");
            
        }
    }

    private void checkTxtStartDateDay() {
        try {
            int txtStartDateDay = Integer.parseInt(
                    request.getParameter("txtStartDateDay"));
            if (txtStartDateDay < 1 || txtStartDateDay > 31)
                setError("Ending day must be between 1 and 31");
        } catch (NumberFormatException e) {
            setError("Starting day must be numeric.");            
        }
    }
    
    private void checkTxtStartDateYear() {
        try {
            Integer.parseInt(request.getParameter("txtStartDateYear"));
        } catch (NumberFormatException e) {
            setError("Starting year must be numeric.");            
        }
    }

    private void checkTxtEndDateMonth() {
        try {
            int txtEndDateMonth = Integer.parseInt(
                    request.getParameter("txtEndDateMonth"));
            if (txtEndDateMonth < 1 || txtEndDateMonth > 12)
                setError("Ending month must be between 1 and 12");
        } catch (NumberFormatException e) {
            setError("Ending month must be numeric.");            
        }
    }

    private void checkTxtEndDateDay() {
        try {
            int txtEndDateDay = Integer.parseInt(
                    request.getParameter("txtEndDateDay"));
            if (txtEndDateDay < 1 || txtEndDateDay > 31)
                setError("Ending day must be between 1 and 31");
        } catch (NumberFormatException e) {
            setError("Ending day must be numeric.");            
        }
    }

    private void checkTxtEndDateYear() {
        try {
            Integer.parseInt(request.getParameter("txtEndDateYear"));
        } catch (NumberFormatException e) {
            setError("Ending year must be numeric.");            
        }
    }

    private void checkRadioPayment() {
        if (request.getParameter("radioPayment") == null)
            setError("Payment must be selected.");
    }

    private void checkTripStartDate() {
       

       Date startDate = getStartDate();
       Date today = Calendar.getInstance().getTime();

        // Get a calender instance to add to
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, +1);

        // Get the result from the calender addition
        Date todayPlusOneYear = cal.getTime();

       if (startDate == null)
           setError("Start date must be a valid date.");
       else if (startDate.before(today))
           setError("Start date must be after today.");
       else if (startDate.after(todayPlusOneYear))
           setError("Start date must be less than one year from today.");
    }

    private void checkTripEndDate() {

        Date startDate = getStartDate();
        Date endDate = getEndDate();
        Date startDatePlus30Days = null;


        // Set the start date plus 30 start date is valid
        if (endDate != null) {
            // Get a calender instance and add a year to it
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.DATE, 30);
            // Get the result from the addition
            startDatePlus30Days = cal.getTime();
        }

        if (endDate == null)
           setError("End date must be a valid date.");
        else if (endDate.before(startDate))
           setError("End date must be on or after start date.");
        else if (endDate.after(startDatePlus30Days))
           setError("End date must be less than 30 days after start date.");
    }

    public void calculate()
    {
            // Subtract dates and convert to days (add one for inclusive)
            tripDuration = ((getEndDate().getTime() - getStartDate().getTime())
                    / (1000 * 60 * 60 * 24)) + 1;

            // Get guest totals from form
            int txtAdults = Integer.parseInt(request.getParameter("txtAdults"));
            int txtTotalGuests = Integer.parseInt(request.getParameter("txtTotalGuests"));

            // Calcuate total children on trip
            tripChildren = txtTotalGuests - txtAdults;

            // Calculate trip total based on per night fee
            tripTotal = ((tripDuration-1) * (txtAdults * PERNIGHTFEEADULT)) +
                        ((tripDuration-1) * (tripChildren * PERNIGHTFEECHILDREN));
    }

    public String getTripDuration()
    {
        if (tripDuration >= 0)
            return tripDuration + " day(s).";
        else
            return "";
    }

    public String getTripTotal() {
        if (tripTotal >= 0)
            return NumberFormat.getCurrencyInstance()
                        .format(tripTotal);
        else
            return "";
    }

    public String getTripChildren() {
        if (tripChildren >= 0)
            return tripChildren + "";
        else
            return "";

    }

    public Date getStartDate() {
        try {
            return sdf.parse(
                    request.getParameter("txtStartDateMonth") + "/" +
                    request.getParameter("txtStartDateDay") + "/" +
                    request.getParameter("txtStartDateYear"));
        } catch (ParseException ex) {
            Logger.getLogger(TripCalculator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Date getEndDate() {
        try {
            return sdf.parse(
                    request.getParameter("txtEndDateMonth") + "/" +
                    request.getParameter("txtEndDateDay") + "/" +
                    request.getParameter("txtEndDateYear"));
        } catch (ParseException ex) {
            Logger.getLogger(TripCalculator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
