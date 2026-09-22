package com.amaris.knowledgecenter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Datos para crear o actualizar un empleado.
 *
 * @param name           Nombre completo del empleado.
 * @param email          Correo corporativo del empleado.
 * @param position       Cargo del empleado (ej. Backend Developer).
 * @param skills         Lista de habilidades del empleado.
 * @param technologyIds  Ids de las tecnologías que el empleado conoce.
 */
public record EmployeeRequest(
        @NotBlank(message = "El nombre es obligatorio") String name,
        @Email(message = "El correo no es válido") String email,
        String position,
        List<String> skills,
        List<Long> technologyIds) {
}