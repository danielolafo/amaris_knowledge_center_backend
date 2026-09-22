package com.amaris.knowledgecenter.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Datos para registrar una implementación de software (ej. autenticación JWT).
 *
 * @param title              Título descriptivo de la implementación.
 * @param description        Descripción breve de la funcionalidad implementada.
 * @param employeeIds        Empleados que participaron en la implementación.
 * @param tags               Etiquetas o palabras clave para buscar la implementación.
 * @param technologies       Tecnologías y versiones usadas en la implementación.
 */
public record ImplementationRequest(
        @NotBlank(message = "El título es obligatorio") String title,
        String description,
        @NotEmpty(message = "Debe indicarse al menos un empleado")
        List<Long> employeeIds,
        @NotEmpty(message = "Debe indicarse al menos una etiqueta")
        List<String> tags,
        @NotEmpty(message = "Debe indicarse al menos una tecnología usada")
        @Valid
        List<TechnologyVersionUsage> technologies) {

    /**
     * Tecnología y versión concreta usadas en una implementación.
     *
     * @param technologyId Identificador de la tecnología.
     * @param versionId    Identificador de la versión de esa tecnología.
     */
    public record TechnologyVersionUsage(Long technologyId, Long versionId) {
    }
}