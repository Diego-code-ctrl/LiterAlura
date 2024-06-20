package com.challange.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author diegobecerril
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(Integer id, String title, List<DatosAutor> authors, 
        List<String> languages,
        @JsonAlias("download_count") Long downloadCount){
}
