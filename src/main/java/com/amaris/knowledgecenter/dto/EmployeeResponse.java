package com.amaris.knowledgecenter.dto;

import java.util.List;

/**
 * Respuesta de un empleado con sus habilidades y tecnologías conocidas.
 *
 * @param id           Identificador del empleado.
 * @param name         Nombre completo del empleado.
 * @param email        Correo corporativo.
 * @param position     Cargo del empleado.
 * @param skills       Habilidades del empleado.
 * @param technologies Tecnologías conocidas por el empleado.
 */
public record EmployeeResponse(
        Long id,
        String name,
        String email,
        String position,
        List<String> skills,
        List<TechnologyRef> technologies) {

    /**
     * Referencia breve de una tecnología.
     *
     * @param id   Identificador de la tecnología.
     * @param name Nombre de la tecnología.
     */
    public record TechnologyRef(Long id, String name) {
    }
}