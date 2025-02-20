// Controlador para manejar las peticiones relacionadas con los posts
package com.example.crudusuario.controller;

import com.example.crudusuario.model.Post;
import com.example.crudusuario.service.PostService;
import com.example.crudusuario.service.SeccionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {
	private final PostService postService;
    private final SeccionService seccionService;

    @Autowired
    public PostController(PostService postService, SeccionService seccionService) {
        this.postService = postService;
        this.seccionService = seccionService;
    }

    @GetMapping("/list/posts")
    public String listPosts(Model model) {
        List<Post> posts = postService.getPostsEsteUsuario();
        model.addAttribute("posts", posts);
        return "user/list-posts"; 
    }
    
    @GetMapping("/list/posts/not")
    public String listPostsNot(Model model) {
        List<Post> posts = postService.getPostsDeOtrosUsuarios();
        model.addAttribute("posts", posts);
        return "user/list-posts2"; // Nombre del archivo HTML en templates/user/
    }

    @GetMapping("/create/posts")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("secciones", seccionService.getAllSecciones()); 
        return "user/create-post";  // Thymeleaf template for the form
    }

    @PostMapping("/create/posts")
    public String createPost(@ModelAttribute Post post, @RequestParam Long seccionId) {
        postService.createPost(post, seccionId);
        return "redirect:/list/posts";  // Redirigir a la lista de posts después de guardar el post
    }
    
    /*@GetMapping("/edit/posts/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("secciones", seccionService.getAllSecciones());
        return "user/edit-post";
    }

    @PostMapping("/edit/posts")
    public String updatePost(@RequestParam("id") Long id, @ModelAttribute Post postEditado) {
        postService.editarPost(id, postEditado);
        return "redirect:/list/posts";  // Redirige a la lista después de editar
    }
    @PostMapping("/edit/posts")
    public String updatePost(@ModelAttribute Post postEditado) {
        postService.editarPost(postEditado.getId(), postEditado);
        return "redirect:/list/posts";  // Redirige a la lista después de editar
    }
    */@GetMapping("/edit/posts/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        if (post == null) {
            throw new IllegalArgumentException("No se encontró un post con el ID: " + id);
        }
        model.addAttribute("post", post);
        model.addAttribute("secciones", seccionService.getAllSecciones());
        return "user/edit-post";
    }
    @PostMapping("/edit/posts")
    public String updatePost(@RequestParam("id") Long id, @ModelAttribute Post postEditado) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del post no puede ser nulo.");
        }
        postService.editarPost(id, postEditado);
        return "redirect:/list/posts";  
    }

    
}