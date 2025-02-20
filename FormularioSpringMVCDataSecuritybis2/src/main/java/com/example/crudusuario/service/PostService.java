package com.example.crudusuario.service;

import com.example.crudusuario.model.Post;
import com.example.crudusuario.model.Seccion;
import com.example.crudusuario.model.Usuario;
import com.example.crudusuario.repository.PostRepository;
import com.example.crudusuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UsuarioRepository usuarioRepository;
    private final SeccionService seccionService;

    public PostService(PostRepository postRepository, UsuarioRepository usuarioRepository, SeccionService seccionService) {
        this.postRepository = postRepository;
        this.usuarioRepository = usuarioRepository;
        this.seccionService = seccionService;
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
    
 // Método para obtener los posts del usuario autenticado
    public List<Post> getPostsEsteUsuario() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Usuario usuarioActual = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return postRepository.findByUsuario(usuarioActual);
    }
    
    public Post createPost(Post post, Long seccionId) {
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

        // Establecer el usuario del post
        post.setUsuario(usuarioActual);

        // Establecer la fecha actual
        post.setFecha(LocalDateTime.now());

        // Obtener la sección seleccionada
        Seccion seccion = seccionService.getSeccionById(seccionId);
        post.setSeccion(seccion);

        // Guardar el post
        return postRepository.save(post);
    }

    
    
    public Post createPost(Post post) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Usuario usuarioActual = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Retrieve Seccion by ID
        Long seccionId = post.getSeccion().getId();
        
        if (seccionId == null) {
            throw new RuntimeException("Sección ID is null");
        }

        // Retrieve Seccion by ID using SeccionService
        Seccion seccion = seccionService.getSeccionById(seccionId);

        post.setSeccion(seccion);
        post.setFecha(LocalDateTime.now());

        // Optionally set a default subtitulo
        if (post.getSubtitulo() == null || post.getSubtitulo().isEmpty()) {
            post.setSubtitulo("Default Subtitulo");
        }

        post.setUsuario(usuarioActual);
        return postRepository.save(post);
    }

}
