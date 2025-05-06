use taller_automotriz;


select * from Repuesto;



-- Inserciones para Cliente (20 registros)
INSERT INTO Cliente (nombre, identificacion, telefono, email, direccion) VALUES
('Juan Pérez', 'CC10000001', '3001000001', 'juan.perez1@example.com', 'Calle 1 #1-01'),
('María Gómez', 'CC10000002', '3001000002', 'maria.gomez2@example.com', 'Calle 2 #2-02'),
('Carlos Rodríguez', 'CC10000003', '3001000003', 'carlos.rodriguez3@example.com', 'Calle 3 #3-03'),
('Laura Martínez', 'CC10000004', '3001000004', 'laura.martinez4@example.com', 'Calle 4 #4-04'),
('Andrés Torres', 'CC10000005', '3001000005', 'andres.torres5@example.com', 'Calle 5 #5-05'),
('Ana Ramírez', 'CC10000006', '3001000006', 'ana.ramirez6@example.com', 'Calle 6 #6-06'),
('Luis Fernández', 'CC10000007', '3001000007', 'luis.fernandez7@example.com', 'Calle 7 #7-07'),
('Patricia Díaz', 'CC10000008', '3001000008', 'patricia.diaz8@example.com', 'Calle 8 #8-08'),
('Diego Herrera', 'CC10000009', '3001000009', 'diego.herrera9@example.com', 'Calle 9 #9-09'),
('Sandra López', 'CC10000010', '3001000010', 'sandra.lopez10@example.com', 'Calle 10 #10-10'),
('Jorge Sánchez', 'CC10000011', '3001000011', 'jorge.sanchez11@example.com', 'Calle 11 #11-11'),
('Carolina Ruiz', 'CC10000012', '3001000012', 'carolina.ruiz12@example.com', 'Calle 12 #12-12'),
('Ricardo Molina', 'CC10000013', '3001000013', 'ricardo.molina13@example.com', 'Calle 13 #13-13'),
('Mónica Castro', 'CC10000014', '3001000014', 'monica.castro14@example.com', 'Calle 14 #14-14'),
('Fernando Gil', 'CC10000015', '3001000015', 'fernando.gil15@example.com', 'Calle 15 #15-15'),
('Natalia Vega', 'CC10000016', '3001000016', 'natalia.vega16@example.com', 'Calle 16 #16-16'),
('Diego Maldonado', 'CC10000017', '3001000017', 'diego.maldonado17@example.com', 'Calle 17 #17-17'),
('Carla Ortiz', 'CC10000018', '3001000018', 'carla.ortiz18@example.com', 'Calle 18 #18-18'),
('Esteban Peña', 'CC10000019', '3001000019', 'esteban.pena19@example.com', 'Calle 19 #19-19'),
('Valentina Ramos', 'CC10000020', '3001000020', 'valentina.ramos20@example.com', 'Calle 20 #20-20');

-- Inserciones para Vehiculo (20 registros)
INSERT INTO Vehiculo (matricula, marca, modelo, tipo, anio, id_cliente) VALUES
('AAA001', 'Toyota', 'Corolla', 'Automóvil', 2018, 1),
('AAA002', 'Honda', 'Civic', 'Automóvil', 2019, 2),
('AAA003', 'Ford', 'Focus', 'Automóvil', 2017, 3),
('AAA004', 'Chevrolet', 'Cruze', 'Automóvil', 2020, 4),
('AAA005', 'Nissan', 'Sentra', 'Automóvil', 2016, 5),
('AAA006', 'BMW', 'X3', 'SUV', 2021, 6),
('AAA007', 'Audi', 'Q5', 'SUV', 2022, 7),
('AAA008', 'Mercedes', 'GLA', 'SUV', 2020, 8),
('AAA009', 'Jeep', 'Wrangler', 'SUV', 2019, 9),
('AAA010', 'Kia', 'Sportage', 'SUV', 2018, 10),
('MOTO01', 'Yamaha', 'MT-07', 'Motocicleta', 2021, 11),
('MOTO02', 'Honda', 'CBR500R', 'Motocicleta', 2020, 12),
('MOTO03', 'Suzuki', 'GSX-R600', 'Motocicleta', 2019, 13),
('MOTO04', 'Kawasaki', 'Ninja 400', 'Motocicleta', 2021, 14),
('MOTO05', 'Ducati', 'Monster', 'Motocicleta', 2022, 15),
('CAM001', 'Volvo', 'FH', 'Camión', 2015, 16),
('CAM002', 'Scania', 'R Series', 'Camión', 2016, 17),
('CAM003', 'MAN', 'TGX', 'Camión', 2017, 18),
('CAM004', 'Mercedes', 'Actros', 'Camión', 2018, 19),
('OTR001', 'Varios', 'Multicar', 'Otro', 2019, 20);

