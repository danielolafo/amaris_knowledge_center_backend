package com.amaris.knowledgecenter.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Respuesta completa de una implementación de software.
 *
 * @param id           Identificador de la implementación.
 * @param title        Título de la implementación.
 * @param description  Descripción de la implementación.
 * @param createdAt    Fecha y hora de registro.
 * @param employees    Empleados que la implementaron.
 * @param tags         Etiquetas asociadas para la búsqueda.
 * @param technologies Tecnologías y versiones usadas.
 */
public record ImplementationResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        List<EmployeeRef> employees,
        List<String> tags,
        List<TechnologyVersionUsed> technologies) {

    /**
     * Referencia breve de un empleado.
     *
     * @param id   Identificador del empleado.
     * @param name Nombre del empleado.
     */
    public record EmployeeRef(Long id, String name) {
    }

    /**
     * Tecnología y versión concretas usadas en la implementación.
     *
     * @param technologyId   Identificador de la tecnología.
     * @param technologyName Nombre de la tecnología.
     * @param versionId      Identificador de la versión.
     * @param version        Etiqueta de la versión.
     */
    public record TechnologyVersionUsed(
            Long technologyId,
            String technologyName,
            Long versionId,
            String version) {
    }
}