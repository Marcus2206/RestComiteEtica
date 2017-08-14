/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author rasec
 */
public class NewMain1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ResultSet rs = null;
        PreparedStatement cs = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "etica");
        connectionProps.put("password", "etica123");
        Connection conn = null;

      
        
        try {
              conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=ComiteEtica",connectionProps);
            
            cs = conn.prepareStatement("exec uspCorreoPago ?");
            cs.setEscapeProcessing(true);
            cs.setQueryTimeout(90);

            cs.setString(1, "PGO1708022");

            //commented, because no need to register parameters out!, I got results from the resultset. 
            //cs.registerOutParameter(1, Types.VARCHAR);
            //cs.registerOutParameter(2, Types.VARCHAR);
            rs = cs.executeQuery();
            ArrayList<Object> list = new ArrayList<Object>();
            while (rs.next()) {
                System.out.println(rs.getInt(1));
                list.add(rs.getInt(1));

            }

        } catch (SQLException se) {
            System.out.println("Error al ejecutar SQL" + se.getMessage());
            se.printStackTrace();
            throw new IllegalArgumentException("Error al ejecutar SQL: " + se.getMessage());

        } finally {

            try {

                rs.close();
                cs.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