-- Inserciones para Tecnico (20 registros)
INSERT INTO Tecnico (nombre, apellido, especialidad, telefono) VALUES
('Luis', 'Martínez', 'Motor', '3111000001'),
('Ana', 'Fernández', 'Eléctrico', '3111000002'),
('Pedro', 'López', 'Carrocería', '3111000003'),
('María', 'García', 'Suspensión', '3111000004'),
('Juan', 'Ramírez', 'Transmisión', '3111000005'),
('Sofía', 'Vega', 'Frenos', '3111000006'),
('Carlos', 'Castro', 'Inyección', '3111000007'),
('Elena', 'Ortiz', 'Aire acondicionado', '3111000008'),
('David', 'Rojas', 'Eléctrico', '3111000009'),
('Laura', 'Gómez', 'Motor', '3111000010'),
('Jorge', 'Sánchez', 'Carrocería', '3111000011'),
('Natalia', 'Díaz', 'Transmisión', '3111000012'),
('Ricardo', 'Torres', 'Frenos', '3111000013'),
('Carolina', 'Ruiz', 'Suspensión', '3111000014'),
('Fernando', 'Gil', 'Inyección', '3111000015'),
('Mónica', 'Castro', 'Aire acondicionado', '3111000016'),
('Diego', 'Herrera', 'Motor', '3111000017'),
('Sandra', 'López', 'Eléctrico', '3111000018'),
('Javier', 'Molina', 'Carrocería', '3111000019'),
('Valentina', 'Ramos', 'Transmisión', '3111000020');

-- Inserciones para Repuesto (20 registros)
INSERT INTO Repuesto (nombre, tipo, marca_compatible, modelo_compatible, descripcion, precio_unitario, stock_actual, stock_minimo, fecha_ingreso, vida_util_meses, estado) VALUES
('Filtro aire Genérico', 'Mecánico', 'Varios', 'Universal', 'Filtro de aire estándar', 30.00, 100, 20, '2025-01-01', 24, 'Disponible'),
('Batería 12V Genérica', 'Eléctrico', 'Varios', 'Universal', 'Batería ácido-plomo', 150.00, 50, 10, '2025-01-05', 36, 'Disponible'),
('Pastillas freno Genéricas', 'Mecánico', 'Varios', 'Universal', 'Juego de pastillas', 45.00, 80, 15, '2025-01-10', 18, 'Disponible'),
('Amortiguador Delantero', 'Mecánico', 'Varios', 'Universal', 'Amortiguador hidráulico', 120.00, 40, 8, '2025-01-15', 24, 'Disponible'),
('Espejo retrovisor Universal', 'Carrocería', 'Varios', 'Universal', 'Espejo lateral ajustable', 55.00, 30, 5, '2025-01-20', 60, 'Disponible'),
('Luz trasera LED', 'Eléctrico', 'Varios', 'Universal', 'Luz trasera LED', 25.00, 70, 10, '2025-01-25', 48, 'Disponible'),
('Correa distribución', 'Mecánico', 'Varios', 'Universal', 'Correa poli V', 65.00, 60, 10, '2025-02-01', 36, 'Disponible'),
('Candado volante', 'Mecánico', 'Varios', 'Universal', 'Candado de seguridad', 30.00, 25, 5, '2025-02-05', 120, 'Disponible'),
('Radiador', 'Mecánico', 'Varios', 'Universal', 'Radiador aluminio 12 filas', 200.00, 15, 5, '2025-02-10', 60, 'Disponible'),
('Sensor oxígeno', 'Eléctrico', 'Varios', 'Universal', 'Sensor de O2', 85.00, 45, 10, '2025-02-15', 36, 'Disponible'),
('Aceite motor 10W40', 'Consumo', 'Varios', 'Universal', 'Aceite sintético', 20.00, 200, 30, '2025-02-20', 12, 'Disponible'),
('Filtro aceite', 'Mecánico', 'Varios', 'Universal', 'Filtro de aceite estándar', 15.00, 150, 25, '2025-02-25', 24, 'Disponible'),
('Bujía estándar', 'Eléctrico', 'Varios', 'Universal', 'Bujía platino', 10.00, 300, 50, '2025-03-01', 24, 'Disponible'),
('Rejilla frontal', 'Carrocería', 'Varios', 'Universal', 'Rejilla plástica', 40.00, 60, 10, '2025-03-05', 60, 'Disponible'),
('Espejo interior', 'Carrocería', 'Varios', 'Universal', 'Espejo retrovisor interior', 25.00, 80, 15, '2025-03-10', 60, 'Disponible'),
('Parabrisas', 'Carrocería', 'Varios', 'Universal', 'Parabrisas templado', 150.00, 10, 2, '2025-03-15', 120, 'Disponible'),
('Limpiaparabrisas', 'Consumo', 'Varios', 'Universal', 'Juego de paletas', 18.00, 120, 20, '2025-03-20', 12, 'Disponible'),
('Faro delantero', 'Eléctrico', 'Varios', 'Universal', 'Faro halógeno', 70.00, 50, 10, '2025-03-25', 48, 'Disponible'),
('Cable bujía', 'Eléctrico', 'Varios', 'Universal', 'Cable de alta tensión', 12.00, 100, 20, '2025-03-30', 36, 'Disponible'),
('Filtro combustible', 'Mecánico', 'Varios', 'Universal', 'Filtro de gasolina', 20.00, 90, 15, '2025-04-01', 24, 'Disponible');

