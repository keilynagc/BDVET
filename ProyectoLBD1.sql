-- stored procedures
-- paciente
describe paciente;
CREATE OR REPLACE PROCEDURE insertar_paciente (
  idMascota IN NUMBER,
  nombre IN VARCHAR2,
  especie IN VARCHAR2,
  raza IN VARCHAR2,
  fechaNac IN VARCHAR2,
  color IN VARCHAR2,
  idDueno IN NUMBER
)
AS
BEGIN
  INSERT INTO paciente (idMascota, nombre, especie, raza, fechaNac, color, idDueno)
  VALUES (idMascota, nombre, especie, raza, fechaNac, color, idDueno);
  COMMIT;
END;

EXEC insertar_paciente(12,'Eden', 'Perro', 'Golden', '17-11-2022', 'cafe', 402400741);

select * from paciente;




---- CREAR UNA CITA NUEVA----
CREATE OR REPLACE PROCEDURE Nueva_Cita (p_idmascota IN CITAS.IDMASCOTA%TYPE, 
                                        p_nombre IN CITAS.NOMBRE%TYPE, 
                                        p_fecha IN DATE, 
                                        p_hora IN CITAS.HORA%TYPE, 
                                        p_idmedico IN CITAS.IDMEDICO%TYPE)
IS
 -- p_IdCita CITAS.IDCITA%TYPE;
BEGIN
    
    DBMS_OUTPUT.PUT_LINE('el valor sec es '|| CITAS_SEQ.NEXTVAL);
    INSERT INTO CITAS (IDCITA, IDMASCOTA, NOMBRE, FECHA, HORA, IDMEDICO)
    VALUES (CITAS_SEQ.NEXTVAL, p_idmascota, p_nombre, p_fecha, p_hora, p_idmedico);
    --RETURNING IDCITA INTO p_IdCita
    DBMS_OUTPUT.PUT_LINE('Nueva cita creada con Ã©xito. ID de la cita: ' || CITAS_SEQ.NEXTVAL);
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error al crear la nueva cita: ' || SQLERRM);
END;

select * from  CITAS;
 
Exec Nueva_Cita (12, 'Eden' ,'20-04-2023', '04:20',1);


-- Dueno
CREATE OR REPLACE PROCEDURE insertar_dueno (
    p_idDueno IN dueno.idDueno%TYPE,
    p_nombre IN dueno.nombre%TYPE,
    p_apellido IN dueno.apellido%TYPE,
    p_telefono IN dueno.telefono%TYPE,
    p_direccion IN dueno.direccion%TYPE
)
IS
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

select * from dueno;

exec insertar_dueno (13,'Jose' ,'Lorenzo' ,'8736842736','La Aurora, Heredia')




-- Registro
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

select * from registro;


Exec insertar_registro (12,'dificultad para respirar','Antibiotico y mascarillas','Pulmonia',8);

----CITAS POR FECHA---
Select * from CITAS;

CREATE OR REPLACE PROCEDURE Citas_filtro (FechaB IN CITAS.FECHA%TYPE) AS
    c_idmedico CITAS.IDMEDICO%TYPE;
    c_hora CITAS.HORA%TYPE;
    c_idmascota CITAS.IDMASCOTA%TYPE;
BEGIN
  SELECT IDMEDICO, HORA, IDMASCOTA
  INTO c_idmedico, c_hora, c_idmascota
  FROM CITAS
  WHERE FECHA = to_date (FechaB, 'DD-MM-YYYY');
  
    DBMS_OUTPUT.PUT_LINE('La informacion de la(s) cita(s) agendadas en el rango de fechas solicitado son : Mascota ID = ' || c_idmascota ||''|| '    Hora  = ' || c_hora ||''|| '    ID Medico = ' || c_idmedico );

END;

Exec Citas_filtro('20-04-2023');

--------------------------------------------------------------------------------
--Vistas

--vitas paciente:
    
--Vista de pacientes por especie y raza:
    
CREATE OR REPLACE VIEW vista_pacientes_especie_raza AS
SELECT especie, raza, COUNT(*) AS cantidad
FROM paciente
GROUP BY especie, raza;


