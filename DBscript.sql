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
    p_dueno_id IN PACIENTE. IDDUEÃ‘O%TYPE
) AS
    p_id PACIENTE.IDMASCOTA%TYPE;
BEGIN
    INSERT INTO PACIENTE (IDMASCOTA, NOMBRE, ESPECIE, RAZA, FECHANAC, COLOR, IDDUEÃ‘O)
    VALUES (PACIENTE_SEQ.NEXTVAL, p_nombre, p_especie, p_raza, p_nacimiento, p_color, p_dueno_id); ----nextval para generar el ID nuevo---- 
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
  DBMS_OUTPUT.PUT_LINE('Nueva cita creada con Ã©xito. ID de la cita: ' || p_IdCita);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error al crear la nueva cita: ' || SQLERRM);
END;

----CREAR NUEVO DUEÃ‘O----

CREATE OR REPLACE PROCEDURE insertar_dueno (
    p_idDueno IN dueno.idDueno%TYPE,
    p_nombre IN dueno.nombre%TYPE,
    p_apellido IN dueno.apellido%TYPE,
    p_telefono IN dueno.telefono%TYPE,
    p_direccion IN dueno.direccion%TYPE
)
AS
BEGIN
    INSERT INTO dueno (idDueno, nombre, apellido, telefono, direccion)
    VALUES (p_idDueno, p_nombre, p_apellido, p_telefono, p_direccion);
    COMMIT;
    dbms_output.put_line('Dueno registrado exitosamente');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        dbms_output.put_line('Error al registrar dueno');
END;


DROP procedure Actualizar_Paciente;


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
  
    DBMS_OUTPUT.PUT_LINE('La informaciÃ³n de la (s)cita(s) agendadas en el rango de fechas solicitado son: Mascota ID' || c_idmascota ||''|| 'Hora' || c_hora ||''|| 'ID Medico' || c_idmedico );

END;

Execute Citas_filtro (TO_DATE('15-03-2023', 'DD-MM-YYYY'));

----VISTAS----
----Vista de pacientes por especie y raza----
    
CREATE OR REPLACE VIEW vista_pacientes_especie_raza AS
SELECT especie, raza, COUNT(*) AS cantidad
FROM paciente
GROUP BY especie, raza;

----Vista de pacientes con informaciÃ³n de su dueno:----
    
CREATE OR REPLACE VIEW vista_pacientes_dueno AS
SELECT p.idMascota, p.nombre, p.especie, p.raza, p.fechaNac, p.color, d.nombre AS nombre_dueno, d.apellido AS apellido_dueno
FROM paciente p
INNER JOIN dueno d ON p.idDueno = d.idDueno;

----Vista de duenos con la cantidad de pacientes que poseen----
CREATE OR REPLACE VIEW vista_duenos_cantidad_pacientes AS
SELECT d.idDueno, d.nombre, d.apellido, COUNT(p.idMascota) AS cantidad_pacientes
FROM dueno d
LEFT JOIN paciente p ON d.idDueno = p.idDueno
GROUP BY d.idDueno, d.nombre, d.apellido;

----Vista de duenos con informaciÃ³n de sus pacientes----
CREATE OR REPLACE VIEW vista_duenos_pacientes AS
SELECT d.idDueno, d.nombre, d.apellido, p.nombre AS nombre_mascota, p.especie, p.raza, p.fechaNac, p.color
FROM dueno d
INNER JOIN paciente p ON d.idDueno = p.idDueno;

----Vista de registros con informaciÃ³n de la cita----
CREATE OR REPLACE VIEW vista_registros_cita AS
SELECT r.idMascota, p.nombre AS nombre_mascota, r.padecimiento, r.tratamiento, r.diagnostico, c.idCita, c.fecha, c.hora
FROM registro r
INNER JOIN paciente p ON r.idMascota = p.idMascota
INNER JOIN citas c ON r.idCita = c.idCita;


----Vista de padecimientos mÃ¡s comunes----
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

----FunciÃ³n que devuelve la edad actual de una mascota en anos----
CREATE OR REPLACE FUNCTION calcular_edad_actual(idMascota IN INT) RETURN INT IS
  fecha_nacimiento VARCHAR2(20);
  edad INT;
BEGIN
  SELECT fechaNac INTO fecha_nacimiento FROM paciente WHERE idMascota = idMascota;
  SELECT FLOOR(MONTHS_BETWEEN(SYSDATE, TO_DATE(fecha_nacimiento, 'DD/MM/YYYY'))/12) INTO edad FROM DUAL;
  RETURN edad;
END;

----FunciÃ³n que devuelve el nÃºmero de mascotas que tiene un dueno----
CREATE OR REPLACE FUNCTION contar_mascotas_dueno(idDueno IN INT) RETURN INT IS
  cantidad_mascotas INT;
