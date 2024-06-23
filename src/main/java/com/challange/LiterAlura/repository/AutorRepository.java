package com.challange.LiterAlura.repository;

import com.challange.LiterAlura.entity.Autor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author diegobecerril
 */
public interface AutorRepository extends JpaRepository<Autor, Long> {

    public Optional<Autor> findByNombre(String nombre);

    @Query("""
           SELECT a.nombre
           FROM Autor a, Libro b
           WHERE b.titulo = :title AND b.id = a.libro.id
           """)
    public List<String> findAutorLibro(@Param("title") String title);

}