-- Inserciones para Proveedor (20 registros)
INSERT INTO Proveedor (nombre, nit, direccion, contacto, frecuencia_suministro) VALUES
('Proveedor 1 S.A.', '900000001-1', 'Calle 1', 'Contacto 1', 'Mensual'),
('Proveedor 2 S.A.', '900000002-2', 'Calle 2', 'Contacto 2', 'Mensual'),
('Proveedor 3 S.A.', '900000003-3', 'Calle 3', 'Contacto 3', 'Quincenal'),
('Proveedor 4 S.A.', '900000004-4', 'Calle 4', 'Contacto 4', 'Mensual'),
('Proveedor 5 S.A.', '900000005-5', 'Calle 5', 'Contacto 5', 'Quincenal'),
('Proveedor 6 S.A.', '900000006-6', 'Calle 6', 'Contacto 6', 'Mensual'),
('Proveedor 7 S.A.', '900000007-7', 'Calle 7', 'Contacto 7', 'Quincenal'),
('Proveedor 8 S.A.', '900000008-8', 'Calle 8', 'Contacto 8', 'Mensual'),
('Proveedor 9 S.A.', '900000009-9', 'Calle 9', 'Contacto 9', 'Quincenal'),
('Proveedor 10 S.A.', '900000010-0', 'Calle 10', 'Contacto 10', 'Mensual'),
('Proveedor 11 S.A.', '900000011-1', 'Calle 11', 'Contacto 11', 'Quincenal'),
('Proveedor 12 S.A.', '900000012-2', 'Calle 12', 'Contacto 12', 'Mensual'),
('Proveedor 13 S.A.', '900000013-3', 'Calle 13', 'Contacto 13', 'Quincenal'),
('Proveedor 14 S.A.', '900000014-4', 'Calle 14', 'Contacto 14', 'Mensual'),
('Proveedor 15 S.A.', '900000015-5', 'Calle 15', 'Contacto 15', 'Quincenal'),
('Proveedor 16 S.A.', '900000016-6', 'Calle 16', 'Contacto 16', 'Mensual'),
('Proveedor 17 S.A.', '900000017-7', 'Calle 17', 'Contacto 17', 'Quincenal'),
('Proveedor 18 S.A.', '900000018-8', 'Calle 18', 'Contacto 18', 'Mensual'),
('Proveedor 19 S.A.', '900000019-9', 'Calle 19', 'Contacto 19', 'Quincenal'),
('Proveedor 20 S.A.', '900000020-0', 'Calle 20', 'Contacto 20', 'Mensual');

-- Inserciones para Proveedor_Repuesto (20 registros)
INSERT INTO Proveedor_Repuesto (id_proveedor, id_repuesto) VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10),
(11,11),(12,12),(13,13),(14,14),(15,15),(16,16),(17,17),(18,18),(19,19),(20,20);