BEGIN
  SELECT COUNT(*) INTO cantidad_mascotas FROM paciente WHERE idDueno = idDueno;
  RETURN cantidad_mascotas;
END;

----FunciÃ³n que devuelve el nombre completo de un dueno:----
CREATE OR REPLACE FUNCTION obtener_nombre_completo(idDueno IN INT) RETURN VARCHAR2 IS
  nombre_completo VARCHAR2(61);
BEGIN
  SELECT nombre || ' ' || apellido INTO nombre_completo FROM dueno WHERE idDueno = idDueno;
  RETURN nombre_completo;
END;

----FunciÃ³n que devuelve el nÃºmero de telÃ©fono de un dueno----
CREATE OR REPLACE FUNCTION obtener_telefono(idDueno IN INT) RETURN INT IS
  telefono_dueno INT;
BEGIN
  SELECT telefono INTO telefono_dueno FROM dueno WHERE idDueno = idDueno;
  RETURN telefono_dueno;
END;

----FunciÃ³n que devuelve el diagnÃ³stico de una mascota----
CREATE OR REPLACE FUNCTION obtener_diagnostico(idMascota IN INT) RETURN VARCHAR2 IS
  diagnostico_mascota VARCHAR2(50);
BEGIN
  SELECT diagnostico INTO diagnostico_mascota FROM registro WHERE idMascota = idMascota;
  RETURN diagnostico_mascota;
END;

----FunciÃ³n que devuelve el padecimiento y tratamiento de una mascota----
CREATE OR REPLACE FUNCTION obtener_padecimiento_y_tratamiento(idMascota IN INT) RETURN VARCHAR2 IS
  padecimiento_tratamiento_mascota VARCHAR2(100);
BEGIN
  SELECT padecimiento || ' - ' || tratamiento INTO padecimiento_tratamiento_mascota FROM registro WHERE idMascota = idMascota;
  RETURN padecimiento_tratamiento_mascota;
END;

----TRIGGERS---

----Triggers que impide la eliminaciÃ³n de un dueno si tiene mascotas asociadas en la tabla paciente----

CREATE OR REPLACE TRIGGER tr_impedir_eliminacion_dueno
BEFORE DELETE ON dueno
FOR EACH ROW
DECLARE
  v_count NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO v_count
  FROM paciente
  WHERE iddueno = :old.idDueno;

  IF v_count > 0 THEN
    RAISE_APPLICATION_ERROR(-20001, 'El dueno no puede ser eliminado porque tiene mascotas asociadas.');
  END IF;
END;

----verifica que el telÃ©fono tenga 10 dÃ­gitos----
CREATE OR REPLACE TRIGGER tr_verificar_telefono
BEFORE INSERT OR UPDATE ON dueno
FOR EACH ROW
DECLARE
  v_telefono VARCHAR2(10);
BEGIN
  v_telefono := :new.telefono;
  
  IF LENGTH(v_telefono) <> 10 THEN
    RAISE_APPLICATION_ERROR(-20001, 'El telÃ©fono debe tener 10 dÃ­gitos.');
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
  v_idDueno paciente.idDueno%TYPE;
BEGIN
  OPEN c_pacientes;
  
  LOOP
    FETCH c_pacientes INTO v_idMascota, v_nombre, v_especie, v_raza, v_fechaNac, v_color, v_idDueno;
    
    EXIT WHEN c_pacientes%NOTFOUND;
    
    DBMS_OUTPUT.PUT_LINE(v_idMascota || ' - ' || v_nombre || ' - ' || v_especie || ' - ' || v_raza || ' - ' || v_fechaNac || ' - ' || v_color || ' - ' || v_idDueno);
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

----Dueno----
DECLARE
  CURSOR c_duenos IS
    SELECT *
    FROM dueno;
  
  v_idDueno dueno.idDueno%TYPE;
  v_nombre dueno.nombre%TYPE;
  v_apellido dueno.apellido%TYPE;
  v_telefono dueno.telefono%TYPE;
  v_direccion dueno.direccion%TYPE;
BEGIN
  OPEN c_duenos;
  
  LOOP
    FETCH c_duenos INTO v_idDueno, v_nombre, v_apellido, v_telefono, v_direccion;
    
    EXIT WHEN c_duenos%NOTFOUND;
    
    -- AquÃ­ puedes hacer lo que necesites con los datos de cada dueno --
    DBMS_OUTPUT.PUT_LINE(v_idDueno || ' - ' || v_nombre || ' ' || v_apellido || ' - ' || v_telefono || ' - ' || v_direccion);
  END LOOP;
  
  CLOSE c_duenos;
END;

---Medico---

------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------