select * from vista_pacientes_especie_raza;
--Vista de pacientes con información de su dueno:
    
CREATE OR REPLACE VIEW vista_pacientes_dueno AS
SELECT p.idMascota, p.nombre, p.especie, p.raza, p.fechaNac, p.color, d.nombre AS nombre_dueno, d.apellido AS apellido_dueno
FROM paciente p
INNER JOIN dueno d ON p.idDueno = d.idDueno;


select * from vista_pacientes_dueno;
--vitas Dueno

---Vista de duenos con la cantidad de pacientes que poseen:
CREATE OR REPLACE VIEW vista_duenos_cantidad_pacientes AS
SELECT d.idDueno, d.nombre, d.apellido, COUNT(p.idMascota) AS cantidad_pacientes
FROM dueno d
LEFT JOIN paciente p ON d.idDueno = p.idDueno
GROUP BY d.idDueno, d.nombre, d.apellido;

--Vista de duenos con información de sus animales:
CREATE OR REPLACE VIEW vista_duenos_pacientes AS
SELECT d.idDueno, d.nombre, d.apellido, p.nombre AS nombre_mascota, p.especie, p.raza, p.fechaNac, p.color
FROM dueno d
INNER JOIN paciente p ON d.idDueno = p.idDueno;

--vitas Registro
    
--Vista de registros con información de la cita
CREATE OR REPLACE VIEW vista_registros_cita AS
SELECT r.idMascota, p.nombre AS nombre_mascota, r.padecimiento, r.tratamiento, r.diagnostico, c.idCita, c.fecha, c.hora
FROM registro r
INNER JOIN paciente p ON r.idMascota = p.idMascota
INNER JOIN citas c ON r.idCita = c.idCita;

select * from vista_registros_cita;

--Vista de padecimientos más comunes:  
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
-------------------------------------------------------------------------------------------------------------------------
--Funciones

--Funciones Paciente

--Función que devuelve la edad actual de una mascota en anos:
CREATE OR REPLACE FUNCTION calcular_edad_actual(idMascotas IN INT) RETURN int IS
  fecha_nacimiento VARCHAR2(20);
  edad INT;
BEGIN
  SELECT fechaNac INTO fecha_nacimiento FROM paciente WHERE idMascota = idMascotas AND ROWNUM <= 1;
  
  SELECT FLOOR(MONTHS_BETWEEN(SYSDATE, TO_DATE(fecha_nacimiento, 'DD/MM/YYYY'))/12) INTO edad FROM DUAL;
 
  
  RETURN edad;
END;

select  calcular_edad_actual(2) from dual ;


--Función que devuelve el número de mascotas que tiene un dueno:
CREATE OR REPLACE FUNCTION contar_mascotas_dueno(idDuenos IN INT) RETURN INT IS
  cantidad_mascotas INT;
BEGIN
  SELECT COUNT(*) INTO cantidad_mascotas FROM paciente WHERE idDueno = idDuenos;
  RETURN cantidad_mascotas;
END;

select contar_mascotas_dueno(116351547) from dual ;
--Funciones dueno

--Función que devuelve el nombre completo de un dueno:
CREATE OR REPLACE FUNCTION obtener_nombre_completo(idDuenos IN INT) RETURN VARCHAR2 IS
  nombre_completo VARCHAR2(61);
BEGIN
  SELECT nombre || ' ' || apellido INTO nombre_completo FROM dueno WHERE idDueno = idDuenos;
  RETURN nombre_completo;
END;


select obtener_nombre_completo(116351547) from dual ;
--Función que devuelve el número de teléfono de un dueno:
CREATE OR REPLACE FUNCTION obtener_telefono(idDuenos IN INT) RETURN INT IS
  telefono_dueno INT;
BEGIN
  SELECT telefono INTO telefono_dueno FROM dueno WHERE idDueno = idDuenos;
  RETURN telefono_dueno;
END;

select obtener_telefono(116351547) from dual;

--Funciones para registro

--Función que devuelve el diagnóstico de una mascota:
CREATE OR REPLACE FUNCTION obtener_diagnostico(idMascotas IN INT) RETURN VARCHAR2 IS
  diagnostico_mascota VARCHAR2(50);
