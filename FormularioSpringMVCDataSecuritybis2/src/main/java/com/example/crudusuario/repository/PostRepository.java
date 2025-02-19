// Repositorio para acceder a los posts desde la base de datos
package com.example.crudusuario.repository;

import com.example.crudusuario.model.Post;
import com.example.crudusuario.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("SELECT p FROM Post p WHERE p.usuario != :usuario")
    List<Post> findByUsuarioNot(@Param("usuario") Usuario usuario);
}
