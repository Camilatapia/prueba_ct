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


-- Volcando estructura de base de datos para alumnos
CREATE DATABASE IF NOT EXISTS `alumnos` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `alumnos`;

-- Volcando estructura para tabla alumnos.curso
CREATE TABLE IF NOT EXISTS `curso` (
  `id` int NOT NULL,
  `titulo` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `imagen` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `precio` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `titulo` (`titulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alumnos.curso: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
REPLACE INTO `curso` (`id`, `titulo`, `imagen`, `precio`) VALUES
	(1, 'Programacion', 'imagen1.png', 100),
	(2, 'Física', 'imagen2.png', 150),
	(3, 'Matemáticas', 'imagen3.png', 200),
	(4, 'Algebra', 'imagen4.png', 250);
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;

-- Volcando estructura para tabla alumnos.curso_comprado
CREATE TABLE IF NOT EXISTS `curso_comprado` (
  `id_curso` int NOT NULL,
  `id_persona` int NOT NULL,
  KEY `id_curso` (`id_curso`),
  KEY `id_persona` (`id_persona`),
  CONSTRAINT `id_curso` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `id_persona` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alumnos.curso_comprado: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `curso_comprado` DISABLE KEYS */;
REPLACE INTO `curso_comprado` (`id_curso`, `id_persona`) VALUES
	(1, 1),
	(2, 1),
	(3, 2),
	(4, 2),
	(1, 4);
/*!40000 ALTER TABLE `curso_comprado` ENABLE KEYS */;

-- Volcando estructura para tabla alumnos.persona
CREATE TABLE IF NOT EXISTS `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '',
  `avatar` varchar(250) NOT NULL DEFAULT '',
  `sexo` varchar(10) NOT NULL DEFAULT '',
  UNIQUE KEY `nombre` (`nombre`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alumnos.persona: ~5 rows (aproximadamente)
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
REPLACE INTO `persona` (`id`, `nombre`, `avatar`, `sexo`) VALUES
	(1, 'Camy', 'avatar2.png', 'm'),
	(2, 'El Loli', 'avatar3.png', 'h'),
	(4, 'Rosita', 'avatar5.png', 'm'),
	(5, 'Sam', 'avatar6.png', 'h'),
	(8, 'Txejo', 'avatar7.png', 'h');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
