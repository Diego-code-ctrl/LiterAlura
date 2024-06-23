package com.challange.LiterAlura.repository;

import com.challange.LiterAlura.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author diegobecerril
 */
public interface LibroRepository extends JpaRepository<Libro, Long>{

    public Optional<Libro> findByTitulo(String title);

    
}
