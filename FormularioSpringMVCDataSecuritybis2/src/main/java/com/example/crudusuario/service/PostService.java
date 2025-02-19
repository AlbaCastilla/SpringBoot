package com.example.crudusuario.service;

import com.example.crudusuario.model.Post;
import com.example.crudusuario.model.Usuario;
import com.example.crudusuario.repository.PostRepository;
import com.example.crudusuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UsuarioRepository usuarioRepository;

    public PostService(PostRepository postRepository, UsuarioRepository usuarioRepository) {
        this.postRepository = postRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Método para obtener todos los posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Método para obtener solo los posts que no sean del usuario autenticado
    public List<Post> getPostsDeOtrosUsuarios() {
        // Obtener el usuario actualmente autenticado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Buscar el usuario en la base de datos
        Usuario usuarioActual = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener los posts que no sean del usuario actual
        return postRepository.findByUsuarioNot(usuarioActual);
    }
}
