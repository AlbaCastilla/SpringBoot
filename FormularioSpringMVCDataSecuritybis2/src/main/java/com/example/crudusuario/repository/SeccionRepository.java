
package com.example.crudusuario.repository;

import com.example.crudusuario.model.Seccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


//JpaRepository es una interfaz proporcionada por Spring Data JPA que facilita la interacción con la base de datos.
//Extiende la interfaz CrudRepository, pero proporciona métodos adicionales para realizar operaciones 

//El primer parámetro <Seccion> indica el tipo de entidad que el repositorio gestionará. En este caso, el repositorio maneja objetos de tipo Seccion, que representa una tabla en la base de datos.

//El segundo parámetro <Long> es el tipo del identificador (ID) de la entidad. En este caso, el ID de la entidad Seccion es de tipo Long, que es un tipo de dato numérico que típicamente representa un identificador único de un registro en la base de datos.

//Usando JpaRepository, Spring automáticamente proporciona la implementación de los métodos básicos para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la entidad Seccion.
//También permite agregar consultas personalizadas como la que hemos visto en el ejemplo (findByNombre).
public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    
	// Este método busca una sección en la base de datos por su nombre. 
	// Devuelve un Optional de Seccion, lo que significa que puede devolver un valor presente (la sección encontrada) o vacío si no se encuentra ninguna sección con el nombre proporcionado.
	Optional<Seccion> findByNombre(String nombre);
}