BEGIN
  SELECT diagnostico INTO diagnostico_mascota FROM registro WHERE idMascota = idMascotas;
  RETURN diagnostico_mascota;
END;

select obtener_diagnostico(12) from dual ;


--Función que devuelve el padecimiento y tratamiento de una mascota:
CREATE OR REPLACE FUNCTION obtener_padecimiento_y_tratamiento(idMascotas IN INT) RETURN VARCHAR2 IS
  padecimiento_tratamiento_mascota VARCHAR2(100);
BEGIN
  SELECT padecimiento || ' - ' || tratamiento INTO padecimiento_tratamiento_mascota FROM registro WHERE idMascota = idMascotas;
  RETURN padecimiento_tratamiento_mascota;
END;

select obtener_padecimiento_y_tratamiento(12) from dual;


--------------------------------------------------------------------------------------------------------------------------------
---Triggers

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
  
  
------------------------
select * from paciente;
select * from dueno;
Delete from dueno where iddueno = 34523452345;

exec insertar_dueno (34523452345,'lopez' ,'Aguero' ,'43564536','Belen, Heredia')
DESCRIBE paciente;
---------------
--trigger




----verifica que el telefono tenga 10 dÃ­gitos----
CREATE OR REPLACE TRIGGER tr_verificar_telefono
BEFORE INSERT OR UPDATE ON dueno
FOR EACH ROW
DECLARE
  v_telefono VARCHAR2(10);
BEGIN
  v_telefono := :new.telefono;
  
  IF LENGTH(v_telefono) <> 8 THEN
    RAISE_APPLICATION_ERROR(-20001, 'El telefono debe tener 8 dÃ­gitos.');
    DBMS_OUTPUT.PUT_LINE('telefono con formato erroeno' );
  END IF;
END; 
 
exec insertar_dueno (34523412,'Juan' ,'Perez' ,'12345611','La Aurora, Heredia');

--Triggers Registro
/*   

--Triggers que actualiza la fecha de registro del paciente en la tabla "paciente" cada vez que se inserta un nuevo registro:
CREATE OR REPLACE TRIGGER tr_actualizar_fecha_registro_paciente
AFTER INSERT ON registro
FOR EACH ROW
BEGIN
  UPDATE paciente
  SET fecha_registro = SYSDATE
  WHERE id_mascota = :new.idMascota;
END;

--Triggers que impide la eliminación de un registro si la cita correspondiente está marcada como "atendida" en la tabla "citas":
CREATE OR REPLACE TRIGGER tr_impedir_eliminacion_registro
BEFORE DELETE ON registro
FOR EACH ROW
DECLARE
  v_atendida VARCHAR2(1);
BEGIN
  SELECT atendida
  INTO v_atendida
  FROM citas
  WHERE id_cita = :old.idCita;

  IF v_atendida = 'S' THEN
    RAISE_APPLICATION_ERROR(-20001, 'El registro no puede ser eliminado porque la cita correspondiente está marcada como "atendida".');
  END IF;
END;
select * from citas;

--Triggers que actualiza el estado de la cita correspondiente a "atendida" en la tabla "citas" cada vez que se inserta un nuevo registro:
CREATE OR REPLACE TRIGGER tr_actualizar_estado_cita
AFTER INSERT ON registro
FOR EACH ROW
BEGIN
  UPDATE citas
  SET atendida = 'S'
  WHERE id_cita = :new.idCita;
END;


--Triggers dueno


--registra la fecha de creación del dueno en la tabla dueno
CREATE OR REPLACE TRIGGER tr_fecha_creacion_dueno
BEFORE INSERT ON dueno
FOR EACH ROW
BEGIN
  :new.fecha_creacion := SYSDATE;
END;
*/

-------------------------------------------------------------------------------------------------------------
--Cursores

-- Paciente
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

--registro
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

//Dueno
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
    
    -- Aquí puedes hacer lo que necesites con los datos de cada dueno --
    DBMS_OUTPUT.PUT_LINE(v_idDueno || ' - ' || v_nombre || ' ' || v_apellido || ' - ' || v_telefono || ' - ' || v_direccion);
  END LOOP;
  
  CLOSE c_duenos;
END;


