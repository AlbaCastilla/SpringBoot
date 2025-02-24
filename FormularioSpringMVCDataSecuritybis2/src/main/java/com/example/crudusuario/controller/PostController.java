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
	
	// Declaración de los servicios necesarios para manejar las funcionalidades de Post y Sección
	private final PostService postService;
    private final SeccionService seccionService;

 // Constructor donde Spring inyecta las dependencias de PostService y SeccionService
    @Autowired
    public PostController(PostService postService, SeccionService seccionService) {
        this.postService = postService;
        this.seccionService = seccionService;
    }

    @GetMapping("/list/posts")
    public String listPosts(Model model) {
    	//obtiene la lista de posts
        List<Post> posts = postService.getPostsEsteUsuario();
        // Añade la lista de posts al modelo para pasarlo a la vista (en este caso, la vista 'user/list-posts')
        model.addAttribute("posts", posts);
        // Retorna el nombre de la vista que se debe renderizar (en este caso 'user/list-posts')
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
        // Crea un objeto vacío de tipo Post y lo agrega al modelo para vincularlo al formulario en la vista
        model.addAttribute("post", new Post());
        
        // Agrega al modelo la lista de secciones disponibles que se obtienen del servicio SeccionService
        model.addAttribute("secciones", seccionService.getAllSecciones()); 
        
        // Devuelve el nombre de la plantilla Thymeleaf 'user/create-post' que representa el formulario de creación de un post
        return "user/create-post";  // Thymeleaf template for the form
    }

    @PostMapping("/create/posts")
    public String createPost(@ModelAttribute Post post, @RequestParam Long seccionId) {
        // Llama al servicio para crear el post, pasando el objeto Post y el ID de la sección seleccionada
        postService.createPost(post, seccionId);
        
        // Redirige al usuario a la lista de posts después de guardar el nuevo post
        return "redirect:/list/posts";  // Redirigir a la lista de posts después de guardar el post
    }

    @GetMapping("/edit/posts/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        // Obtiene el post que se desea editar a través del ID proporcionado en la URL
        Post post = postService.getPostById(id);
        
        // Si no se encuentra el post con ese ID, se lanza una excepción
        if (post == null) {
            throw new IllegalArgumentException("No se encontró un post con el ID: " + id);
        }
        
        // Agrega el post que se va a editar al modelo
        model.addAttribute("post", post);
        
        // Agrega la lista de secciones al modelo para que esté disponible en el formulario de edición
        model.addAttribute("secciones", seccionService.getAllSecciones());
        
        // Devuelve el nombre de la plantilla Thymeleaf 'user/edit-post' para mostrar el formulario de edición
        return "user/edit-post";
    }

    @PostMapping("/edit/posts")
    public String updatePost(@RequestParam("id") Long id, 
                             @RequestParam("seccionId") Long seccionId, 
                             @ModelAttribute Post postEditado) {
        // Llama al servicio para actualizar el post con el ID especificado, el nuevo post editado y la sección seleccionada
        postService.editarPost(id, postEditado, seccionId);
        
        // Redirige al usuario a la lista de posts después de editar el post
        return "redirect:/list/posts";  
    }

    @PostMapping("/delete/posts/{id}")
    public String deletePost(@PathVariable Long id) {
        // Llama al servicio para eliminar el post por su ID
        postService.deletePostById(id);
        
        // Redirige al usuario a la lista de posts después de eliminar el post
        return "redirect:/list/posts"; // Redirige a la lista de posts después de borrar
    }


    
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
*/
/*
@PostMapping("/edit/posts")
public String updatePost(@RequestParam("id") Long id, 
                         @RequestParam("seccionId") Long seccionId, 
                         @ModelAttribute Post postEditado) {
    if (id == null) {
        throw new IllegalArgumentException("El ID del post no puede ser nulo.");
    }

    // Obtener la sección seleccionada
    Seccion seccion = seccionService.getSeccionById(seccionId);
    postEditado.setSeccion(seccion);

    postService.editarPost(id, postEditado);
    return "redirect:/list/posts";  
}*/
/*@PostMapping("/edit/posts")
public String updatePost(@RequestParam("id") Long id, @ModelAttribute Post postEditado) {
    if (id == null) {
        throw new IllegalArgumentException("El ID del post no puede ser nulo.");
    }
    postService.editarPost(id, postEditado);
    return "redirect:/list/posts";  
}*/