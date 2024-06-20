package com.challange.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author diegobecerril
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosApi(Integer count, String next, String previous, List<DatosLibro> results) {

}
