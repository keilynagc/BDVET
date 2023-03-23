SET SERVEROUTPUT ON;
---- Secuencia para crear ID's----
CREATE SEQUENCE PACIENTE_SEQ
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

CREATE SEQUENCE CITAS_SEQ
START WITH 1
INCREMENT BY 1
NOMAXVALUE;

Select * from registro;
----Procedimientos almacenados----

----CREAR PACIENTE----

DROP procedure Crear_Paciente;
CREATE OR REPLACE PROCEDURE Crear_Paciente (
    p_nombre IN PACIENTE.NOMBRE%TYPE,
    p_especie IN PACIENTE.ESPECIE%TYPE,
    p_raza IN PACIENTE.RAZA%TYPE,
    p_nacimiento IN PACIENTE.FECHANAC%TYPE,
    p_color IN PACIENTE.COLOR%TYPE,
    p_due�o_id IN PACIENTE. IDDUE�O%TYPE
) AS
    p_id PACIENTE.IDMASCOTA%TYPE;
BEGIN
    INSERT INTO PACIENTE (IDMASCOTA, NOMBRE, ESPECIE, RAZA, FECHANAC, COLOR, IDDUE�O)
    VALUES (PACIENTE_SEQ.NEXTVAL, p_nombre, p_especie, p_raza, p_nacimiento, p_color, p_due�o_id); ----nextval para generar el ID nuevo---- 
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Se ha creado un nuevo paciente');
END;

---- CREAR UNA CITA NUEVA----
CREATE OR REPLACE PROCEDURE Nueva_Cita (p_idmascota IN CITAS.IDMASCOTA%TYPE, 
                                        p_nombre IN CITAS.NOMBRE%TYPE, 
                                        p_fecha IN DATE, 
                                        p_hora IN CITAS.HORA%TYPE, 
                                        p_idmedico IN CITAS.IDMEDICO%TYPE)
IS
  p_IdCita CITAS.IDCITA%TYPE;
BEGIN
  INSERT INTO CITAS (IDCITA, IDMASCOTA, NOMBRE, FECHA, HORA, IDMEDICO)
  VALUES (CITAS_SEQ.NEXTVAL, p_idmascota, p_nombre, p_fecha, p_hora, p_idmedico)
  RETURNING IDCITA INTO p_IdCita;
  DBMS_OUTPUT.PUT_LINE('Nueva cita creada con �xito. ID de la cita: ' || p_IdCita);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error al crear la nueva cita: ' || SQLERRM);
END;

----CREAR NUEVO DUE�O----

CREATE OR REPLACE PROCEDURE insertar_due�o (
    p_idDue�o IN due�o.idDue�o%TYPE,
    p_nombre IN due�o.nombre%TYPE,
    p_apellido IN due�o.apellido%TYPE,
    p_telefono IN due�o.telefono%TYPE,
    p_direccion IN due�o.direccion%TYPE
)
AS
BEGIN
    INSERT INTO due�o (idDue�o, nombre, apellido, telefono, direccion)
    VALUES (p_idDue�o, p_nombre, p_apellido, p_telefono, p_direccion);
    COMMIT;
    dbms_output.put_line('Due�o registrado exitosamente');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        dbms_output.put_line('Error al registrar due�o');
END;

---- NUEVO REGISTRO----
CREATE OR REPLACE PROCEDURE insertar_registro (
    p_idMascota IN registro.idMascota%TYPE,
    p_padecimiento IN registro.padecimiento%TYPE,
    p_tratamiento IN registro.tratamiento%TYPE,
    p_diagnostico IN registro.diagnostico%TYPE,
    p_idCita IN registro.idCita%TYPE
)
IS
BEGIN
    INSERT INTO registro (idMascota, padecimiento, tratamiento, diagnostico, idCita)
    VALUES (p_idMascota, p_padecimiento, p_tratamiento, p_diagnostico, p_idCita);
    COMMIT;
    dbms_output.put_line('Registro de cita registrado exitosamente');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        dbms_output.put_line('Error al registrar registro de cita');
END;

----CITAS POR FECHA---
Select * from CITAS;

CREATE OR REPLACE PROCEDURE Citas_filtro(FechaB IN CITAS.FECHA%TYPE) AS
    c_idmedico CITAS.IDMEDICO%TYPE;
    c_hora CITAS.HORA%TYPE;
    c_idmascota CITAS.IDMASCOTA%TYPE;
BEGIN
  SELECT IDMEDICO, HORA, IDMASCOTA
  INTO c_idmedico, c_hora, c_idmascota
  FROM CITAS
  WHERE FECHA = to_date (FechaB, 'DD-MM-YYYY');
  
    DBMS_OUTPUT.PUT_LINE('La informaci�n de la (s)cita(s) agendadas en el rango de fechas solicitado son: Mascota ID' || c_idmascota ||''|| 'Hora' || c_hora ||''|| 'ID Medico' || c_idmedico );

