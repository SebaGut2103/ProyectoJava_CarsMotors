CREATE DATABASE IF NOT EXISTS taller_automotriz;


USE taller_automotriz;

-- TABLAS 


-- 

CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    identificacion VARCHAR(30) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion VARCHAR(255)
);


-- 

CREATE TABLE Vehiculo (
    id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    tipo ENUM('Automóvil','SUV','Motocicleta','Camión','Otro') NOT NULL,
    anio INT NOT NULL,
    id_cliente INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);


-- 
CREATE TABLE Tecnico (
    id_tecnico INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100),
    especialidad VARCHAR(100),
    telefono VARCHAR(20)
);



-- 
CREATE TABLE Repuesto (
    id_repuesto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo ENUM('Mecánico', 'Eléctrico', 'Carrocería', 'Consumo') NOT NULL,
    marca_compatible VARCHAR(100),
    modelo_compatible VARCHAR(100),
    descripcion TEXT,
    precio_unitario DECIMAL(10,2) NOT NULL,
    stock_actual INT NOT NULL,
    stock_minimo INT NOT NULL,
    fecha_ingreso DATE,
    vida_util_meses INT,
    estado ENUM('Disponible', 'Reservado', 'Fuera de servicio') DEFAULT 'Disponible'
);



-- 
CREATE TABLE Proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nit VARCHAR(30),
    direccion VARCHAR(255),
    contacto VARCHAR(100),
    frecuencia_suministro VARCHAR(50)
);






-- 

CREATE TABLE Proveedor_Repuesto (
    id_proveedor_repuesto INT AUTO_INCREMENT PRIMARY KEY,
    id_proveedor INT NOT NULL,
    id_repuesto INT NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor),
    FOREIGN KEY (id_repuesto) REFERENCES Repuesto(id_repuesto)
);


-- INVENTARIO Y TRAZABILIDAD




-- 

CREATE TABLE Orden_Compra (
    id_orden INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    estado ENUM('Pendiente', 'Completada', 'Cancelada') NOT NULL,
    total DECIMAL(10,2),
    id_proveedor INT,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id_proveedor)
);



-- 

CREATE TABLE Lote (
    id_lote INT AUTO_INCREMENT PRIMARY KEY,
    fecha_ingreso DATE NOT NULL,
    cantidad INT NOT NULL,
    costo_total DECIMAL(10,2),
    fecha_caducidad DATE,
    id_repuesto INT NOT NULL,
    id_orden INT NOT NULL,
    FOREIGN KEY (id_repuesto) REFERENCES Repuesto(id_repuesto),
    FOREIGN KEY (id_orden) REFERENCES Orden_Compra(id_orden)
);


-- SERVICIOS Y FLUJO DE MANTENIMIENTO




-- 

CREATE TABLE Servicio (
    id_servicio INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    tipo ENUM('Preventivo','Correctivo') NOT NULL,
    costo_mano_obra DECIMAL(10,2) NOT NULL,
    duracion_estimada TIME NOT NULL
);



-- 

CREATE TABLE Orden_Servicio (
    id_orden_servicio INT AUTO_INCREMENT PRIMARY KEY,
    id_vehiculo INT NOT NULL,
    id_tecnico INT NOT NULL,
    fecha_ingreso DATE NOT NULL,
    fecha_entrega DATE,
    estado ENUM('Pendiente','En proceso','Completado','Entregado') DEFAULT 'Pendiente',
    observaciones TEXT,
    FOREIGN KEY (id_vehiculo) REFERENCES Vehiculo(id_vehiculo),
    FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico)
);


-- 
CREATE TABLE Detalle_Servicio (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_orden_servicio INT NOT NULL,
    id_servicio INT NOT NULL,
    FOREIGN KEY (id_orden_servicio) REFERENCES Orden_Servicio(id_orden_servicio),
    FOREIGN KEY (id_servicio) REFERENCES Servicio(id_servicio)
);



-- 

CREATE TABLE Repuesto_Usado (
    id_repuesto_usado INT AUTO_INCREMENT PRIMARY KEY,
    id_orden_servicio INT NOT NULL,
    id_repuesto INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (id_orden_servicio) REFERENCES Orden_Servicio(id_orden_servicio),
    FOREIGN KEY (id_repuesto) REFERENCES Repuesto(id_repuesto)
);


-- FACTURACIÓN

-- 

CREATE TABLE Factura (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    iva DECIMAL(10,2),
    total DECIMAL(10,2) NOT NULL,
    estado ENUM('Pagada', 'Pendiente', 'Anulada') NOT NULL,
    cufe VARCHAR(100),
    qr_code TEXT,
    id_cliente INT NOT NULL,
    id_orden_servicio INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_orden_servicio) REFERENCES Orden_Servicio(id_orden_servicio)
);


-- CAMPAÑAS Y INSPECCIONES



-- 
CREATE TABLE Campania (
    id_campania INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL
);


-- 

CREATE TABLE Cita_Campania (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado ENUM('Programada','Realizada','Cancelada'),
    id_cliente INT NOT NULL,
    id_campania INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_campania) REFERENCES Campania(id_campania)
);


-- 

CREATE TABLE Inspeccion (
    id_inspeccion INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    resultado ENUM('Aprobada','Rechazada') NOT NULL,
    observaciones TEXT,
    id_vehiculo INT NOT NULL,
    id_tecnico INT NOT NULL,
    FOREIGN KEY (id_vehiculo) REFERENCES Vehiculo(id_vehiculo),
    FOREIGN KEY (id_tecnico) REFERENCES Tecnico(id_tecnico)
);
