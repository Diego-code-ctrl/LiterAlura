package com.challange.LiterAlura.repository;

import com.challange.LiterAlura.entity.Autor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author diegobecerril
 */
public interface AutorRepository extends JpaRepository<Autor, Long>{

    public Optional<Autor> findByName(String name);
    
}