-- Inserciones para Orden_Compra (20 registros)
INSERT INTO Orden_Compra (fecha, estado, total, id_proveedor) VALUES
('2025-01-05','Completada',1000.00,1),
('2025-01-10','Completada',1500.00,2),
('2025-01-15','Pendiente',800.00,3),
('2025-01-20','Cancelada',0.00,4),
('2025-01-25','Completada',1200.00,5),
('2025-02-01','Completada',900.00,6),
('2025-02-05','Pendiente',600.00,7),
('2025-02-10','Completada',1100.00,8),
('2025-02-15','Completada',1300.00,9),
('2025-02-20','Pendiente',500.00,10),
('2025-02-25','Cancelada',0.00,11),
('2025-03-01','Completada',1600.00,12),
('2025-03-05','Completada',1700.00,13),
('2025-03-10','Pendiente',750.00,14),
('2025-03-15','Completada',1400.00,15),
('2025-04-01','Completada',1500.00,16),
('2025-04-05','Pendiente',950.00,17),
('2025-04-10','Completada',1250.00,18),
('2025-04-15','Cancelada',0.00,19),
('2025-04-20','Completada',1350.00,20);

-- Inserciones para Lote (20 registros)
INSERT INTO Lote (fecha_ingreso, cantidad, costo_total, fecha_caducidad, id_repuesto, id_orden) VALUES
('2025-01-05',50,1500.00,'2026-01-05',1,1),
('2025-01-10',60,1800.00,'2026-01-10',2,2),
('2025-01-15',40,900.00,'2026-01-15',3,3),
('2025-01-20',20,800.00,'2026-01-20',4,4),
('2025-01-25',30,1650.00,'2026-01-25',5,5),
('2025-02-01',25,3125.00,'2026-02-01',6,6),
('2025-02-05',15,1800.00,'2026-02-05',7,7),
('2025-02-10',35,875.00,'2026-02-10',8,8),
('2025-02-15',45,3825.00,'2026-02-15',9,9),
('2025-02-20',55,192.50,'2026-02-20',10,10),
('2025-02-25',65,975.00,'2026-02-25',11,11),
('2025-03-01',75,2625.00,'2026-03-01',12,12),
('2025-03-05',85,3400.00,'2026-03-05',13,13),
('2025-03-10',95,3800.00,'2026-03-10',14,14),
('2025-03-15',105,4725.00,'2026-03-15',15,15),
('2025-04-01',115,8280.00,'2026-04-01',16,16),
('2025-04-05',125,3437.50,'2026-04-05',17,17),
('2025-04-10',135,4725.00,'2026-04-10',18,18),
('2025-04-15',145,507.50,'2026-04-15',19,19),
('2025-04-20',155,3100.00,'2026-04-20',20,20);

-- Inserciones para Servicio (20 registros)
INSERT INTO Servicio (descripcion, tipo, costo_mano_obra, duracion_estimada) VALUES
('Cambio aceite y filtro','Preventivo',80.00,'01:30:00'),
('Revisión frenos','Preventivo',60.00,'01:00:00'),
('Alineación y balanceo','Preventivo',50.00,'01:15:00'),
('Revisión sistema eléctrico','Preventivo',70.00,'01:45:00'),
('Cambio pastillas freno','Preventivo',65.00,'01:20:00'),
('Reparación motor','Correctivo',200.00,'03:00:00'),
('Reparación transmisión','Correctivo',250.00,'04:00:00'),
('Reparación sistema suspensión','Correctivo',180.00,'02:30:00'),
('Reparación sistema escape','Correctivo',150.00,'02:00:00'),
('Cambio amortiguadores','Preventivo',90.00,'02:15:00'),
('Reparación carrocería','Correctivo',300.00,'05:00:00'),
('Reparación aire acond.','Correctivo',120.00,'02:00:00'),
('Cambio correa distr.','Preventivo',110.00,'02:30:00'),
('Revisión inyección','Preventivo',75.00,'01:50:00'),
('Cambio radiador','Correctivo',220.00,'03:00:00'),
('Revisión suspensión','Preventivo',55.00,'01:10:00'),
('Revisión sistema seguridad','Preventivo',65.00,'01:40:00'),
('Reemplazo sensor O2','Correctivo',85.00,'01:30:00'),
('Cambio bujías','Preventivo',45.00,'01:00:00'),
('Instalación parrilla','Correctivo',130.00,'02:15:00');

