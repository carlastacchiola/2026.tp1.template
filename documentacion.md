Decisiones de Diseño – Sistema BiblioTech
1. Arquitectura del sistema

Se decidió utilizar una arquitectura por capas separando el sistema en:

model: entidades del dominio (Recurso, Socio, Prestamo, etc.)
repository: acceso a datos en memoria
service: lógica de negocio
exception: manejo de errores personalizados

Esta separación permite organizar mejor el código y mantener responsabilidades claras.

2. Uso de interfaces y desacoplamiento

Se implementaron interfaces para los repositorios (Repository, RecursoRepository, etc.) con el objetivo de desacoplar la lógica de negocio de la implementación concreta.

Las clases *InMemory implementan estas interfaces, permitiendo cambiar fácilmente la forma de almacenamiento en el futuro.

3. Manejo de errores

Se creó una jerarquía de excepciones personalizadas que heredan de BibliotecaException.

Esto permite:

manejar errores de forma controlada
evitar el uso de excepciones genéricas
mejorar la claridad del código
4. Uso de Optional

Se utilizó Optional en los métodos de búsqueda de los repositorios para evitar el uso de null y reducir errores.

Esto obliga a manejar correctamente los casos en los que un elemento no existe.

5. Modelado del dominio

Se definieron las entidades principales:

Recurso (con subtipos LibroFisico y EBook)
Socio (con subtipos Estudiante y Docente)
Prestamo

Se utilizó herencia para representar los distintos tipos de socios y recursos.

6. Separación de responsabilidades

Se evitó mezclar lógica de negocio con acceso a datos:

Los services contienen las reglas del sistema (préstamos, validaciones, etc.)
Los repositories solo almacenan y recuperan datos
7. Clase Main

Se implementó una clase Main que actúa como orquestador del sistema, permitiendo probar el flujo completo:

registro de recurso
registro de socio
préstamo
devolución
8. Metodología de trabajo

Se utilizó GitHub siguiendo el flujo indicado:

creación de Issues por funcionalidad
desarrollo en ramas separadas
commits siguiendo Conventional Commits
uso de Pull Requests para integrar cambios