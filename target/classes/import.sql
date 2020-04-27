/*Insert para la Tabla Roles*/
INSERT INTO roles (id, nombre) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, nombre) VALUES (2, 'ROLE_USER');
INSERT INTO roles (id, nombre) VALUES (3, 'ROLE_GERENTE');

/*Insert para la Tabla Usuario*/
INSERT INTO usuarios (id, cedula, nombres, apellidos, telefono, username, password, enabled) VALUES (1, '1065639582','Amilkar Jose','Hernandez Oñate', '3006449089', 'amijotta', '$2a$10$k8dBUmCiBVfEUY161VMLz.9I.OxipljxdakVSBrTCLQgWfA8BSsnS', true);
INSERT INTO usuarios (id, cedula, nombres, apellidos, telefono, username, password, enabled) VALUES (2, '10066278382','Andres Jose','Junior Ramirez', '3008773642', 'user', '$2a$10$k8dBUmCiBVfEUY161VMLz.9I.OxipljxdakVSBrTCLQgWfA8BSsnS', true);

/*Insert para la Tabla Usuario_roles*/
INSERT INTO usuarios_roles (usuarios_id, roles_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuarios_id, roles_id) VALUES (2,2);

/*Insert para la Tabla Proyectos*/
INSERT INTO proyectos (id, cod_proyecto, proj_nombre, proj_desc, horas_tot, costo_tot) VALUES (1, '00122-9872','Proyecto de Prueba','Descripcion de pruebas en proyectos', 100, 3200000);
INSERT INTO proyectos (id, cod_proyecto, proj_nombre, proj_desc, horas_tot, costo_tot) VALUES (2, 'JHU22-0097','Proyecto de Prueba 2','Descripcion de pruebas en proyectos 2', 70, 9200000);
INSERT INTO proyectos (id, cod_proyecto, proj_nombre, proj_desc, horas_tot, costo_tot) VALUES (56062-271, 'JHU22-0097','Proyecto de Prueba 2','Descripcion de pruebas en proyectos 2', 40, 3200000);


/*Insert para la Tabla Integrantes*/
INSERT INTO integrantes (id, nombre, apellido, email, genero, cod_proyecto) VALUES (1, 'Amilkar','Hernandez','amilkarhernandez07@gmailcom', 'Male', 1);
INSERT INTO integrantes (id, nombre, apellido, email, genero, cod_proyecto) VALUES (2, 'Luisa','Frey','luisafrey@gmailcom', 'Female', 2);

