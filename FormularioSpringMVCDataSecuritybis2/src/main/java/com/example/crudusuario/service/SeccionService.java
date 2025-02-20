package com.example.crudusuario.service;

import com.example.crudusuario.model.Seccion;
import com.example.crudusuario.repository.SeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeccionService {

    private final SeccionRepository seccionRepository;

    @Autowired
    public SeccionService(SeccionRepository seccionRepository) {
        this.seccionRepository = seccionRepository;
    }

    // Fetch all sections
    public List<Seccion> getAllSecciones() {
        return seccionRepository.findAll();
    }
    
 // Fetch a section by ID (using the inherited findById method)
    public Seccion getSeccionById(Long id) {
        Optional<Seccion> seccionOptional = seccionRepository.findById(id);
        return seccionOptional.orElseThrow(() -> new RuntimeException("Sección no encontrada"));
    }
    
    
    public Long getSeccionIdByName(String nombre) {
        Optional<Seccion> seccionOptional = seccionRepository.findByNombre(nombre);
        return seccionOptional.map(Seccion::getId)
                              .orElseThrow(() -> new RuntimeException("Sección con nombre '" + nombre + "' no encontrada"));
    }

    // Optionally, you can add methods for specific queries (e.g., by ID or name)
}
