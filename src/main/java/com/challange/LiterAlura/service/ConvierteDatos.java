package com.challange.LiterAlura.service;

import com.challange.LiterAlura.model.DatosLibro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diegobecerril
 */
public class ConvierteDatos implements IConvierteDatos{
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConvierteDatos.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("No se pudieron convertir los datos");
        }
    }
    
}