-- Inserciones para Orden_Servicio (20 registros)
INSERT INTO Orden_Servicio (id_vehiculo, id_tecnico, fecha_ingreso, fecha_entrega, estado, observaciones) VALUES
(1,1,'2025-04-01','2025-04-02','Completado','OK'),
(2,2,'2025-04-02',NULL,'En proceso','Revisión'),
(3,3,'2025-04-03',NULL,'Pendiente','Programado'),
(4,4,'2025-04-04','2025-04-05','Completado','OK'),
(5,5,'2025-04-05',NULL,'En proceso','Revisión'),
(6,6,'2025-04-06',NULL,'Pendiente','Programado'),
(7,7,'2025-04-07','2025-04-08','Completado','OK'),
(8,8,'2025-04-08',NULL,'En proceso','Revisión'),
(9,9,'2025-04-09',NULL,'Pendiente','Programado'),
(10,10,'2025-04-10','2025-04-11','Completado','OK'),
(11,11,'2025-04-11',NULL,'En proceso','Revisión'),
(12,12,'2025-04-12',NULL,'Pendiente','Programado'),
(13,13,'2025-04-13','2025-04-14','Completado','OK'),
(14,14,'2025-04-14',NULL,'En proceso','Revisión'),
(15,15,'2025-04-15',NULL,'Pendiente','Programado'),
(16,16,'2025-04-16','2025-04-17','Completado','OK'),
(17,17,'2025-04-17',NULL,'En proceso','Revisión'),
(18,18,'2025-04-18',NULL,'Pendiente','Programado'),
(19,19,'2025-04-19','2025-04-20','Completado','OK'),
(20,20,'2025-04-20',NULL,'En proceso','Revisión');

-- Inserciones para Detalle_Servicio (20 registros)
INSERT INTO Detalle_Servicio (id_orden_servicio, id_servicio) VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10),
(11,11),(12,12),(13,13),(14,14),(15,15),(16,16),(17,17),(18,18),(19,19),(20,20);

-- Inserciones para Repuesto_Usado (20 registros)
INSERT INTO Repuesto_Usado (id_orden_servicio, id_repuesto, cantidad) VALUES
(1,1,2),(2,2,1),(3,3,1),(4,4,2),(5,5,3),(6,6,1),(7,7,1),(8,8,2),(9,9,1),(10,10,1),
(11,11,2),(12,12,1),(13,13,1),(14,14,2),(15,15,3),(16,16,1),(17,17,1),(18,18,2),(19,19,1),(20,20,1);

-- Inserciones para Factura (20 registros)
INSERT INTO Factura (fecha, subtotal, iva, total, estado, cufe, qr_code, id_cliente, id_orden_servicio) VALUES
('2025-04-02',200.00,38.00,238.00,'Pagada','CUFE0001','QR1',1,1),
('2025-04-03',150.00,28.50,178.50,'Pagada','CUFE0002','QR2',2,2),
('2025-04-04',300.00,57.00,357.00,'Pendiente','CUFE0003','QR3',3,3),
('2025-04-05',250.00,47.50,297.50,'Pagada','CUFE0004','QR4',4,4),
('2025-04-06',180.00,34.20,214.20,'Pendiente','CUFE0005','QR5',5,5),
('2025-04-07',220.00,41.80,261.80,'Pagada','CUFE0006','QR6',6,6),
('2025-04-08',275.00,52.25,327.25,'Pagada','CUFE0007','QR7',7,7),
('2025-04-09',190.00,36.10,226.10,'Pendiente','CUFE0008','QR8',8,8),
('2025-04-10',210.00,39.90,249.90,'Pagada','CUFE0009','QR9',9,9),
('2025-04-11',230.00,43.70,273.70,'Pendiente','CUFE0010','QR10',10,10),
('2025-04-12',240.00,45.60,285.60,'Pagada','CUFE0011','QR11',11,11),
('2025-04-13',260.00,49.40,309.40,'Pendiente','CUFE0012','QR12',12,12),
('2025-04-14',280.00,53.20,333.20,'Pagada','CUFE0013','QR13',13,13),
('2025-04-15',300.00,57.00,357.00,'Pendiente','CUFE0014','QR14',14,14),
('2025-04-16',320.00,60.80,380.80,'Pagada','CUFE0015','QR15',15,15),
('2025-04-17',340.00,64.60,404.60,'Pendiente','CUFE0016','QR16',16,16),
('2025-04-18',360.00,68.40,428.40,'Pagada','CUFE0017','QR17',17,17),
('2025-04-19',380.00,72.20,452.20,'Pendiente','CUFE0018','QR18',18,18),
('2025-04-20',400.00,76.00,476.00,'Pagada','CUFE0019','QR19',19,19),
('2025-04-21',420.00,79.80,499.80,'Pendiente','CUFE0020','QR20',20,20);

