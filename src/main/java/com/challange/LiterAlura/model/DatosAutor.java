package com.challange.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 *
 * @author diegobecerril
 */
public record DatosAutor(String name, @JsonAlias("birth_year") Integer birthYear, @JsonAlias("death_year") Integer deathYear) {
}
