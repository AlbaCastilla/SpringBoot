// Controlador para manejar las peticiones relacionadas con los posts
package com.example.crudusuario.controller;

import com.example.crudusuario.model.Post;
import com.example.crudusuario.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/list/posts")
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "user/list-posts"; 
    }
    
    @GetMapping("/list/posts/not")
    public String listPostsNot(Model model) {
        List<Post> posts = postService.getPostsDeOtrosUsuarios();
        model.addAttribute("posts", posts);
        return "user/list-posts"; // Nombre del archivo HTML en templates/user/
    }
}
