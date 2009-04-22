/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsp_quetico_trip_planner;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author cleblanc
 */
public class TripCalculator {
    
    private HttpServletRequest request;




    // Date parser
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


    public TripCalculator(HttpServletRequest request)
    {
        this.request = request;
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
