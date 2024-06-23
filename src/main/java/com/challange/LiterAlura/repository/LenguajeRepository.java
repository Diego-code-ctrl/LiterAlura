package com.challange.LiterAlura.repository;

import com.challange.LiterAlura.entity.Lenguaje;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author diegobecerril
 */
public interface LenguajeRepository extends JpaRepository<Lenguaje, Object>{

    public Optional<Lenguaje> findByLenguaje(String language);
    
    
    @Query("""
           SELECT l.lenguaje
           FROM Lenguaje l, Libro b
           WHERE b.titulo = :title AND b.id = l.libro.id
           """)
    public List<String> findLenguajeLibro(@Param("title") String title);
    
    @Query("""
           SELECT l.lenguaje
           FROM Lenguaje l, Libro b
           WHERE b.titulo = :title AND b.id = l.libro.id
           """)
    public String unicoLenguaje(@Param("title") String title);
    
}
