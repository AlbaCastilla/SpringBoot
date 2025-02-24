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

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));
    }
/*
    public Post editarPost(Long id, Post postEditado) {
        Post postExistente = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        postExistente.setTitulo(postEditado.getTitulo());
        postExistente.setSubtitulo(postEditado.getSubtitulo());
        postExistente.setTexto(postEditado.getTexto());

        if (postEditado.getSeccion() != null && postEditado.getSeccion().getId() != null) {
            Seccion seccion = seccionService.getSeccionById(postEditado.getSeccion().getId());
            postExistente.setSeccion(seccion);
        }

        return postRepository.save(postExistente);
    }
    public Post editarPost(Long id, Post postEditado) {
        Post postExistente = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        postExistente.setTitulo(postEditado.getTitulo());
        postExistente.setSubtitulo(postEditado.getSubtitulo());
        postExistente.setTexto(postEditado.getTexto());

        if (postEditado.getSeccion() != null && postEditado.getSeccion().getId() != null) {
            System.out.println("Sección enviada desde el formulario: " + postEditado.getSeccion().getId());
            Seccion seccion = seccionService.getSeccionById(postEditado.getSeccion().getId());
            postExistente.setSeccion(seccion);
        } else {
            System.out.println("Sección no enviada o ID nulo.");
        }

        return postRepository.save(postExistente);
    }*/
    public Post editarPost(Long id, Post postEditado, Long seccionId) {
        Post postExistente = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado"));

        postExistente.setTitulo(postEditado.getTitulo());
        postExistente.setSubtitulo(postEditado.getSubtitulo());
        postExistente.setTexto(postEditado.getTexto());

        // Forzar la actualización de la sección
        Seccion seccion = seccionService.getSeccionById(seccionId);
        postExistente.setSeccion(seccion);

        return postRepository.save(postExistente);
    }

    
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }


    // Método para obtener solo los posts que no sean del usuario autenticado
    public List<Post> getPostsDeOtrosUsuarios() {
    	// Obtiene el principal (usuario autenticado) del contexto de seguridad de Spring Security
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

     // Verifica si el objeto principal es una instancia de UserDetails (usuario autenticado con detalles de usuario)
        if (principal instanceof UserDetails) {
        	// Verifica si el objeto 'principal' es una instancia de 'UserDetails' (es decir, si el usuario autenticado contiene detalles de usuario)
        	// Si lo es, se realiza un cast a 'UserDetails' para poder acceder a su nombre de usuario (username)
        	// Si no es una instancia de 'UserDetails', se usa el método 'toString()' para obtener una representación del objeto principal
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
