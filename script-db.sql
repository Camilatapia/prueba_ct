-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.19 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para alumnosv3
CREATE DATABASE IF NOT EXISTS `alumnosv3` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `alumnosv3`;

-- Volcando estructura para tabla alumnosv3.curso
CREATE TABLE IF NOT EXISTS `curso` (
  `id` int NOT NULL,
  `titulo` varchar(250) NOT NULL DEFAULT '',
  `imagen` varchar(250) NOT NULL DEFAULT '',
  `precio` float NOT NULL DEFAULT '0',
  `id_profesor` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `titulo` (`titulo`),
  KEY `id_profesor` (`id_profesor`) USING BTREE,
  CONSTRAINT `id_profesor` FOREIGN KEY (`id_profesor`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alumnosv3.curso: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
REPLACE INTO `curso` (`id`, `titulo`, `imagen`, `precio`, `id_profesor`) VALUES
	(1, 'HTML\r\n', 'imagen1.png', 100, 3),
	(2, 'CSS', 'imagen1.png', 123, 4),
	(3, 'JAVA', 'imagen1.png', 122, 6),
	(4, 'EXCEL', 'imagen1.png', 150, 6),
	(5, 'WORD', 'imagen1.png', 200, 3);
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;

-- Volcando estructura para tabla alumnosv3.curso_comprado
CREATE TABLE IF NOT EXISTS `curso_comprado` (
  `id_persona` int NOT NULL,
  `id_curso` int NOT NULL,
  PRIMARY KEY (`id_persona`,`id_curso`),
  KEY `id_curso` (`id_curso`),
  CONSTRAINT `id_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`),
  CONSTRAINT `id_persona` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alumnosv3.curso_comprado: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `curso_comprado` DISABLE KEYS */;
REPLACE INTO `curso_comprado` (`id_persona`, `id_curso`) VALUES
	(2, 1),
	(4, 1),
	(11, 1),
	(6, 2),
	(1, 3),
	(1, 4),
	(2, 4),
	(3, 4),
	(9, 4),
	(2, 5),
	(3, 5),
	(9, 5);
/*!40000 ALTER TABLE `curso_comprado` ENABLE KEYS */;

-- Volcando estructura para tabla alumnosv3.persona
CREATE TABLE IF NOT EXISTS `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `avatar` varchar(250) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `id_rol` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `id_rol` (`id_rol`),
  CONSTRAINT `id_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alumnosv3.persona: ~9 rows (aproximadamente)
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
REPLACE INTO `persona` (`id`, `nombre`, `avatar`, `sexo`, `id_rol`) VALUES
	(1, 'Camila', 'avatar1.png', 'm', 1),
	(2, 'Alumno1', 'avatar3.png', 'h', 1),
	(3, 'Profesor1', 'avatar3.png', 'h', 2),
	(4, 'Profesor2', 'avatar2.png', 'm', 2),
	(6, 'Profesora3', 'avatar1.png', 'm', 2),
	(9, 'Alumno2', 'avatar4.png', 'h', 1),
	(11, 'Alumno3', 'avatar6.png', 'h', 1),
	(13, 'Alumnomodificado4', 'avatar7.png', 'h', 1);
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;

-- Volcando estructura para tabla alumnosv3.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int NOT NULL,
  `nombre` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alumnosv3.rol: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
REPLACE INTO `rol` (`id`, `nombre`) VALUES
	(1, 'alumno'),
	(2, 'profesor');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
