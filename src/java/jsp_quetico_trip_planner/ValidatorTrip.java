/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsp_quetico_trip_planner;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * ValidatorTrip
 *
 * @author cleblanc
 */
public class ValidatorTrip {

    private HttpServletRequest request;
    private List<String> errors = new ArrayList<String>();

    private boolean hasErrors = false;

    /**
     * ValidatorTrip
     * 
     * Takes a request object to be validated.
     * 
     * @param request
     */
    public ValidatorTrip(HttpServletRequest request)
    {
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


    public void validate() {
        checkGroupLeaderName();
        //checkPartySize();
        //checkNumberOfAdults();
        //checkTripBeginDate();
        //checkTripEndDate();
        //checkPaymentSelected();
    }

    
    /**
     * Get Validation Summary
     *
     * Returns a summary of all errors for a given form.
     * @return
     */
    public List<String> getErrors()
    {
        return errors;
    }

    private void checkGroupLeaderName() {
        if (request.getParameter("txtGroupLeader").length() < 5) {
            errors.add("Group leader must be greater than 5 characters.");
            hasErrors = true;
        }
    }

    private void checkPartySize() {
        try {
            Integer totalGuests = Integer.getInteger(
                    request.getParameter("txtTotalGuests"));
            if (totalGuests >= 1 && totalGuests <= 9)
                errors.add("Total guests must be between 1 and 9, inclusive.");

        } catch (NumberFormatException e) {
            errors.add("Total guests must be numeric.");
        }
    }

    private void checkNumberOfAdults() {
        Integer totalGuests = Integer.getInteger(
                request.getParameter("txtTotalGuests"));
    }

    private void checkPaymentSelected() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void checkTripBeginDate() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void checkTripEndDate() {
        throw new UnsupportedOperationException("Not yet implemented");
    }






}
