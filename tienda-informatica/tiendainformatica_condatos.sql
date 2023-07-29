-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 22-11-2022 a las 20:19:19
-- Versión del servidor: 8.0.28
-- Versión de PHP: 8.0.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tiendainformatica`
--

DROP DATABASE IF EXISTS tiendainformatica;
CREATE DATABASE tiendainformatica;

USE tiendainformatica;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id_categoria` int UNSIGNED NOT NULL,
  `nombre` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id_categoria`, `nombre`) VALUES
(1, 'Placas base'),
(2, 'Procesadores'),
(3, 'Discos duros'),
(4, 'Tarjetas graficas'),
(5, 'Memorias RAM'),
(6, 'Cajas'),
(7, 'Ventilacion'),
(8, 'Fuentes de alimentacion'),
(9, 'Portatiles'),
(10, 'Sobremesa'),
(11, 'Smartphones'),
(12, 'Smartwatches'),
(13, 'Tablets'),
(14, 'Camaras'),
(15, 'Monitores'),
(16, 'Teclados'),
(17, 'Auriculares'),
(18, 'Altavoces'),
(19, 'Ratones'),
(20, 'Consolas'),
(21, 'Impresoras');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estados`
--

CREATE TABLE `estados` (
  `id_estado` int UNSIGNED NOT NULL,
  `nombre` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `estados`
--

INSERT INTO `estados` (`id_estado`, `nombre`) VALUES
(1, 'Pagado'),
(2, 'Preparando'),
(3, 'Enviado'),
(4, 'Entregado'),
(5, 'Cancelado'),
(6, 'Devuelto'),
(7, 'Carrito');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturas`
--

CREATE TABLE `facturas` (
  `id_factura` int UNSIGNED NOT NULL,
  `id_pedido` int UNSIGNED NOT NULL,
  `id_producto` int UNSIGNED NOT NULL,
  `cantidad` int UNSIGNED NOT NULL,
  `precio` decimal(8,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id_pedido` int UNSIGNED NOT NULL,
  `id_usuario` char(9) NOT NULL,
  `fecha` timestamp NOT NULL,
  `estado` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_producto` int UNSIGNED NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `categoria` int UNSIGNED NOT NULL,
  `precio` decimal(8,2) NOT NULL,
  `stock` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `nombre`, `descripcion`, `categoria`, `precio`, `stock`) VALUES
(1, 'Xiaomi Redmi Note 10', 'color gris', 11, '229.90', 30),
(2, 'Asus Prime B550-PLUS', 'nueva', 1, '199.90', 5),
(3, 'Samsung 870 EVO SSD', 'capacidad: 1TB', 3, '102.40', 23),
(4, 'ASUS TUF Gaming GeForce GTX 1660', '6GB', 4, '259.99', 8),
(5, 'iPhone 14 Pro', '256GB', 11, '1229.99', 10),
(6, 'Tempest Spook RGB Torre ATX', 'color blanco', 6, '35.99', 40),
(7, 'Nokia 3310', 'color azul', 11, '59.90', 20),
(8, 'Canon 90D 32MP', 'color negro', 14, '1190.90', 34),
(9, 'Logitech G23 Prodigy', 'teclado gaming', 16, '40.20', 50),
(10, 'Genesis Thor 300', 'teclado mecanico RGB', 16, '29.90', 30),
(11, 'PlayStation 5', '2 juegos de regalo', 20, '499.90', 5),
(12, 'Nintendo Switch', 'Con Pokemon Purpura', 20, '329.90', 27),
(13, 'Lenovo Ideapad 3 8GB RAM', 'color negro, almacenamiendo SSD 512GB', 9, '879.90', 10),
(14, 'Dell XPS 13', 'color: space gray', 9, '945.80', 14),
(15, 'Samsung Galaxy S22', '128GB 6GM Ram', 11, '799.99', 40);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id_rol` int UNSIGNED NOT NULL,
  `nombre` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id_rol`, `nombre`) VALUES
(1, 'admin'),
(2, 'user');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` char(9) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `password` varchar(250) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `telefono` int DEFAULT NULL,
  `rol` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre`, `password`, `direccion`, `telefono`, `rol`) VALUES
('12345678L', 'Laura', '$2a$10$bUNy964zrAT4rOLQNz14bOujEOKBDBwsApjAbxxOWDGW2tIiaqTE2', 'Calle Dia 12', 922369852, 2),
('74185236T', 'Tania', '$2a$10$3sL.Xb1yQPakpGV.8np3HuBPP4As5seh0RsDE4XblAD2upwIMaAxO', 'Avenida Amanecer 3', 654987321, 2),
('87654321F', 'Carlos', '$2a$10$Vvh8K4EfylkP8DKLrD/NK.7s6FFzHIKhYFe5jpQUs9h1OAuaDxa3a', 'Calle Noche 23', 0, 2),
('admin', 'admin', '$2a$10$6QsIKodIAgRcr5L/xNBcQunXy6qh0WSQhyBpn12kHal58xNewFNua', '-', 0, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `estados`
--
ALTER TABLE `estados`
  ADD PRIMARY KEY (`id_estado`);

--
-- Indices de la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD PRIMARY KEY (`id_factura`),
  ADD KEY `fk_facturasped` (`id_pedido`),
  ADD KEY `fk_facturasprod` (`id_producto`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `fk_pedidos` (`estado`),
  ADD KEY `fk_pedidosusr` (`id_usuario`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `fk_productos` (`categoria`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `fk_usuarios` (`rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id_categoria` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `estados`
--
ALTER TABLE `estados`
  MODIFY `id_estado` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `facturas`
--
ALTER TABLE `facturas`
  MODIFY `id_factura` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_pedido` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_producto` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id_rol` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `facturas`
--
ALTER TABLE `facturas`
  ADD CONSTRAINT `fk_facturasped` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`),
  ADD CONSTRAINT `fk_facturasprod` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `fk_pedidos` FOREIGN KEY (`estado`) REFERENCES `estados` (`id_estado`),
  ADD CONSTRAINT `fk_pedidosusr` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_productos` FOREIGN KEY (`categoria`) REFERENCES `categorias` (`id_categoria`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `fk_usuarios` FOREIGN KEY (`rol`) REFERENCES `roles` (`id_rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;