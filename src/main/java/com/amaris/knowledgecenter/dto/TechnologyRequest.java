package com.amaris.knowledgecenter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Datos para crear una tecnología con sus versiones.
 *
 * @param name     Nombre de la tecnología (ej. Spring Boot).
 * @param versions Lista de versiones disponibles (ej. 3.3, 3.5).
 */
public record TechnologyRequest(
        @NotBlank(message = "El nombre de la tecnología es obligatorio") String name,
        @NotEmpty(message = "Debe indicarse al menos una versión") List<String> versions) {
}