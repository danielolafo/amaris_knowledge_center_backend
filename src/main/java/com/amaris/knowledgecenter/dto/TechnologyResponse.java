package com.amaris.knowledgecenter.dto;

import java.util.List;

/**
 * Respuesta de una tecnología con sus versiones disponibles.
 *
 * @param id       Identificador de la tecnología.
 * @param name     Nombre de la tecnología.
 * @param versions Versiones disponibles de la tecnología.
 */
public record TechnologyResponse(
        Long id,
        String name,
        List<VersionRef> versions) {

    /**
     * Referencia de una versión concreta.
     *
     * @param id      Identificador de la versión.
     * @param version Etiqueta de la versión (ej. 3.5).
     */
    public record VersionRef(Long id, String version) {
    }
}