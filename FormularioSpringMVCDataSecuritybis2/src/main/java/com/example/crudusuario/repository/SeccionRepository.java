
package com.example.crudusuario.repository;

import com.example.crudusuario.model.Seccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    // You can add custom queries if needed
	Optional<Seccion> findByNombre(String nombre);
}