--- Actualizar Paciente ---
DROP procedure Actualizar_Paciente;
CREATE OR REPLACE PROCEDURE Actualizar_Paciente (
    p_id IN PACIENTE.IDMASCOTA%TYPE,
    p_nombre IN PACIENTE.NOMBRE%TYPE,
    p_especie IN PACIENTE.ESPECIE%TYPE,
    p_raza IN PACIENTE.RAZA%TYPE,
    p_nacimiento IN PACIENTE.FECHANAC%TYPE,
    p_color IN PACIENTE.COLOR%TYPE,
    p_dueno_id IN PACIENTE. IDDUEÃ‘O%TYPE
) AS
BEGIN
    UPDATE PACIENTE SET NOMBRE =p_nombre, ESPECIE=p_especie, RAZA=p_raza, FECHANAC=p_nacimiento, COLOR=p_color
    WHERE IDMASCOTA= p_id AND IDDUEÃ‘O=p_dueno_id;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Se ha Actualizado un nuevo paciente');
END;

--- Eliminar Paciente --
DROP procedure Eliminar_Paciente;
CREATE OR REPLACE PROCEDURE Eliminar_Paciente (
    p_id IN PACIENTE.IDMASCOTA%TYPE,
    p_dueno_id IN PACIENTE. IDDUEÃ‘O%TYPE
) AS
BEGIN
    DELETE FROM PACIENTE 
    WHERE IDMASCOTA= p_id AND IDDUEÃ‘O=p_dueno_id;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Se ha eliminado un nuevo paciente');
END;
------------------------------------------------------------------------------------------------------------------------------
--- Actualizar dueno ---
select * from dueno;
DROP procedure Actualizar_Dueno;
CREATE OR REPLACE PROCEDURE Actualizar_Dueno (
    d_id IN DUEÃ‘O.IDDUEÃ‘O%TYPE,
    d_nombre IN DUEÃ‘O.NOMBRE%TYPE,
    d_apellido IN DUEÃ‘O.APELLIDO%TYPE,
    d_telefono IN DUEÃ‘O.TELEFONO%TYPE,
    d_direccion IN DUEÃ‘O.DIRECCION%TYPE
) AS
BEGIN
    UPDATE DUEÃ‘O SET NOMBRE =d_nombre, APELLIDO=d_apellido, TELEFONO=d_telefono, DIRECCION=d_direccion
    WHERE IDDUEÃ‘O= d_id;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Los datos del dueno han sido actualizados');
END;

--- Eliminar Dueno ---
DROP procedure Eliminar_Dueno;
CREATE OR REPLACE PROCEDURE Eliminar_Dueno (
    d_id IN DUEÃ‘O.IDDUEÃ‘O%TYPE
) AS
BEGIN
    DELETE FROM DUEÃ‘O 
    WHERE IDDUEÃ‘O= d_id;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Registro de dueno eliminado');
END;

------------------------------------------------------------------------------------------------------------------------------
--- Actualizar Registro ---
select * from registro;
DROP procedure Actualizar_Registro;
CREATE OR REPLACE PROCEDURE Actualizar_Registro (
    r_id_mascota IN REGISTRO.IDMASCOTA%TYPE,
    r_padecimiento IN REGISTRO.PADECIMIENTO%TYPE,
    r_tratamiento IN REGISTRO.TRATAMIENTO%TYPE,
    r_diagnostico IN REGISTRO.DIAGNOSTICO%TYPE,
    r_id_cita IN REGISTRO.IDCITA%TYPE
) AS
BEGIN
    UPDATE REGISTRO SET PADECIMIENTO =r_padecimiento, TRATAMIENTO=r_tratamiento, DIAGNOSTICO=r_diagnostico
    WHERE IDMASCOTA= r_id_mascota and IDCITA= r_id_cita;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Registro actualizado');
END;

--- Eliminar Registro ---
DROP procedure Eliminar_Registro;
CREATE OR REPLACE PROCEDURE Eliminar_Registro (
    r_id_mascota IN REGISTRO.IDMASCOTA%TYPE,
    r_id_cita IN REGISTRO.IDCITA%TYPE
) AS
BEGIN
    DELETE FROM REGISTRO 
    WHERE IDMASCOTA=r_id_mascota AND IDCITA= r_id_cita;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Registro eliminado');
END;

------------------------------------------------------------------------------------------------------------------------------
--- Actualizar Citas ---
select * from citas;
DROP procedure Actualizar_Citas;
CREATE OR REPLACE PROCEDURE Actualizar_citas (
    c_id IN CITAS.IDCITA%TYPE,
    c_id_mascota IN CITAS.IDMASCOTA%TYPE,
    c_nombre IN CITAS.NOMBRE%TYPE,
    c_fecha IN CITAS.FECHA%TYPE,
    c_hora IN CITAS.HORA%TYPE,
    c_id_medico IN CITAS.IDMEDICO%TYPE
) AS
BEGIN
    UPDATE CITAS SET NOMBRE =c_nombre, FECHA=c_fecha, HORA=c_hora, IDMEDICO= c_id_medico
    WHERE IDCITA= c_id;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Los datos de la cita han sido cambiados');
