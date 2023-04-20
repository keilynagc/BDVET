/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectovet;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;        
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author 50688
 */
public class ProyectoVet {

    public static void main(String[] args) {
         
        // menu
          int selection ;
          JOptionPane.showMessageDialog(null, " Gracias por ingresar a nuestro systema ");
          

        selection = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todos los pacientes \n"
                + "2. buscar un paciente por id \n"
                + "3. consultar cita  \n"
                + "4. Eliminar un paciente  \n"));
        switch (selection) {
            case 1:
                allAnimals();
                break;
            case 2:
                search_animal();
                break;
            case 3:
                revisar_cita();
                break;
            case 4:
                eliminar_paciente();
                break;
            

            default:
                JOptionPane.showMessageDialog(null, "Por favor digite una opción válida");
                exit(0);
                break;

        }
    
    }
    
    
    public static  void allAnimals (){
        String chain= "";
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from vista_pacientes_dueno");
            
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\nEl id de mascota es: "+ rs.getInt("idMascota") + 
                        "\n  la especie es : "+rs.getString("especie")+
                        "\n  y el nombre de la mascota es: " + rs.getString("nombre")+
                        "\n  El dueño es : "+rs.getString("nombre_dueno");
                System.out.println(chain);
                
            }
            JOptionPane.showMessageDialog(null, chain);
            // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    String[] vectorStrings = new String[5];
     main(vectorStrings);
    }
    
    
  public static  void search_animal (){
        String chain= "";
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            Statement stmt = conn.createStatement();
            String idmasc = JOptionPane.showInputDialog(" Digite el id de la mascota a buscar");
            String query = "select * FROM vista_pacientes_dueno where idmascota ="+idmasc;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\nEl id de mascota es: "+ idmasc + 
                        "\n  y el nombre de la mascota es: " + rs.getString("nombre")+
                        "\n  la especie es : "+rs.getString("especie")+
                        "\n  la raza es : "+rs.getString("raza")+
                        "\n  El dueño es : "+rs.getString("nombre_dueno");
                System.out.println(chain);
                
            }
            JOptionPane.showMessageDialog(null, chain);
            // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    String[] vectorStrings = new String[5];
     main(vectorStrings);
    }   
    
      
    
  public static  void revisar_cita (){
        String chain= "";
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            Statement stmt = conn.createStatement();
            String fecha = JOptionPane.showInputDialog(" Digite la fecha de la cita  en formato dd-mm-yyyy");
            String query = "select * from citas where fecha = '"+fecha+ "'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\n Las citas acorde a la fecha ingresada  "+ fecha +" son : "+  
                        "\n  El id de la mascota: " + rs.getInt("idmascota")+
                        "\n  nombre: : "+rs.getString("nombre")+
                        "\n  la hora : "+rs.getString("hora");
                System.out.println(chain);
                
            }
            JOptionPane.showMessageDialog(null, chain);
            // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    String[] vectorStrings = new String[5];
     main(vectorStrings);
    }   
 public static  void eliminar_paciente (){
        String chain= "";
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_id = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota que desea eliminar"));
            int p_dueno_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el la cedula del dueno de la mascota que desea eliminar"));
            String procedureCall = "{call Eliminar_Paciente(?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

            
            statement.setInt(1, p_id);
            statement.setInt(2, p_dueno_id);
        
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El paciente se elimino correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    String[] vectorStrings = new String[5];
     main(vectorStrings);
    }   
 
    public static void addAnimal(){
        
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            System.out.println("antes de procedure");
            // Realizar una consulta de prueba
            String sql = "{call Crear_Paciente(?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, "Amelia");
            stmt.setString(2, "perro");
            stmt.setString(3, "Golden");
            stmt.setDate(4, java.sql.Date.valueOf("2022-04-07"));
            stmt.setString(5, "cafe claro");
            stmt.setInt(6, 432452352); // ID del dueño del paciente
            System.out.println("despues de procedure");
            stmt.execute();
            stmt.close();
    
      } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
    
    
    }
}

    