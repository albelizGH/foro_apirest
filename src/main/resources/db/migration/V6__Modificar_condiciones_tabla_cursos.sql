ALTER TABLE cursos
ADD CONSTRAINT unique_nombre_categoria UNIQUE (nombre, categoria);