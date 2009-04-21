package jsp_quetico_trip_planner;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * TripValidator
 *
 * @author cleblanc
 */
public class TripValidator {

    private HttpServletRequest request;
    private List<String> errors = new ArrayList<String>();

    private boolean hasErrors = false;

    /**
     * TripValidator
     * 
     * Takes a request object to be validated.
     * 
     * @param request
     */
    public TripValidator(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Has Errors
     *
     * Returns whether the validator found an error.
     *
     * @return boolean
     */
    public boolean hasErrors() {
        return hasErrors;
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
        if (request.getParameter(parameter) == null)
            return "";
        else
            return request.getParameter(parameter);
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
        //checkTripEndDate();
        //checkPaymentSelected();
    }



    private void checkTxtGroupLeader() {
        if (request.getParameter("txtGroupLeader").length() < 5) {
            errors.add("Group leader must be greater than 5 characters.");
            hasErrors = true;
        }
    }

    private void checkTxtTotalGuests() {
        try {
            int totalGuests = Integer.parseInt(
                    request.getParameter("txtTotalGuests"));
            if (totalGuests <= 1 || totalGuests >= 9) {
                errors.add("Total guests must be between 1 and 9, inclusive.");
                hasErrors = true;
            }

        } catch (NumberFormatException e) {
            errors.add("Total guests must be numeric.");
            hasErrors = true;
        }
    }

    private void checkTxtAdults() {
        try {
            int totalGuests = Integer.parseInt(
                    request.getParameter("txtTotalGuests"));
            int adults = Integer.parseInt(
                    request.getParameter("txtAdults"));
            if (adults <= 1 || adults > totalGuests) {
                errors.add("Adult must be between 1 and " + totalGuests +
                        " aguests.");
                hasErrors = true;
            }

        } catch (NumberFormatException e) {
            errors.add("Adults &amp; total guests must be numeric.");
            hasErrors = true;
        }
    }

    private void checkTxtStartDateMonth() {
        try {
            int txtStartDateMonth = Integer.parseInt(
                    request.getParameter("txtStartDateMonth"));
            if (txtStartDateMonth < 1 || txtStartDateMonth > 12) {
                errors.add("Starting month must be between 1 and 12");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            errors.add("Starting month must be numeric.");
            hasErrors = true;
        }
    }

    private void checkTxtStartDateDay() {
        try {
            int txtStartDateDay = Integer.parseInt(
                    request.getParameter("txtStartDateDay"));
            if (txtStartDateDay < 1 || txtStartDateDay > 31) {
                errors.add("Ending day must be between 1 and 31");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            errors.add("Starting day must be numeric.");
            hasErrors = true;
        }
    }
    
    private void checkTxtStartDateYear() {
        try {
            Integer.parseInt(request.getParameter("txtStartDateYear"));
        } catch (NumberFormatException e) {
            errors.add("Starting year must be numeric.");
            hasErrors = true;
        }
    }

    private void checkTxtEndDateMonth() {
        try {
            int txtEndDateMonth = Integer.parseInt(
                    request.getParameter("txtEndDateMonth"));
            if (txtEndDateMonth < 1 || txtEndDateMonth > 12) {
                errors.add("Ending month must be between 1 and 12");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            errors.add("Ending month must be numeric.");
            hasErrors = true;
        }
    }

    private void checkTxtEndDateDay() {
        try {
            int txtEndDateDay = Integer.parseInt(
                    request.getParameter("txtEndDateDay"));
            if (txtEndDateDay < 1 || txtEndDateDay > 31) {
                errors.add("Ending day must be between 1 and 31");
                hasErrors = true;
            }
        } catch (NumberFormatException e) {
            errors.add("Ending day must be numeric.");
            hasErrors = true;
        }
    }

    private void checkTxtEndDateYear() {
        try {
            Integer.parseInt(request.getParameter("txtEndDateYear"));
        } catch (NumberFormatException e) {
            errors.add("Ending year must be numeric.");
            hasErrors = true;
        }
    }

    private void checkRadioPayment() {
        if (request.getParameter("radioPayment") == null) {
            errors.add("Payment must be selected.");
            hasErrors = true;
        }
    }

    private void checkTripEndDate() {
        throw new UnsupportedOperationException("Not yet implemented");
    }






}
