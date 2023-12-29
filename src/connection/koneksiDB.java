/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;

import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author muhammad noval aula
 */
    import com.mysql.cj.jdbc.MysqlDataSource;
    import java.sql.Connection ;
    import javax.swing.JOptionPane ;
public class koneksiDB {


        public static Connection BukaKoneksi() {
            try {
                MysqlDataSource m = new MysqlDataSource();
                m.setDatabaseName("app_tiket_konser");
                m.setUser("root");
                m.setPassword("");
                m.setServerName("localhost");
                m.setPortNumber(8111);
                m.setServerTimezone("Asia/Jakarta");
                Connection c = m.getConnection();
                return c;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            return null;
        }
    
}
