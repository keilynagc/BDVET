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
          JOptionPane.showMessageDialog(null, " Gracias por ingresar a nnuestro systema ");
          

        selection = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todos los pacientes \n"
                + "2. bsucar un paciente por id \n"
                + "3. consultar diagnostico  \n"));
        switch (selection) {
            case 1:
                allAnimals();
                break;
            case 2:
                addAnimal();
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
               
                chain+= "\n El id de mascota es: "+ rs.getInt("idMascota") + 
                        "\n  la especie es : "+rs.getString("especie")+
                        "\n  y el nombre de la mascota es: " + rs.getString("nombre")+"\n \n";
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

    