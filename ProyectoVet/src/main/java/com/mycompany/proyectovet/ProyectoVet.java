/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectovet;
import java.awt.Dimension;
import static java.lang.System.exit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;        
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
                + "1. Sección Paciente \n"
                + "2. Sección Cita \n"
                + "3. Sección Dueño  \n"
                + "4. Sección Registro\n"
                + "5. Sección Médico \n"
                + "6. Sección Especialidades \n"
                ));
        switch (selection) {
            case 1:
                menuAnimal();
                break;
            case 2:
                menuCitas();
                break;
            case 3:
                menuDueno();
                break;
            case 4:
                menuRegistro();
                break;
            case 5:
                menuMedico();
            
                break;
            case 6:
                menuEspecialidad();
            
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
                + "2. Buscar paciente por ID \n"
                + "3. Actualizar paciente \n"
                + "4. Eliminar paciente  \n"
                + "5. Agregar paciente  \n"));
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
    chain += "\n ***************************************\nEl id de la mascota es: "+ rs.getInt("idMascota") + 
             "\n  la especie es : "+rs.getString("especie")+
             "\n  el nombre de la mascota es: " + rs.getString("nombre")+
             "\n  el dueño es : "+rs.getString("nombre_dueno");
    System.out.println(chain);           
}

    JTextArea textArea = new JTextArea(chain);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 200)); // ajusta el tamaño

JOptionPane.showMessageDialog(null, scrollPane, "Información de las mascotas", JOptionPane.PLAIN_MESSAGE);


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
                        "\n  el nombre de la mascota es: " + rs.getString("nombre")+
                        "\n  la especie es : "+rs.getString("especie")+
                        "\n  la raza es : "+rs.getString("raza")+
                        "\n  el dueño es : "+rs.getString("nombre_dueno");
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
            int p_dueno_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite la cédula del dueño de la mascota que desea eliminar"));
            String procedureCall = "{call Eliminar_Paciente(?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

            
            statement.setInt(1, p_id);
            statement.setInt(2, p_dueno_id);
        
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El paciente se eliminó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
     return;
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
            int p_id = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota que desea actualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre de la mascota que desea actualizar");
            String esp = JOptionPane.showInputDialog(null, "Digite la especie de la mascota que desea actualizar");
            String raza = JOptionPane.showInputDialog(null, "Digite la raza de la mascota que desea actualizar");
            String fecha = JOptionPane.showInputDialog(null, "Digite la fecha de nacimiento de la mascota que desea actualizar");
            String color = JOptionPane.showInputDialog(null, "Digite el color de la mascota que desea actualizar");
            int p_dueno_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el la cédula correspondiente al dueño de la mascota que desea actualizar"));
            
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
            JOptionPane.showMessageDialog(null, "El paciente se actualizó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
             return;
    
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
            int p_dueno_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el la cédula del dueño de la mascota que desea agregar"));
            
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
            JOptionPane.showMessageDialog(null, "El paciente se agregó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
             return;
    
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
                + "2. Buscar cita por ID \n"
                + "3. Actualizar cita \n"
                + "4. Eliminar cita  \n"
                + "5. Agregar cita  \n"));
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
                      return;
             

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
               
                chain+= "\n ***************************************\nEl id de la mascota es: "+ rs.getInt("idMascota") + 
                        "\n  El nombre de la mascota es: " + rs.getString("nombre_mascota")+
                        "\n  El padecimiento es : "+rs.getString("PADECIMIENTO")+
                         "\n  El diagnóstico es : "+rs.getString("DIAGNOSTICO")+
                         "\n  El código de la cita es : "+rs.getInt("IDCITA")+
                         "\n  La fecha es : "+rs.getString("fecha")+" A las : "+rs.getString("HORA");
                System.out.println(chain);
            }
                JTextArea textArea = new JTextArea(chain);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 200)); // ajusta el tamaño
            
JOptionPane.showMessageDialog(null, scrollPane, "Información de las citas", JOptionPane.PLAIN_MESSAGE);            

// Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return;
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
            String query = "select * FROM citas where idmascota ="+idmasc;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                 chain+= "\n ***************************************\nEl id de mascota es: "+ rs.getInt("idMascota") + 
                        "\n  El nombre de la mascota es: " + rs.getString("NOMBRE")+
                         "\n  El código de la cita es : "+rs.getInt("IDCITA")+
                         "\n  La fecha es : "+rs.getString("FECHA")+" A las : "+rs.getString("HORA");
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
            JOptionPane.showMessageDialog(null, "La cita se  eliminó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
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
            int p_idCita = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la cita que desea actualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre de la mascota que desea actualizar");
            String fecha = JOptionPane.showInputDialog(null, "Digite la fecha de nacimiento de la cita que desea actualizar");
            String hora = JOptionPane.showInputDialog(null, "Digite la hora de la cita que desea actualizar");
            int p_mascota_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota de la  cita que desea actualizar"));
            int p_medico_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id del medico de la cita que desea actualizar"));
            
            
            String procedureCall = "{call Actualizar_citas(?, ?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idCita);
            statement.setInt(2, p_mascota_id);
            statement.setString(3, nom);
            statement.setString(4, fecha);
            statement.setString(5, hora);
            statement.setInt(6, p_medico_id);
            
            
            
            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Si digitaste el id de la la cita correcto entonces la cita se actualizó correctamente");
            
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return;
        
    
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
             int p_mascota_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota de la  cita que desea agregar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre de la mascota a la cita que desea agregar");
            String fecha = JOptionPane.showInputDialog(null, "Digite la fecha de nacimiento de la cita que desea agregar");
            String hora = JOptionPane.showInputDialog(null, "Digite la hora de la cita que desea agregar");
           
            int p_medico_id =  Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id del médico de la cita que desea agregar"));
            
            
            String procedureCall = "{call Nueva_cita(?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           

            statement.setInt(1, p_mascota_id);
            statement.setString(2, nom);
            statement.setString(3, fecha);
            statement.setString(4, hora);
            statement.setInt(5, p_medico_id);
            
            
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "La cita se agregó correctamente");
             
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
        
    
    }
    
    
    
    
        
   //****************************************************************************************************************************     
   //**************************************************************************************************************************** 
   //                        DUENOS 
   //****************************************************************************************************************************    
   //****************************************************************************************************************************     
    public static void menuDueno(){
    
        int selection2;
        selection2 = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todos los duenos y su paciente \n"
                + "2. Buscar dueño por cédula \n"
                + "3. Actualizar dueño \n"
                + "4. Eliminar dueño  \n"
                + "5. Agregar dueño  \n"));
        switch (selection2) {
            case 1:
                todosDueno();
                break;
            case 2:
                buscarDueno();
                break;
            case 3:
                actualizarDueno();
                break;
            case 4:
                eliminarDueno();
                break;
            case 5:
                agregarDueno();
                break;

            default:
                  JOptionPane.showMessageDialog(null, "Por favor digite una opción válida ");
                      return;
             

        }
    
    }
    
    
    public static  void todosDueno (){
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
            ResultSet rs = stmt.executeQuery("select * from dueno");
            
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\nLa cédula es: "+ rs.getInt("iddueno") + 
                        "\n  El nombre del dueño  es: " + rs.getString("nombre")+
                        "\n  El apellido es : "+rs.getString("Apellido")+
                         "\n  Su teléfono es : "+rs.getString("telefono")+
                            "\n  Su dirección es : "+rs.getString("direccion");
                System.out.println(chain);
                
            }
    JTextArea textArea = new JTextArea(chain);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200)); // ajusta el tamaño

    JOptionPane.showMessageDialog(null, scrollPane, "Información de los dueños", JOptionPane.PLAIN_MESSAGE);    
    // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return;
    }
    
    
  public static  void buscarDueno (){
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
            String iddu = JOptionPane.showInputDialog(" Digite la cédula del dueño a buscar");
            String query = "select * FROM dueno where iddueno ='"+iddu+"'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                 chain+= "\n ***************************************\nLa cédula buscada  es: "+ rs.getInt("iddueno") + 
                        "\n  El nombre de dueño es: " + rs.getString("nombre")+
                        "\n  El apellido es : "+rs.getString("Apellido")+
                        "\n  Su teléfono es : "+rs.getString("telefono")+
                        "\n  Su dirección es : "+rs.getString("direccion");
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
    
  
  
  
 public static  void eliminarDueno (){
  
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_id = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite la cédula del dueño que desea eliminar"));
            String procedureCall = "{call Eliminar_Dueno(?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

            
            statement.setInt(1, p_id);
        
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El dueño se eliminó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
    }   
 
    public static void actualizarDueno(){

    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_idDueno = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite la cédula del dueño que desea actualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre del dueño que desea actualizar");
            String apellidos = JOptionPane.showInputDialog(null, "Digite el apellido  que desea actualizar");
            String telefono = JOptionPane.showInputDialog(null, "Digite el teléfono que desea actualizar");
            String direc = JOptionPane.showInputDialog(null, "Digite la nueva dirección");
            
            
            String procedureCall = "{call Actualizar_Dueno(?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idDueno);
            statement.setString(2, nom);
            statement.setString(3, apellidos);
            statement.setString(4, telefono);
            statement.setString(5, direc);
            
            
            
            statement.execute();
            
            JOptionPane.showMessageDialog(null, "El dueño se actualizó correctamente");
            
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return;
        
    
    }



    public static void agregarDueno(){
 
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
              
            // Realizar una consulta de prueba
            int p_idDueno = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite la cédula del dueño que desea agregar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre del dueño que desea agregar");
            String apellidos = JOptionPane.showInputDialog(null, "Digite el apellido  que desea agregar");
            String telefono = JOptionPane.showInputDialog(null, "Digite el teléfono al que desea agregar");
            String direc = JOptionPane.showInputDialog(null, "Digite la dirección del dueño ");
            
            
            String procedureCall = "{call insertar_dueno(?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idDueno);
            statement.setString(2, nom);
            statement.setString(3, apellidos);
            statement.setString(4, telefono);
            statement.setString(5, direc);            
            
            
            statement.execute();
            if (telefono.length() !=8 ){
            JOptionPane.showMessageDialog(null, "El dueño no se agregó debido a un número de teléfono con formato erróneo");
            }
            else{ JOptionPane.showMessageDialog(null, "El dueño se agregó exitosamente");}
           
             
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
        
    
    }
    
    
    
           
   //****************************************************************************************************************************     
   //**************************************************************************************************************************** 
   //                        Registro 
   //****************************************************************************************************************************    
   //****************************************************************************************************************************     
    public static void menuRegistro(){
    
        int selection2;
        selection2 = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todos los registros \n"
                + "2. Buscar registro \n"
                + "3. Actualizar registro \n"
                + "4. Eliminar registro  \n"
                + "5. Agregar registro  \n"));
        switch (selection2) {
            case 1:
                todosRegistro();
                break;
            case 2:
                buscarRegistro();
                break;
            case 3:
                actualizarRegistro();
                break;
            case 4:
                eliminarRegistro();
                break;
            case 5:
                agregarRegistro();
                break;

            default:
                  JOptionPane.showMessageDialog(null, "Por favor digite una opción válida ");
                      return;
             

        }
    
    }
    
    
    public static  void todosRegistro (){
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
            ResultSet rs = stmt.executeQuery("select * from vista_registros_cita ");
            
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\nEl id de la mascota es : "+ rs.getInt("idmascota") + 
                        "\n  El nombre de la mascota  es: " + rs.getString("nombre_Mascota")+
                        "\n  El padecimiento es : "+rs.getString("padecimiento")+
                         "\n  El tratamiento es : "+rs.getString("tratamiento")+
                         "\n  El diagnóstico es  : "+rs.getString("diagnostico")+
                        "\n  El ID de la cita es  : "+rs.getInt("idcita");
                System.out.println(chain);
                
            }
            JTextArea textArea = new JTextArea(chain);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 200)); // ajusta el tamaño

    JOptionPane.showMessageDialog(null, scrollPane, "Información de los registros", JOptionPane.PLAIN_MESSAGE);

            // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return;
    }
    
    
  public static  void buscarRegistro (){
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
            String idmasc = JOptionPane.showInputDialog(" Digite el id de la mascota del registro a buscar");
            String query = "select * FROM registro where idmascota ="+idmasc;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                 chain+= "\n ***************************************\nEL id de la mascota buscada es : "+ rs.getInt("idmascota") + 
                       "\n  El padecimiento es : "+rs.getString("padecimiento")+
                         "\n  El tratamiento es : "+rs.getString("tratamiento")+
                         "\n  El diagnóstico es  : "+rs.getString("diagnostico")+
                        "\n  El ID de la cita es  : "+rs.getInt("idcita");
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
    
  
  
  
 public static  void eliminarRegistro (){
  
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
           int idmasc = Integer.parseInt(JOptionPane.showInputDialog(" Digite el id de la mascota del registro que desea eliminar "));
           int idCita = Integer.parseInt(JOptionPane.showInputDialog(" Digite el id de la cita del registro que desea eliminar "));
            String procedureCall = "{call Eliminar_Registro(?,?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

            
            statement.setInt(1, idmasc);
             statement.setInt(2, idCita);
        
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El registro se  eliminó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
    }   
 
    public static void actualizarRegistro(){

    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_idMasc = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota del registro que desea actualizar"));
            String pad = JOptionPane.showInputDialog(null, "Digite el nuevo padecimiento");
            String trat = JOptionPane.showInputDialog(null, "Digite el nuevo tratamiento");
            String diag = JOptionPane.showInputDialog(null, "Digite el nuevo diagnóstico");
            int p_idcita = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la cita que desea actualizar"));
            
            
            String procedureCall = "{call Actualizar_Registro(?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idMasc);
            statement.setString(2, pad);
            statement.setString(3, trat);
            statement.setString(4, diag);
            statement.setInt(5, p_idcita);
            
            
            
            statement.execute();
            
            JOptionPane.showMessageDialog(null, "El registro se actualizó correctamente");
            
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return;
        
    
    }



    public static void agregarRegistro(){
 
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
              
            // Realizar una consulta de prueba
             int p_idMasc = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la mascota que desea agregar al registro"));
            String pad = JOptionPane.showInputDialog(null, "Digite el  padecimiento que desea agregar al registro");
            String trat = JOptionPane.showInputDialog(null, "Digite el  tratamiento que desea agregar al registro");
            String diag = JOptionPane.showInputDialog(null, "Digite el diagnóstico que desea agregar al registro");
            int p_idcita = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la cita que desea agregar al registro"));
            
            
            String procedureCall = "{call Insertar_Registro(?, ?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idMasc);
            statement.setString(2, pad);
            statement.setString(3, trat);
            statement.setString(4, diag);
            statement.setInt(5, p_idcita);
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El registro se agregó exitosamente");
           
             
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
        
    
    }
    
    
             
   //****************************************************************************************************************************     
   //**************************************************************************************************************************** 
   //                        Medico
   //****************************************************************************************************************************    
   //****************************************************************************************************************************     
    public static void menuMedico(){
    
        int selection2;
        selection2 = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todos los medicos \n"
                + "2. Buscar médico \n"
                + "3. Actualizar médico \n"
                + "4. Eliminar médico  \n"
                + "5. Agregar médico  \n"));
        switch (selection2) {
            case 1:
                todosMedico();
                break;
            case 2:
                buscarMedico();
                break;
            case 3:
                actualizarMedico();
                break;
            case 4:
                eliminarMedico();
                break;
            case 5:
                agregarMedico();
                break;

            default:
                  JOptionPane.showMessageDialog(null, "Por favor digite una opción válida ");
                      return;
             

        }
    
    }
    
    
    public static  void todosMedico (){
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
            ResultSet rs = stmt.executeQuery("select * from medico ");
            
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\nEl id del médico  es : "+ rs.getInt("idmedico") + 
                        "\n  El nombre es: " + rs.getString("nombre")+
                        "\n  El apellido es : "+rs.getString("apellido")+
                        
                        "\n  El ID de su especialidad es  : "+rs.getInt("idespecialidad");
                System.out.println(chain);
                
            }
            JTextArea textArea = new JTextArea(chain);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 200)); // ajusta el tamaño

    JOptionPane.showMessageDialog(null, scrollPane, "Información de los médicos", JOptionPane.PLAIN_MESSAGE);
            // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return;
    }
    
    
  public static  void buscarMedico (){
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
            String idmed= JOptionPane.showInputDialog(" Digite el id del médico a buscar");
            String query = "select * FROM medico where idmedico="+idmed;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                 chain+= "\n ***************************************\nEl id de médico buscada es : "+ rs.getInt("idmedico") + 
                        "\n  El nombre del médico es: " + rs.getString("nombre")+
                        "\n  El apellido es : "+rs.getString("apellido")+
                        
                        "\n  El ID de su especialidad es  : "+rs.getInt("idespecialidad");;
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
    
  
  
  
 public static  void eliminarMedico (){
  
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
           int idmed = Integer.parseInt(JOptionPane.showInputDialog(" Digite el id del médico que desea eliminar "));
            String procedureCall = "{call Eliminar_Medico(?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

            
            statement.setInt(1, idmed);
        
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El médico se  eliminó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
    }   
 
    public static void actualizarMedico(){

    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_idMed = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id del médico que desea actualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nuevo nombre");
            String apellido = JOptionPane.showInputDialog(null, "Digite el nuevo apellido");
            int p_idespe = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el ID de la especialidad"));
            
            
            String procedureCall = "{call Actualizar_Medico(?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idMed);
            statement.setString(2, nom);
            statement.setString(3, apellido);
            statement.setInt(4, p_idespe);
            
            
            
            statement.execute();
            
            JOptionPane.showMessageDialog(null, "El médico se actualizó correctamente");
            
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return;
        
    
    }



    public static void agregarMedico(){
 
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
              
            // Realizar una consulta de prueba
           int p_idMed = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id del médico que desea agregar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre que desea  agregar");
            String apellido = JOptionPane.showInputDialog(null, "Digite el apellido que desea agregar");
            int p_idespe = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el ID de la especialidad al que desea agregar al médico"));
            
            
            String procedureCall = "{call Insertar_Medico(?, ?, ?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idMed);
            statement.setString(2, nom);
            statement.setString(3, apellido);
            statement.setInt(4, p_idespe);
            
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "El médico se agregó exitosamente");
           
             
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
        
    
    }
    
    
          
   //****************************************************************************************************************************     
   //**************************************************************************************************************************** 
   //                        ESPECIALIDAD
   //****************************************************************************************************************************    
   //****************************************************************************************************************************     
    public static void menuEspecialidad(){
    
        int selection2;
        selection2 = Integer.parseInt(JOptionPane.showInputDialog(null, "V E T E R I N A R I A  \n"
                + "Por favor digite una de las siguientes opciones: \n"
                + "1. Ver todas las especialidades \n"
                + "2. Buscar especialidad \n"
                + "3. Actualizar especialidad \n"
                + "4. Eliminar especialidad  \n"
                + "5. Agregar  especialidad  \n"));
        switch (selection2) {
            case 1:
                todasEspecialidad();
                break;
            case 2:
                buscarEspecialidad();
                break;
            case 3:
                actualizarEspecialidad();
                break;
            case 4:
                eliminarEspecialidad();
                break;
            case 5:
                agregarEspecialidad();
                break;

            default:
                  JOptionPane.showMessageDialog(null, "Por favor digite una opción válida ");
                      return;
             

        }
    
    }
    
    
    public static  void todasEspecialidad (){
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
            ResultSet rs = stmt.executeQuery("select * from especialidades ");
            
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                chain+= "\n ***************************************\nEl id de la especialidad es : "+ rs.getInt("idespecialidad") + 
                        "\n  El nombre de la especialidad  es: " + rs.getString("nombreespec");
                System.out.println(chain);
                
            }
            JTextArea textArea = new JTextArea(chain);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(400, 200)); // ajusta el tamaño

    JOptionPane.showMessageDialog(null, scrollPane, "Información de las especialidades", JOptionPane.PLAIN_MESSAGE);

            // Cerrar la conexión
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       return;
    }
    
    
  public static  void buscarEspecialidad (){
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
            String idespec= JOptionPane.showInputDialog(" Digite el id de la especialidad  a buscar");
            String query = "select * FROM especialidades where idespecialidad="+idespec;
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            System.out.println(query);
            
            // Imprimir los resultados de la consulta
          
            while (rs.next()) {
               
                 chain+= "\n ***************************************\nEl id de la especialidad es : "+ rs.getInt("idespecialidad") + 
                        "\n  El nombre de la especialidad  es: " + rs.getString("nombreespec");
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
    
  
  
  
 public static  void eliminarEspecialidad(){
  
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
           int idespe = Integer.parseInt(JOptionPane.showInputDialog(" Digite el id de la especialidad que desea eliminar "));
            String procedureCall = "{call Eliminar_Especialidad(?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

            
            statement.setInt(1, idespe);
        
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "La especialidad se eliminó correctamente");
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
    }   
 
    public static void actualizarEspecialidad(){

    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
            
            // Realizar una consulta de prueba
            int p_idespe = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la especialidad que desea acutualizar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nuevo nombre de la especialidad");
            
            
            String procedureCall = "{call Actualizar_Especialidad(?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idespe);
            statement.setString(2, nom);
            
            
            
            statement.execute();
            
            JOptionPane.showMessageDialog(null, "La especialidad se actualizó correctamente");
            
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return;
        
    
    }



    public static void agregarEspecialidad(){
 
    try {
            
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Conectar a la base de datos Oracle
            String url = "jdbc:oracle:thin:@localhost:1521:orcl";
            String username = "KEILYN";
            String password = "Proverbios423";
            Connection conn = DriverManager.getConnection(url, username, password);
            
              
            // Realizar una consulta de prueba
           int p_idespe = Integer.parseInt( JOptionPane.showInputDialog(null, "Digite el id de la especialidad que desea agregar"));
            String nom = JOptionPane.showInputDialog(null, "Digite el nombre que desea agregar");
            
            
            String procedureCall = "{call INSERTAR_Especialidad(?, ?)}";
            CallableStatement statement = conn.prepareCall(procedureCall);

           
            statement.setInt(1, p_idespe);
            statement.setString(2, nom);
            
            
            statement.execute();
            JOptionPane.showMessageDialog(null, "La especialidad se agregó exitosamente");
           
             
             
            // Cerrar la conexión

            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
         return;
        
    }
    
}
  