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
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String subtitulo;

    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    // Relación muchos a uno: muchas instancias de esta entidad pueden estar asociadas a un solo User.
    @ManyToOne
    // Define la clave foránea "usuario_id", que no puede ser nula (cada entidad debe estar asociada a un User).
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    @Column(columnDefinition = "TEXT")
    private String texto;

    public Post() {}

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", titulo=" + titulo + ", subtitulo=" + subtitulo +
                ", fecha=" + fecha + ", seccion=" + (seccion != null ? seccion.getNombre() : "null") +
                ", usuario=" + usuario.getUsername() + ", texto=" + texto + "]";
    }
}