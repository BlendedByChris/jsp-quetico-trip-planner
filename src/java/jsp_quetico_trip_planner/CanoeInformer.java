/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jsp_quetico_trip_planner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cleblanc
 */
public class CanoeInformer {

    private ResultSet canoesResultSet;
    private List<String> canoes = new ArrayList<String>();

    public CanoeInformer() {
        try {
            canoesResultSet = new CanoeDb().getAllCanoes();
        } catch (SQLException ex) {
            Logger.getLogger(CanoeInformer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List getAllCanoes() {
        try {
            int i = 0;
            while (canoesResultSet.next()) {
                canoes.add(i, canoesResultSet.getString("clCanoe"));
                i++;
            }
            return canoes;
        } catch (SQLException ex) {
            Logger.getLogger(CanoeInformer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    

}