END;

Execute Citas_filtro (TO_DATE('15-03-2023', 'DD-MM-YYYY'));

----VISTAS----
----Vista de pacientes por especie y raza----
    
CREATE OR REPLACE VIEW vista_pacientes_especie_raza AS
SELECT especie, raza, COUNT(*) AS cantidad
FROM paciente
GROUP BY especie, raza;

----Vista de pacientes con informaci�n de su due�o:----
    
CREATE OR REPLACE VIEW vista_pacientes_due�o AS
SELECT p.idMascota, p.nombre, p.especie, p.raza, p.fechaNac, p.color, d.nombre AS nombre_due�o, d.apellido AS apellido_due�o
FROM paciente p
INNER JOIN due�o d ON p.idDue�o = d.idDue�o;

----Vista de due�os con la cantidad de pacientes que poseen----
CREATE OR REPLACE VIEW vista_due�os_cantidad_pacientes AS
SELECT d.idDue�o, d.nombre, d.apellido, COUNT(p.idMascota) AS cantidad_pacientes
FROM due�o d
LEFT JOIN paciente p ON d.idDue�o = p.idDue�o
GROUP BY d.idDue�o, d.nombre, d.apellido;

----Vista de due�os con informaci�n de sus pacientes----
CREATE OR REPLACE VIEW vista_due�os_pacientes AS
SELECT d.idDue�o, d.nombre, d.apellido, p.nombre AS nombre_mascota, p.especie, p.raza, p.fechaNac, p.color
FROM due�o d
INNER JOIN paciente p ON d.idDue�o = p.idDue�o;

----Vista de registros con informaci�n de la cita----
CREATE OR REPLACE VIEW vista_registros_cita AS
SELECT r.idMascota, p.nombre AS nombre_mascota, r.padecimiento, r.tratamiento, r.diagnostico, c.idCita, c.fecha, c.hora
FROM registro r
INNER JOIN paciente p ON r.idMascota = p.idMascota
INNER JOIN citas c ON r.idCita = c.idCita;


----Vista de padecimientos m�s comunes----
CREATE OR REPLACE VIEW vista_padecimientos_comunes AS
SELECT padecimiento, COUNT(*) AS cantidad
FROM registro
GROUP BY padecimiento
ORDER BY cantidad DESC;

----CITAS POR DIA----
CREATE VIEW citas_por_dia AS
SELECT FECHA, COUNT(*) AS cantidad_citas
FROM CITAS
GROUP BY FECHA;

SELECT * FROM citas_por_dia;

----FUNCIONES----

----Funci�n que devuelve la edad actual de una mascota en a�os----
CREATE OR REPLACE FUNCTION calcular_edad_actual(idMascota IN INT) RETURN INT IS
  fecha_nacimiento VARCHAR2(20);
  edad INT;
BEGIN
  SELECT fechaNac INTO fecha_nacimiento FROM paciente WHERE idMascota = idMascota;
  SELECT FLOOR(MONTHS_BETWEEN(SYSDATE, TO_DATE(fecha_nacimiento, 'DD/MM/YYYY'))/12) INTO edad FROM DUAL;
  RETURN edad;
END;

----Funci�n que devuelve el n�mero de mascotas que tiene un due�o----
CREATE OR REPLACE FUNCTION contar_mascotas_due�o(idDue�o IN INT) RETURN INT IS
  cantidad_mascotas INT;
BEGIN
  SELECT COUNT(*) INTO cantidad_mascotas FROM paciente WHERE idDue�o = idDue�o;
  RETURN cantidad_mascotas;
END;

----Funci�n que devuelve el nombre completo de un due�o:----
CREATE OR REPLACE FUNCTION obtener_nombre_completo(idDue�o IN INT) RETURN VARCHAR2 IS
  nombre_completo VARCHAR2(61);
BEGIN
  SELECT nombre || ' ' || apellido INTO nombre_completo FROM due�o WHERE idDue�o = idDue�o;
  RETURN nombre_completo;
END;

----Funci�n que devuelve el n�mero de tel�fono de un due�o----
CREATE OR REPLACE FUNCTION obtener_telefono(idDue�o IN INT) RETURN INT IS
  telefono_due�o INT;
BEGIN
  SELECT telefono INTO telefono_due�o FROM due�o WHERE idDue�o = idDue�o;
  RETURN telefono_due�o;
END;

----Funci�n que devuelve el diagn�stico de una mascota----
CREATE OR REPLACE FUNCTION obtener_diagnostico(idMascota IN INT) RETURN VARCHAR2 IS
  diagnostico_mascota VARCHAR2(50);
