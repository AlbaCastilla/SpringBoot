package com.example.crudusuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//La anotación @SpringBootApplication marca esta clase como la clase principal de la aplicación.
@SpringBootApplication
public class FormularioSpringMvcDataSecurityApplication {

	// Se llama a SpringApplication.run() para iniciar la aplicación Spring Boot.
    // Esta línea arranca la aplicación, carga los componentes de Spring y pone en marcha el servidor web dentro de la aplicacion
	public static void main(String[] args) {
		SpringApplication.run(FormularioSpringMvcDataSecurityApplication.class, args);
	}

}
