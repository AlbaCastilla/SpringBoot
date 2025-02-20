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
    
}