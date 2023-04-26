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
        int selection;
        
         JOptionPane.showMessageDialog(null, " Gracias por ingresar a nuestro sistema ");     
         while(true) {
         
        
          
            selection = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Seccion paciente \n"
                + "2. Seccion Cita \n"
                + "3. Seccion dueno  \n"
                + "4. Seccion registro\n"
                + "5. Seccion Medico \n"
                + "6. Seccion especialidades \n"
                ));
        switch (selection) {
            case 1:
                menuAnimal();
                break;
            case 2:
                menuCitas();
                break;
            case 3:
               
                break;
            case 4:
              
                break;
            case 5:
            
                break;

            default:
                 JOptionPane.showMessageDialog(null, "Por favor digite una opción válida ");
                 exit(0);
                break;
    } 
    }
    }
    
    
        
   //****************************************************************************************************************************     
   //**************************************************************************************************************************** 
   //                                               PACIENTES  
   //****************************************************************************************************************************    
   //****************************************************************************************************************************  
    
    
    
    
    public static void menuAnimal(){
    
        int selection2;
        selection2 = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todos los pacientes \n"
                + "2. buscar un paciente por id \n"
                + "3. Actualizar paciente \n"
                + "4. Eliminar un paciente  \n"
                + "5. Agregar un paciente  \n"));
        switch (selection2) {
            case 1:
                todosPacientes();
                break;
            case 2:
                buscaPaciente();
                break;
            case 3:
                actualizarPaciente();
                break;
            case 4:
                eliminarPaciente();
                break;
            case 5:
                agregarPaciente();
                break;

            default:
                JOptionPane.showMessageDialog(null, "Por favor digite una opción válida ");
                 return;

        }
    
    }
    
    
    public static  void todosPacientes (){
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
     return;
    }
    
    
  public static  void buscaPaciente (){
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
    
  
  
  
 public static  void eliminarPaciente (){
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
 
    public static void actualizarPaciente(){

    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_id = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota que desea acutualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre de la mascota que desea acutualizar");
            String esp = JOptionPane.showInputDialog(null, "Digite la especie de la mascota que desea acutualizar");
            String raza = JOptionPane.showInputDialog(null, "Digite la raza de la mascota que desea acutualizar");
            String fecha = JOptionPane.showInputDialog(null, "Digite la fecha de nacimiento de la mascota que desea acutualizar");
            String color = JOptionPane.showInputDialog(null, "Digite el color de la mascota que desea acutualizar");
            int p_dueno_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el la cedula del dueno de la mascota que desea acutualizar"));
            
            String procedureCall = "{call Actualizar_Paciente(?, ?, ?, ?, ?, ?,?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_id);
            statement.setString(2, nom);
            statement.setString(3, esp);
            statement.setString(4, raza);
            statement.setString(5, fecha);
            statement.setString(6, color);
            statement.setInt(7, p_dueno_id);
            
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El paciente se actualizo correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] vectorStrings = new String[5];
         main(vectorStrings);
        
    
    }



    public static void agregarPaciente(){
        
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
            int p_id = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota que desea agregar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre de la mascota que desea agregar");
            String esp = JOptionPane.showInputDialog(null, "Digite la especie de la mascota que desea agregar");
            String raza = JOptionPane.showInputDialog(null, "Digite la raza de la mascota que desea agregar");
            String fecha = JOptionPane.showInputDialog(null, "Digite la fecha de nacimiento de la mascota que desea agregar");
            String color = JOptionPane.showInputDialog(null, "Digite el color de la mascota que desea agregar");
            int p_dueno_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el la cedula del dueno de la mascota que desea agregar"));
            
            String procedureCall = "{call insertar_paciente(?, ?, ?, ?, ?, ?,?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_id);
            statement.setString(2, nom);
            statement.setString(3, esp);
            statement.setString(4, raza);
            statement.setString(5, fecha);
            statement.setString(6, color);
            statement.setInt(7, p_dueno_id);
            
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El paciente se agrego correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] vectorStrings = new String[5];
         main(vectorStrings);
        
    
    }
    
   //****************************************************************************************************************************     
   //**************************************************************************************************************************** 
   //                        CITAS  
   //****************************************************************************************************************************    
   //****************************************************************************************************************************     
    public static void menuCitas(){
    
        int selection2;
        selection2 = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todos las citas \n"
                + "2. buscar una cita por id \n"
                + "3. Actualizar una cita \n"
                + "4. Eliminar una cita  \n"
                + "5. Agregar una cita  \n"));
        switch (selection2) {
            case 1:
                todasCitas();
                break;
            case 2:
                buscarCita();
                break;
            case 3:
                actualizarCita();
                break;
            case 4:
                eliminarCita();
                break;
            case 5:
                agregarCita();
                break;

            default:
                  JOptionPane.showMessageDialog(null, "Por favor digite una opción válida ");
                 exit(0);
                break;
             

        }
    
    }
    
    
    public static  void todasCitas (){
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
            ResultSet rs = stmt.executeQuery("select * from vista_registros_cita");
            
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\nEl id de mascota es: "+ rs.getInt("idMascota") + 
                        "\n  El nombre de la mascota es: " + rs.getString("nombre_mascota")+
                        "\n  El padedcimiento es : "+rs.getString("PADECIMIENTO")+
                         "\n  El diagnostico es : "+rs.getString("DIAGNOSTICO")+
                         "\n  El codigo de la cita es : "+rs.getInt("IDCITA")+
                         "\n  La fecha es : "+rs.getString("fecha")+" A las : "+rs.getString("HORA");
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
    
    
  public static  void buscarCita (){
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
            String query = "select * FROM vista_registros_cita where idmascota ="+idmasc;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                 chain+= "\n ***************************************\nEl id de mascota es: "+ rs.getInt("idMascota") + 
                        "\n  El nombre de la mascota es: " + rs.getString("nombre_mascota")+
                        "\n  El padedcimiento es : "+rs.getString("PADECIMIENTO")+
                         "\n  El diagnostico es : "+rs.getString("DIAGNOSTICO")+
                         "\n  El codigo de la cita es : "+rs.getInt("IDCITA")+
                         "\n  La fecha es : "+rs.getString("fecha")+" A las : "+rs.getString("HORA");
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
    
  
  
  
 public static  void eliminarCita (){
  
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_id = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la cita que desea eliminar"));
            String procedureCall = "{call Eliminar_Citas(?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

            
            statement.setInt(1, p_id);
        
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "La cita se  elimino correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    String[] vectorStrings = new String[5];
     main(vectorStrings);
    }   
 
    public static void actualizarCita(){

    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_idCita = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la cita que desea acutualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre de la mascota que desea acutualizar");
            String fecha = JOptionPane.showInputDialog(null, "Digite la fecha de nacimiento de la cita que desea acutualizar");
            String hora = JOptionPane.showInputDialog(null, "Digite la hora de la cita que desea acutualizar");
            int p_mascota_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota de la  cita que desea acutualizar"));
            int p_medico_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id del medico de la cita que desea acutualizar"));
            
            
            String procedureCall = "{call Actualizar_citas(?, ?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idCita);
            statement.setInt(2, p_mascota_id);
            statement.setString(3, nom);
            statement.setString(4, fecha);
            statement.setString(5, hora);
            statement.setInt(6, p_medico_id);
            
            
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "La cita se actualizo correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] vectorStrings = new String[5];
         main(vectorStrings);
        
    
    }



    public static void agregarCita(){
 
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
              
            // Realizar una consulta de prueba
             int p_mascota_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota de la  cita que desea acutualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre de la mascota que desea acutualizar");
            String fecha = JOptionPane.showInputDialog(null, "Digite la fecha de nacimiento de la cita que desea acutualizar");
            String hora = JOptionPane.showInputDialog(null, "Digite la hora de la cita que desea acutualizar");
           
            int p_medico_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id del medico de la cita que desea acutualizar"));
            
            
            String procedureCall = "{call Nueva_cita(?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           

            statement.setInt(1, p_mascota_id);
            statement.setString(2, nom);
            statement.setString(3, fecha);
            statement.setString(4, hora);
            statement.setInt(5, p_medico_id);
            
            
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "La cita se agrego correctamente");
             
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] vectorStrings = new String[5];
         main(vectorStrings);
        
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}

    