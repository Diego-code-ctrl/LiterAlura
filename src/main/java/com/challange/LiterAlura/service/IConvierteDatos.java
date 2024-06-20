package com.challange.LiterAlura.service;

import com.challange.LiterAlura.model.DatosLibro;

/**
 *
 * @author diegobecerril
 */
public interface IConvierteDatos {
    
    <T> T obtenerDatos(String json, Class<T> clase);
}