-- Inserciones para Campania (20 registros)
INSERT INTO Campania (nombre, descripcion, fecha_inicio, fecha_fin) VALUES
('Campaña 1','Desc 1','2025-05-01','2025-05-31'),
('Campaña 2','Desc 2','2025-05-02','2025-06-01'),
('Campaña 3','Desc 3','2025-05-03','2025-06-02'),
('Campaña 4','Desc 4','2025-05-04','2025-06-03'),
('Campaña 5','Desc 5','2025-05-05','2025-06-04'),
('Campaña 6','Desc 6','2025-05-06','2025-06-05'),
('Campaña 7','Desc 7','2025-05-07','2025-06-06'),
('Campaña 8','Desc 8','2025-05-08','2025-06-07'),
('Campaña 9','Desc 9','2025-05-09','2025-06-08'),
('Campaña 10','Desc 10','2025-05-10','2025-06-09'),
('Campaña 11','Desc 11','2025-05-11','2025-06-10'),
('Campaña 12','Desc 12','2025-05-12','2025-06-11'),
('Campaña 13','Desc 13','2025-05-13','2025-06-12'),
('Campaña 14','Desc 14','2025-05-14','2025-06-13'),
('Campaña 15','Desc 15','2025-05-15','2025-06-14'),
('Campaña 16','Desc 16','2025-05-16','2025-06-15'),
('Campaña 17','Desc 17','2025-05-17','2025-06-16'),
('Campaña 18','Desc 18','2025-05-18','2025-06-17'),
('Campaña 19','Desc 19','2025-05-19','2025-06-18'),
('Campaña 20','Desc 20','2025-05-20','2025-06-19');

-- Inserciones para Cita_Campania (20 registros)
INSERT INTO Cita_Campania (fecha, hora, estado, id_cliente, id_campania) VALUES
('2025-05-01','09:00:00','Programada',1,1),
('2025-05-02','10:00:00','Programada',2,2),
('2025-05-03','11:00:00','Programada',3,3),
('2025-05-04','12:00:00','Programada',4,4),
('2025-05-05','13:00:00','Programada',5,5),
('2025-05-06','14:00:00','Programada',6,6),
('2025-05-07','15:00:00','Programada',7,7),
('2025-05-08','16:00:00','Programada',8,8),
('2025-05-09','17:00:00','Programada',9,9),
('2025-05-10','08:00:00','Programada',10,10),
('2025-05-11','09:30:00','Programada',11,11),
('2025-05-12','10:30:00','Programada',12,12),
('2025-05-13','11:30:00','Programada',13,13),
('2025-05-14','12:30:00','Programada',14,14),
('2025-05-15','13:30:00','Programada',15,15),
('2025-05-16','14:30:00','Programada',16,16),
('2025-05-17','15:30:00','Programada',17,17),
('2025-05-18','16:30:00','Programada',18,18),
('2025-05-19','17:30:00','Programada',19,19),
('2025-05-20','18:00:00','Programada',20,20);

-- Inserciones para Inspeccion (20 registros)
INSERT INTO Inspeccion (fecha, resultado, observaciones, id_vehiculo, id_tecnico) VALUES
('2025-04-01','Aprobada','OK',1,1),
('2025-04-02','Rechazada','Reemplazar freno',2,2),
('2025-04-03','Aprobada','OK',3,3),
('2025-04-04','Rechazada','Fuga aceite',4,4),
('2025-04-05','Aprobada','OK',5,5),
('2025-04-06','Rechazada','Batería baja',6,6),
('2025-04-07','Aprobada','OK',7,7),
('2025-04-08','Rechazada','Correa desgastada',8,8),
('2025-04-09','Aprobada','OK',9,9),
('2025-04-10','Rechazada','Sensor fallo',10,10),
('2025-04-11','Aprobada','OK',11,11),
('2025-04-12','Rechazada','Bujías malas',12,12),
('2025-04-13','Aprobada','OK',13,13),
('2025-04-14','Rechazada','Radiador fisurado',14,14),
('2025-04-15','Aprobada','OK',15,15),
('2025-04-16','Rechazada','Frenos bajos',16,16),
('2025-04-17','Aprobada','OK',17,17),
('2025-04-18','Rechazada','Escape corroído',18,18),
('2025-04-19','Aprobada','OK',19,19),
('2025-04-20','Rechazada','Luz falla',20,20);
