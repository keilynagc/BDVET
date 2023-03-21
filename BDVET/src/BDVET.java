
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;        
import java.sql.*;
import java.sql.*;


public class BDVET {
   
    public static void main(String[] args) {
        try {
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa!");
            conn.close();
            
            // Realizar una consulta de prueba
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM persons");
            
            
            // Imprimir los resultados de la consulta
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("name"));
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
