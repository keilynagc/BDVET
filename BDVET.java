

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;        
import java.sql.*;

public class BDVET {
    public static void main(String[] args) {
        try {
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("start");
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            // Realizar una consulta de prueba
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idMascota, nombre FROM paciente");
            
            
            // Imprimir los resultados de la consulta
            while (rs.next()) {
                System.out.println(rs.getInt("idMascota") + " " + rs.getString("nombre"));
            }
            
            // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