BEGIN
  SELECT diagnostico INTO diagnostico_mascota FROM registro WHERE idMascota = idMascota;
  RETURN diagnostico_mascota;
END;

----Funci�n que devuelve el padecimiento y tratamiento de una mascota----
CREATE OR REPLACE FUNCTION obtener_padecimiento_y_tratamiento(idMascota IN INT) RETURN VARCHAR2 IS
  padecimiento_tratamiento_mascota VARCHAR2(100);
BEGIN
  SELECT padecimiento || ' - ' || tratamiento INTO padecimiento_tratamiento_mascota FROM registro WHERE idMascota = idMascota;
  RETURN padecimiento_tratamiento_mascota;
END;

----TRIGGERS---

----Triggers que impide la eliminaci�n de un due�o si tiene mascotas asociadas en la tabla paciente----

CREATE OR REPLACE TRIGGER tr_impedir_eliminacion_due�o
BEFORE DELETE ON due�o
FOR EACH ROW
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO v_count
  FROM paciente
  WHERE iddue�o = :old.idDue�o;

  IF v_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'El due�o no puede ser eliminado porque tiene mascotas asociadas.');
  END IF;
END;

----verifica que el tel�fono tenga 10 d�gitos----
CREATE OR REPLACE TRIGGER tr_verificar_telefono
BEFORE INSERT OR UPDATE ON due�o
FOR EACH ROW
DECLARE
  v_telefono VARCHAR2(10);
BEGIN
  v_telefono := :new.telefono;
  
  IF LENGTH(v_telefono) <> 10 THEN
    RAISE_APPLICATION_ERROR(-20001, 'El tel�fono debe tener 10 d�gitos.');
  END IF;
END; 

----CURSORES----
----Paciente----
DECLARE
  CURSOR c_pacientes IS
    SELECT *
    FROM paciente;
  
  v_idMascota paciente.idMascota%TYPE;
  v_nombre paciente.nombre%TYPE;
  v_especie paciente.especie%TYPE;
  v_raza paciente.raza%TYPE;
  v_fechaNac paciente.fechaNac%TYPE;
  v_color paciente.color%TYPE;
  v_idDue�o paciente.idDue�o%TYPE;
BEGIN
  OPEN c_pacientes;
  
  LOOP
    FETCH c_pacientes INTO v_idMascota, v_nombre, v_especie, v_raza, v_fechaNac, v_color, v_idDue�o;
    
    EXIT WHEN c_pacientes%NOTFOUND;
    
    DBMS_OUTPUT.PUT_LINE(v_idMascota || ' - ' || v_nombre || ' - ' || v_especie || ' - ' || v_raza || ' - ' || v_fechaNac || ' - ' || v_color || ' - ' || v_idDue�o);
  END LOOP;
  
  CLOSE c_pacientes;
END;

----Registro----

DECLARE
  CURSOR c_registros IS
    SELECT *
    FROM registro;
  
  v_idMascota registro.idMascota%TYPE;
  v_padecimiento registro.padecimiento%TYPE;
  v_tratamiento registro.tratamiento%TYPE;
  v_diagnostico registro.diagnostico%TYPE;
  v_idCita registro.idCita%TYPE;
BEGIN
  OPEN c_registros;
  
  LOOP
    FETCH c_registros INTO v_idMascota, v_padecimiento, v_tratamiento, v_diagnostico, v_idCita;
    
    EXIT WHEN c_registros%NOTFOUND;
    
    DBMS_OUTPUT.PUT_LINE(v_idMascota || ' - ' || v_padecimiento || ' - ' || v_tratamiento || ' - ' || v_diagnostico || ' - ' || v_idCita);
  END LOOP;
  
  CLOSE c_registros;
END;

----Due�o----
DECLARE
  CURSOR c_due�os IS
    SELECT *
    FROM due�o;
  
  v_idDue�o due�o.idDue�o%TYPE;
  v_nombre due�o.nombre%TYPE;
  v_apellido due�o.apellido%TYPE;
  v_telefono due�o.telefono%TYPE;
  v_direccion due�o.direccion%TYPE;
BEGIN
  OPEN c_due�os;
  
  LOOP
    FETCH c_due�os INTO v_idDue�o, v_nombre, v_apellido, v_telefono, v_direccion;
    
    EXIT WHEN c_due�os%NOTFOUND;
    
    -- Aqu� puedes hacer lo que necesites con los datos de cada due�o --
    DBMS_OUTPUT.PUT_LINE(v_idDue�o || ' - ' || v_nombre || ' ' || v_apellido || ' - ' || v_telefono || ' - ' || v_direccion);
  END LOOP;
  
  CLOSE c_due�os;
END;
