-- MySQL dump 10.13  Distrib 5.7.14, for Win64 (x86_64)
--
-- Host: localhost    Database: librarylocal
-- ------------------------------------------------------
-- Server version	5.7.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activar_tarjeta`
--

DROP TABLE IF EXISTS `activar_tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activar_tarjeta` (
  `id_activar` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `codigo_tarjeta` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id_activar`),
  KEY `id_usuario` (`id_usuario`),
  KEY `activar_tarjeta_ibfk_2` (`codigo_tarjeta`),
  CONSTRAINT `activar_tarjeta_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activar_tarjeta_ibfk_2` FOREIGN KEY (`codigo_tarjeta`) REFERENCES `tarjeta_prepago` (`codigo_tarjeta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activar_tarjeta`
--

LOCK TABLES `activar_tarjeta` WRITE;
/*!40000 ALTER TABLE `activar_tarjeta` DISABLE KEYS */;
/*!40000 ALTER TABLE `activar_tarjeta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `archivo`
--

DROP TABLE IF EXISTS `archivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archivo` (
  `id_archivo` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `total_ingresados` int(10) DEFAULT NULL,
  `total_no_ingresados` int(10) DEFAULT NULL,
  `total_leidos` int(10) DEFAULT NULL,
  PRIMARY KEY (`id_archivo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archivo`
--

LOCK TABLES `archivo` WRITE;
/*!40000 ALTER TABLE `archivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `archivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `canjear_premio`
--

DROP TABLE IF EXISTS `canjear_premio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `canjear_premio` (
  `id_canjear` int(11) NOT NULL AUTO_INCREMENT,
  `id_premio` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha_canjear` date DEFAULT NULL,
  PRIMARY KEY (`id_canjear`),
  KEY `id_premio` (`id_premio`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `canjear_premio_ibfk_1` FOREIGN KEY (`id_premio`) REFERENCES `premio` (`id_premio`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `canjear_premio_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canjear_premio`
--

LOCK TABLES `canjear_premio` WRITE;
/*!40000 ALTER TABLE `canjear_premio` DISABLE KEYS */;
INSERT INTO `canjear_premio` VALUES (1,1,2,'2018-07-08'),(2,1,2,'2018-07-08'),(3,2,2,'2018-07-08'),(4,1,2,'2018-07-10'),(5,1,2,'2018-07-12'),(6,1,2,'2018-07-12'),(7,2,4,'2018-07-22'),(8,2,4,'2018-07-23');
/*!40000 ALTER TABLE `canjear_premio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrito`
--

DROP TABLE IF EXISTS `carrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrito` (
  `id_carrito` int(11) NOT NULL AUTO_INCREMENT,
  `id_libro` int(11) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_carrito`),
  KEY `id_libro` (`id_libro`),
  CONSTRAINT `carrito_ibfk_1` FOREIGN KEY (`id_libro`) REFERENCES `libro` (`id_libro`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrito`
--

LOCK TABLES `carrito` WRITE;
/*!40000 ALTER TABLE `carrito` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compartir_wish`
--

DROP TABLE IF EXISTS `compartir_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compartir_wish` (
  `id_compartew` int(11) NOT NULL AUTO_INCREMENT,
  `id_wish` int(11) DEFAULT NULL,
  `correo_envio` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_compartew`),
  KEY `id_wish` (`id_wish`),
  CONSTRAINT `compartir_wish_ibfk_1` FOREIGN KEY (`id_wish`) REFERENCES `wish` (`id_wish`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compartir_wish`
--

LOCK TABLES `compartir_wish` WRITE;
/*!40000 ALTER TABLE `compartir_wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `compartir_wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra_boleto`
--

DROP TABLE IF EXISTS `compra_boleto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compra_boleto` (
  `folio` int(10) NOT NULL AUTO_INCREMENT,
  `cantidad_boletos` int(10) DEFAULT NULL,
  `cantidad_pagos` int(10) DEFAULT NULL,
  `costo_total` decimal(9,2) DEFAULT NULL,
  `restante` float DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `fecha_compra` date DEFAULT NULL,
  `id_evento` int(10) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`folio`),
  KEY `id_evento` (`id_evento`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `compra_boleto_ibfk_1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `compra_boleto_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra_boleto`
--

LOCK TABLES `compra_boleto` WRITE;
/*!40000 ALTER TABLE `compra_boleto` DISABLE KEYS */;
INSERT INTO `compra_boleto` VALUES (17,1,1,200.00,0,'Pagado','2018-07-12',2,4),(24,2,1,0.00,0,'Pagado','2018-07-18',4,2),(25,1,1,0.00,0,'Pagado','2018-07-19',4,7),(27,1,1,20.00,0,'Pagado','2018-07-22',6,4),(28,2,1,40.00,0,'Pagado','2018-07-23',7,4),(29,1,1,20.00,0,'Pagado','2018-07-23',8,4);
/*!40000 ALTER TABLE `compra_boleto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compra_wish`
--

DROP TABLE IF EXISTS `compra_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compra_wish` (
  `id_compraw` int(11) NOT NULL AUTO_INCREMENT,
  `id_detallew` int(11) DEFAULT NULL,
  `id_compartew` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_compraw`),
  KEY `id_detallew` (`id_detallew`),
  KEY `id_compartew` (`id_compartew`),
  CONSTRAINT `compra_wish_ibfk_1` FOREIGN KEY (`id_detallew`) REFERENCES `detalle_wish` (`id_detallew`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `compra_wish_ibfk_2` FOREIGN KEY (`id_compartew`) REFERENCES `compartir_wish` (`id_compartew`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra_wish`
--

LOCK TABLES `compra_wish` WRITE;
/*!40000 ALTER TABLE `compra_wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra_wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datalle_carrito`
--

DROP TABLE IF EXISTS `datalle_carrito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datalle_carrito` (
  `iddetalle_carrito` int(11) NOT NULL AUTO_INCREMENT,
  `id_libro` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddetalle_carrito`),
  KEY `id_libro` (`id_libro`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `datalle_carrito_ibfk_1` FOREIGN KEY (`id_libro`) REFERENCES `libro` (`id_libro`),
  CONSTRAINT `datalle_carrito_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datalle_carrito`
--

LOCK TABLES `datalle_carrito` WRITE;
/*!40000 ALTER TABLE `datalle_carrito` DISABLE KEYS */;
/*!40000 ALTER TABLE `datalle_carrito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_venta`
--

DROP TABLE IF EXISTS `detalle_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_venta` (
  `id_detalleVenta` int(11) NOT NULL AUTO_INCREMENT,
  `id_libro` int(11) DEFAULT NULL,
  `canti_libros` int(10) DEFAULT NULL,
  `codigo_compra` int(10) DEFAULT NULL,
  PRIMARY KEY (`id_detalleVenta`),
  KEY `id_libro` (`id_libro`),
  KEY `codigo_compra` (`codigo_compra`),
  CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`id_libro`) REFERENCES `libro` (`id_libro`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`codigo_compra`) REFERENCES `venta` (`codigo_compra`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
INSERT INTO `detalle_venta` VALUES (3,14,2,39);
/*!40000 ALTER TABLE `detalle_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_wish`
--

DROP TABLE IF EXISTS `detalle_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detalle_wish` (
  `id_detallew` int(11) NOT NULL AUTO_INCREMENT,
  `id_wish` int(11) DEFAULT NULL,
  `id_libro` int(11) DEFAULT NULL,
  `estado` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id_detallew`),
  KEY `id_wish` (`id_wish`),
  KEY `id_libro` (`id_libro`),
  CONSTRAINT `detalle_wish_ibfk_1` FOREIGN KEY (`id_wish`) REFERENCES `wish` (`id_wish`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `detalle_wish_ibfk_2` FOREIGN KEY (`id_libro`) REFERENCES `libro` (`id_libro`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_wish`
--

LOCK TABLES `detalle_wish` WRITE;
/*!40000 ALTER TABLE `detalle_wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `detalle_wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `devoluciones`
--

DROP TABLE IF EXISTS `devoluciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devoluciones` (
  `id_devolucion` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_compra` int(11) DEFAULT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `tipo_devolucion` varchar(30) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `id_libro` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_devolucion`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devoluciones`
--

LOCK TABLES `devoluciones` WRITE;
/*!40000 ALTER TABLE `devoluciones` DISABLE KEYS */;
INSERT INTO `devoluciones` VALUES (1,21,'ola','Defecto','2018-07-11',0),(2,23,'es libro esaba defectuoso','Defecto','2018-07-12',0),(3,25,'defectuoso','Defecto','2018-07-12',0),(4,26,'Slieron defectuosos los libros','Defecto','2018-07-12',0),(5,38,'Venia mal el libro','Defecto','2018-07-19',12),(6,39,'estaba feo','Defecto','2018-07-19',12);
/*!40000 ALTER TABLE `devoluciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `envio`
--

DROP TABLE IF EXISTS `envio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `envio` (
  `id_venta` int(11) NOT NULL AUTO_INCREMENT,
  `direccion` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id_venta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `envio`
--

LOCK TABLES `envio` WRITE;
/*!40000 ALTER TABLE `envio` DISABLE KEYS */;
/*!40000 ALTER TABLE `envio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `id_evento` int(10) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `cupo` int(10) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `costo` decimal(9,2) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_registro` date DEFAULT NULL,
  `fecha_evento` date DEFAULT NULL,
  `foto` varchar(200) DEFAULT NULL,
  `calificacion` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (1,'Evento curso de AJAX','Imagenes',30,'Activo',150.00,'Curso de AJAX para principantes desde 0','2018-07-07','2018-07-07','01.png',10),(2,'Evento curso de AJAX','Abierto',39,'Disponible',200.00,'Evento para principantes','2018-07-10','2018-01-01','02.png',10),(3,'Firma de autografos','Imagenes',10,'Activo',200.00,'hola','2018-07-12','2018-01-01','biodanza.jpg',10),(4,'Prueba evento','FirmaAutografos',0,'Activo',0.00,'ola','2018-07-12','2019-01-01','taller.png',0),(5,'Curso de JAva','Imagenes',20,'Activo',20.00,'Curso para principiantes','2018-07-22','2018-07-22','taller.png',10),(6,'Ejemplo','Imagenes',19,'Activo',20.00,'fdskljq','2018-07-22','2018-07-22','biodanza.jpg',10),(7,'Nuevo evento','Imagenes',18,'Activo',20.00,'Juan','2018-07-23','2018-07-23','biodanza.jpg',0),(8,'Veronica','Imagenes',19,'Activo',20.00,'Vero','2018-07-23','2018-07-24','30aniversario.jpg',10);
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libro` (
  `id_libro` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) DEFAULT NULL,
  `precio` int(10) DEFAULT NULL,
  `autor` varchar(40) DEFAULT NULL,
  `editorial` varchar(40) DEFAULT NULL,
  `categoria` varchar(40) DEFAULT NULL,
  `ano_publicacion` int(11) DEFAULT NULL,
  `descripcion` varchar(90) DEFAULT NULL,
  `status` varchar(40) DEFAULT NULL,
  `cantidad` int(10) DEFAULT NULL,
  `foto` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_libro`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
INSERT INTO `libro` VALUES (8,'Margaret',300,'Dos','ttres','cinco',6,'siete','ocho',10,'06.png'),(12,'Mitos',200,'sdamk','fdmslk','nfdskj',1,'dnsjk','nfkdsn',2,'03.png'),(14,'Anita',250,'Marifer','Esfinge','Novelas',2009,'Novela de anita la guerfanita','Disponible',20,'04.png');
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `premio`
--

DROP TABLE IF EXISTS `premio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `premio` (
  `id_premio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `descripcion` varchar(250) DEFAULT NULL,
  `fotopremio` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_premio`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `premio`
--

LOCK TABLES `premio` WRITE;
/*!40000 ALTER TABLE `premio` DISABLE KEYS */;
INSERT INTO `premio` VALUES (1,'La Vero',90,'Inactivo',1,'La mas mejor','08.jpg'),(2,'lampara',30,'Activo',0,'esta chida','03.jpg'),(3,'Yo mero',1000,'Activo',2,'soy el mejor','15.jpg');
/*!40000 ALTER TABLE `premio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regalo`
--

DROP TABLE IF EXISTS `regalo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regalo` (
  `id_regalo` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_compra` int(11) DEFAULT NULL,
  `envoltura` varchar(20) DEFAULT NULL,
  `tarjeta` int(10) DEFAULT NULL,
  `costo_envoltura` int(10) DEFAULT NULL,
  PRIMARY KEY (`id_regalo`),
  KEY `codigo_compra` (`codigo_compra`),
  CONSTRAINT `regalo_ibfk_1` FOREIGN KEY (`codigo_compra`) REFERENCES `venta` (`codigo_compra`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regalo`
--

LOCK TABLES `regalo` WRITE;
/*!40000 ALTER TABLE `regalo` DISABLE KEYS */;
/*!40000 ALTER TABLE `regalo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrar_ticket`
--

DROP TABLE IF EXISTS `registrar_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registrar_ticket` (
  `codigo_compra` int(11) NOT NULL,
  `status` varchar(30) DEFAULT NULL,
  `monto` int(11) DEFAULT NULL,
  `puntos_obtenidos` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo_compra`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrar_ticket`
--

LOCK TABLES `registrar_ticket` WRITE;
/*!40000 ALTER TABLE `registrar_ticket` DISABLE KEYS */;
INSERT INTO `registrar_ticket` VALUES (21,'Registrado',696,69),(30,'Registrado',638,63),(123,'Registrado',0,0),(21312,'Registrado',0,0);
/*!40000 ALTER TABLE `registrar_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registro` (
  `correo` varchar(100) NOT NULL,
  `contra` varchar(100) DEFAULT NULL,
  `usuario` varchar(30) DEFAULT NULL,
  `tipo_usuario` enum('Administrador','Cliente') DEFAULT NULL,
  `codigo_conf` int(11) DEFAULT NULL,
  `estado_conf` enum('Pendiente','Confirmado') DEFAULT NULL,
  PRIMARY KEY (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro`
--

LOCK TABLES `registro` WRITE;
/*!40000 ALTER TABLE `registro` DISABLE KEYS */;
INSERT INTO `registro` VALUES ('juanalva432@gmail.com','juan123','juanalva432@gmail.com','Cliente',0,'Confirmado'),('kitsunei1997120700@hotmail.com','cliente123456','kitsunei','Cliente',1234543,'Confirmado'),('SmilinMoonLibraryLocal@gmail.com','smiling123456','Smiling','Administrador',1234543,'Confirmado'),('vero.cure24@gmail.com','1234','vero.cure24@gmail.com','Cliente',0,'Confirmado');
/*!40000 ALTER TABLE `registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saldo_credito`
--

DROP TABLE IF EXISTS `saldo_credito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saldo_credito` (
  `saldocredito` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `saldo` int(10) DEFAULT NULL,
  `codigo_tarjetacredito` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`saldocredito`),
  KEY `id_usuario` (`id_usuario`),
  KEY `codigo_tarjetacredito` (`codigo_tarjetacredito`),
  CONSTRAINT `saldo_credito_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saldo_credito`
--

LOCK TABLES `saldo_credito` WRITE;
/*!40000 ALTER TABLE `saldo_credito` DISABLE KEYS */;
/*!40000 ALTER TABLE `saldo_credito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saldo_prepago`
--

DROP TABLE IF EXISTS `saldo_prepago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saldo_prepago` (
  `saldoprepago` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `saldo` int(10) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `codigo_tarjeta` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`saldoprepago`),
  KEY `id_usuario` (`id_usuario`),
  KEY `codigo_tarjeta` (`codigo_tarjeta`),
  CONSTRAINT `saldo_prepago_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saldo_prepago`
--

LOCK TABLES `saldo_prepago` WRITE;
/*!40000 ALTER TABLE `saldo_prepago` DISABLE KEYS */;
/*!40000 ALTER TABLE `saldo_prepago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sharedwishlist`
--

DROP TABLE IF EXISTS `sharedwishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sharedwishlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_wishlist` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_usuario_shared` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharedwishlist`
--

LOCK TABLES `sharedwishlist` WRITE;
/*!40000 ALTER TABLE `sharedwishlist` DISABLE KEYS */;
INSERT INTO `sharedwishlist` VALUES (1,1,3,2),(2,2,3,2),(3,4,4,3),(4,5,4,3);
/*!40000 ALTER TABLE `sharedwishlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sugerencia`
--

DROP TABLE IF EXISTS `sugerencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sugerencia` (
  `id_sugerencia` int(11) NOT NULL AUTO_INCREMENT,
  `nom_libro` varchar(20) DEFAULT NULL,
  `editorial` varchar(40) DEFAULT NULL,
  `autor` varchar(40) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_sugerencia`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `sugerencia_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sugerencia`
--

LOCK TABLES `sugerencia` WRITE;
/*!40000 ALTER TABLE `sugerencia` DISABLE KEYS */;
INSERT INTO `sugerencia` VALUES (1,'Cien años de soledad','esfinge','Gabriel garcia marquez','2018-07-07',2),(2,'el principito','esfinge','no se','2018-07-07',2),(3,'udjk','nkcs','knsnk','2018-07-12',2);
/*!40000 ALTER TABLE `sugerencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta`
--

DROP TABLE IF EXISTS `tarjeta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta` (
  `idTarjeta` int(11) NOT NULL AUTO_INCREMENT,
  `costos` int(10) DEFAULT NULL,
  `noTarjeta` bigint(16) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `idUsuario` int(11) DEFAULT NULL,
  `vigencia` date DEFAULT NULL,
  PRIMARY KEY (`idTarjeta`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta`
--

LOCK TABLES `tarjeta` WRITE;
/*!40000 ALTER TABLE `tarjeta` DISABLE KEYS */;
INSERT INTO `tarjeta` VALUES (1,25,1010101010101010,'Ocupado',7,'2019-05-25'),(2,30,4564564678975648,'DISPONIBLE',0,'2019-04-17'),(3,50,2124444545788788,'OCUPADO',0,'2019-06-01');
/*!40000 ALTER TABLE `tarjeta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_credito`
--

DROP TABLE IF EXISTS `tarjeta_credito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_credito` (
  `codigo_tarjetacredito` varchar(16) NOT NULL,
  `estado` varchar(200) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `saldo` float DEFAULT NULL,
  `saldo_credito` float DEFAULT NULL,
  PRIMARY KEY (`codigo_tarjetacredito`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `tarjeta_credito_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_credito`
--

LOCK TABLES `tarjeta_credito` WRITE;
/*!40000 ALTER TABLE `tarjeta_credito` DISABLE KEYS */;
INSERT INTO `tarjeta_credito` VALUES ('123321','Activada',2,-200,3000),('1234567897658','Activada',7,2063,3000),('4564564678975649','Activada',4,2258,3000);
/*!40000 ALTER TABLE `tarjeta_credito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjeta_prepago`
--

DROP TABLE IF EXISTS `tarjeta_prepago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tarjeta_prepago` (
  `codigo_tarjeta` varchar(16) NOT NULL,
  `estado` varchar(100) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `saldo` float DEFAULT NULL,
  `saldo_prepago` float DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo_tarjeta`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `tarjeta_prepago_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjeta_prepago`
--

LOCK TABLES `tarjeta_prepago` WRITE;
/*!40000 ALTER TABLE `tarjeta_prepago` DISABLE KEYS */;
INSERT INTO `tarjeta_prepago` VALUES ('1010101010101010','Activada',7,225,NULL,NULL),('123322','Activada',2,1150,3000,132),('4564564678975648','Activada',4,1274,NULL,240);
/*!40000 ALTER TABLE `tarjeta_prepago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `id_ticket` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(100) DEFAULT NULL,
  `monto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_ticket`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'ACTIVO',100),(2,'ACTIVO',300),(3,'ACTIVO',200);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `correo` varchar(100) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `apaterno` varchar(30) DEFAULT NULL,
  `amaterno` varchar(30) DEFAULT NULL,
  `edad` int(3) DEFAULT NULL,
  `sexo` enum('F','M') DEFAULT NULL,
  `telefono` bigint(10) DEFAULT NULL,
  `calle` varchar(50) DEFAULT NULL,
  `colonia` varchar(50) DEFAULT NULL,
  `municipio` varchar(50) DEFAULT NULL,
  `estado` varchar(30) DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  KEY `correo` (`correo`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`correo`) REFERENCES `registro` (`correo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'SmilinMoonLibraryLocal@gmail.com','Pedro','Reyes','Torres',23,'F',5543564432,'Manuel Buen dia','Nose','Tecamac','Mexico','Admin','123'),(2,'kitsunei1997120700@hotmail.com','Gustavo','Padilla','Ruiz',23,'F',5543564432,'Manuel Buen dia','Tecamac','Tecamac','Mexico','Cliente','123'),(4,'vero.cure24@gmail.com','veronica','chavez','torres',24,'F',5515036628,'san francisco','santo domingo','tecamac','mexico','Cliente','1234'),(7,'juanalva432@gmail.com','Juan','Alva','Bustamante',20,'M',5516365615,'Trevi','Zumpang','Zumpango','Mexico','Cliente','juan123');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venta` (
  `codigo_compra` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_pago` varchar(20) DEFAULT NULL,
  `monto` int(10) DEFAULT NULL,
  `direccion` varchar(80) DEFAULT NULL,
  `puntos_conpra` int(10) DEFAULT NULL,
  `tipo_compra` varchar(60) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `cantidad_libros` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo_compra`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (39,'credito',812,'Trevi, Zumpang , Zumpango , Mexico',81,'propia','2018-07-19',7,1);
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wish`
--

DROP TABLE IF EXISTS `wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wish` (
  `id_wish` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `numero_libros` int(2) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `fecha_expiracion` date DEFAULT NULL,
  PRIMARY KEY (`id_wish`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `wish_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wish`
--

LOCK TABLES `wish` WRITE;
/*!40000 ALTER TABLE `wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wishlist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `id_libro` int(11) DEFAULT NULL,
  `estado` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
/*!40000 ALTER TABLE `wishlist` DISABLE KEYS */;
INSERT INTO `wishlist` VALUES (1,3,13,''),(2,3,8,''),(4,4,8,''),(5,4,12,''),(6,4,8,''),(7,4,12,''),(8,4,14,''),(18,2,14,''),(19,2,12,''),(20,2,8,''),(21,7,8,''),(22,7,12,''),(23,7,14,'');
/*!40000 ALTER TABLE `wishlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-26  0:13:54
