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
    private long tripDuration = 0;
    private long tripTotal = 0;

    // Per night fees
    private static long PERNIGHTFEEADULT = 20;
    private static long PERNIGHTFEECHILDREN = 8;

    // Date parser
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");


    public TripCalculator(HttpServletRequest request)
    {
        this.request = request;
    }

    public void calculate()
    {
        try {
            // Get start and end dates from form
            Date startDate = sdf.parse(getFormattedStartDate());
            Date endDate = sdf.parse(getFormattedEndDate());
            
            // Subtract dates and convert to days (add one for inclusive)
            tripDuration = ((endDate.getTime() - startDate.getTime())
                    / (1000 * 60 * 60 * 24)) + 1;

            // Get guest totals from form
            int txtAdults = Integer.parseInt(request.getParameter("txtAdults"));
            int txtTotalGuests = Integer.parseInt(request.getParameter("txtTotalGuests"));
            int txtChildren = txtTotalGuests - txtAdults;

            // Calculate trip total based on per night fee
            tripTotal = ((tripDuration-1) * (txtAdults * PERNIGHTFEEADULT)) +
                        ((tripDuration-1) * (txtChildren * PERNIGHTFEECHILDREN));
        } catch (ParseException ex) {
            Logger.getLogger(TripCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getTripDuration()
    {
        return tripDuration + " day(s).";
    }

    public String getTripTotal()
    {
        return NumberFormat.getCurrencyInstance()
                    .format(tripTotal);
    }

    private String getFormattedStartDate()
    {
        return
            request.getParameter("txtStartDateMonth") + "/" +
            request.getParameter("txtStartDateDay") + "/" +
            request.getParameter("txtStartDateYear");
    }

    private String getFormattedEndDate()
    {
        return
            request.getParameter("txtEndDateMonth") + "/" +
            request.getParameter("txtEndDateDay") + "/" +
            request.getParameter("txtEndDateYear");
    }
}
