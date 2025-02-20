// Controlador para manejar las peticiones relacionadas con los posts
package com.example.crudusuario.controller;

import com.example.crudusuario.model.Post;
import com.example.crudusuario.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

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
        return "user/create-post";  // Thymeleaf template for the form
    }

    @PostMapping("/create/posts")
    public String createPost(@ModelAttribute Post post) {
        postService.createPost(post);  // Save the post using the service layer
        return "redirect:/list/posts";  // Redirect after form submission
    }
    
    /*@GetMapping("/create/posts")
 public String showCreatePostForm(Model model) {
     model.addAttribute("post", new Post());
     return "user/create-post";
 }

 @PostMapping("/create/posts")
 public String createPost(@ModelAttribute Post post) {
     postService.createPost(post);
     return "redirect:/list/posts";
 }*/
}