END;

--- eliminar Citas ---
DROP procedure Eliminar_Citas;
CREATE OR REPLACE PROCEDURE Eliminar_citas (
    c_id IN CITAS.IDCITA%TYPE
) AS
BEGIN
    DELETE FROM CITAS 
    WHERE IDCITA= c_id;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('La cita fue cancelada');
END;

-------------------------------------------------------------------------------------------------------------------------------------
----CREAR NUEVO MEDICO----

CREATE OR REPLACE PROCEDURE insertar_medico (
    p_idMedico IN medico.idMedico%TYPE,
    p_nombre IN medico.nombre%TYPE,
    p_apellido IN medico.apellido%TYPE,
    p_idEspecialidad IN medico.idEspecialidad%TYPE,
)
AS
BEGIN
    INSERT INTO medico (idMedico, nombre, apellido, idEspecialidad)
    VALUES (p_idMedico, p_nombre, p_apellido, p_idEspecialidad);
    COMMIT;
    dbms_output.put_line('medico registrado exitosamente');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        dbms_output.put_line('Error al registrar medico');
END;


DROP procedure Actualizar_Medico;

------------------------------------------------------------------------------------------------------------------------------
--- Actualizar Medico ---
DROP procedure Actualizar_Medico;
CREATE OR REPLACE PROCEDURE Actualizar_Medico (
    p_idMedico IN medico.idMedico%TYPE,
    p_nombre IN medico.nombre%TYPE,
    p_apellido IN medico.apellido%TYPE,
    p_idEspecialidad IN medico.idEspecialidad%TYPE,
) AS
BEGIN
    UPDATE MEDICO SET NOMBRE =p_nombre, APELLIDO=p_apellido
      WHERE IDMEDICO= p_idMedico;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Los datos del medico han sido cambiados');
END;

------------------------------------------------------------------------------------------------------------------------------
--- Eliminar Medico ---
DROP procedure Eliminar_Medico;
CREATE OR REPLACE PROCEDURE Eliminar_Medico (
    m_idMedico IN MEDICO.idMedico%TYPE,
    m_nombre IN MEDICO.nombre%TYPE,
	m_apellido IN MEDICO.apellido%TYPE,
	m_idEspecialidad IN MEDICO.idEspecialidad%TYPE
) AS
BEGIN
    DELETE FROM MEDICO 
    WHERE IDMEDICO = m_idMedico;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Medico eliminado');
END;

-------------------------------------------------------------------------------------------------------------------------------------
----CREAR ESPECIALIDADES----

CREATE OR REPLACE PROCEDURE insertar_especialidad (
    p_idEspecialidad IN especialidades.idEspecialidad%TYPE,
    p_nombre IN especialidades.nombreEspec%TYPE,
)
AS
BEGIN
    INSERT INTO especialidades (idEspecialidad, nombreEspec)
    VALUES (p_idEspecialidad, p_nombre);
    COMMIT;
    dbms_output.put_line('especialidad registrada exitosamente');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        dbms_output.put_line('Error al registrar la especialidad');
END;


DROP procedure Actualizar_Especialidad;

------------------------------------------------------------------------------------------------------------------------------
--- Actualizar Especialidad ---
DROP procedure Actualizar_Especialidad;
CREATE OR REPLACE PROCEDURE Actualizar_Especialidad (
    p_idEspecialidad IN especialidades.idEspecialidad%TYPE,
    p_nombre IN especialidades.nombreEspec%TYPE,
) AS
BEGIN
    UPDATE ESPECIALIDAD SET NOMBRE =p_nombre;
      WHERE IDESPECIALIDAD= p_idEspecialidad;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Los datos de la especialidad han sido cambiados');
END;
------------------------------------------------------------------------------------------------------------------------------
--- Eliminar Especialidad ---
DROP procedure Eliminar_Especialidad;
CREATE OR REPLACE PROCEDURE Eliminar_Especialidad (
    p_idEspecialidad IN especialidades.idEspecialidad%TYPE,
    p_nombre IN especialidades.nombreEspec%TYPE,
) AS
BEGIN
    DELETE FROM ESPECIALIDADES 
    WHERE idEspecialidad = p_idEspecialidad;
    COMMIT; ---- para confirmar que los cambios se llevaron a cabo----
    DBMS_OUTPUT.PUT_LINE('Especialidad eliminado');
END;