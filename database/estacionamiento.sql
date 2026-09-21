-- =========================================================
-- Parcial II - Sistema de Estacionamiento
-- Base de datos: parcial2_estacionamiento
-- =========================================================

-- 1. Crear la base de datos
-- (Asegúrate de ejecutar esta línea primero estando conectado a la base normal "postgres")
CREATE DATABASE parcial2_estacionamiento;

-- (Luego conéctate a la base "parcial2_estacionamiento" antes de seguir con lo demás)

-- =========================================================
-- 2. Creación de la tabla principal de vehículos
-- =========================================================
CREATE TABLE vehiculo (
    id                 SERIAL PRIMARY KEY,
    placa              VARCHAR(10)     NOT NULL UNIQUE, -- No se puede repetir la placa
    propietario        VARCHAR(100)    NOT NULL,
    tipo               VARCHAR(20)     NOT NULL,
    hora_ingreso       TIMESTAMP       NOT NULL DEFAULT NOW(),
    horas_utilizadas   NUMERIC(5,2)    NOT NULL CHECK (horas_utilizadas > 0), -- Obliga a que sean horas reales mayores a cero
    costo              NUMERIC(8,2)    NOT NULL CHECK (costo >= 0),           -- Evita costos negativos
    activo             BOOLEAN         NOT NULL DEFAULT TRUE                  -- True significa que sigue adentro
);

-- =========================================================
-- 3. Inserción de los registros iniciales (mínimo 5)
-- =========================================================
INSERT INTO vehiculo (placa, propietario, tipo, horas_utilizadas, costo) VALUES
('P123ABC', 'Juan Perez',      'Automovil',   3.0, 30.00),
('P456DEF', 'Maria Lopez',     'Automovil',   7.0, 63.00),
('M789GHI', 'Carlos Ramirez',  'Motocicleta', 2.0, 12.00),
('M321JKL', 'Ana Gonzalez',    'Motocicleta', 6.0, 32.40),
('P654MNO', 'Luis Hernandez',  'Automovil',   1.5, 15.00);

-- =========================================================
-- 4. Ver toda la tabla mostrando todas sus columnas
-- =========================================================
SELECT id, placa, propietario, tipo, hora_ingreso, horas_utilizadas, costo, activo
FROM vehiculo;

-- =========================================================
-- 5. Buscar solo los vehículos de tipo Automóvil
-- =========================================================
SELECT id, placa, propietario, horas_utilizadas, costo
FROM vehiculo
WHERE tipo = 'Automovil';

-- =========================================================
-- 6. Buscar vehículos que hayan costado más de 20 quetzales
-- =========================================================
SELECT id, placa, propietario, tipo, costo
FROM vehiculo
WHERE costo > 20.00;

-- =========================================================
-- 7. Mostrar la lista ordenada del más caro al más barato
-- =========================================================
SELECT id, placa, propietario, tipo, costo
FROM vehiculo
ORDER BY costo DESC;

-- =========================================================
-- 8. Modificar los datos de un vehículo (Actualizar)
-- =========================================================
UPDATE vehiculo
SET horas_utilizadas = 4.0,
    costo = 40.00
WHERE placa = 'P123ABC';

-- =========================================================
-- 9. Cambiar el estado a falso para simular que ya salió del parqueo
-- =========================================================
UPDATE vehiculo
SET activo = FALSE
WHERE placa = 'M789GHI';

-- =========================================================
-- 10. Borrar un registro de la tabla (Eliminar)
-- =========================================================
DELETE FROM vehiculo
WHERE placa = 'P654MNO';

-- =========================================================
-- 11. Pruebas para demostrar que las restricciones funcionan (provocan error a propósito)
-- =========================================================

-- 11.a Intentar meter una placa que ya existe (debe fallar por la regla UNIQUE)
INSERT INTO vehiculo (placa, propietario, tipo, horas_utilizadas, costo)
VALUES ('P456DEF', 'Pedro Duplicado', 'Automovil', 2.0, 20.00);

-- 11.b Intentar meter horas en negativo (debe fallar por la regla CHECK)
INSERT INTO vehiculo (placa, propietario, tipo, horas_utilizadas, costo)
VALUES ('P999ZZZ', 'Sofia Invalida', 'Automovil', -3.0, 10.00);
