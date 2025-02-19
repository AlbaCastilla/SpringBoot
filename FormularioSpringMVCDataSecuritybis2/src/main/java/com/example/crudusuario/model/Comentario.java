package com.example.crudusuario.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    @Column(columnDefinition = "TEXT")
    private String texto;

    // Relación muchos a uno: muchas instancias de esta entidad pueden estar asociadas a un solo Post.
    @ManyToOne
    // Define la clave foránea "post_id", que no puede ser nula (cada entidad debe estar asociada a un Post).
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    public Comentario() {}

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getTexto() {
        return texto;
    }

    public Post getPost() {
        return post;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comentario [id=" + id + ", usuario=" + usuario.getUsername() + ", fecha=" + fecha +
                ", texto=" + texto + ", post=" + post.getTitulo() + "]";
    }
}