package com.challange.LiterAlura.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Libros")
public class Libro{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String titulo;
    
    @OneToMany(mappedBy = "libro")
    private List<Autor> autores;
    
    @OneToMany(mappedBy = "libro")
    private List<Lenguaje> lenguaje;
    
    private Long downloadCount;

    public Libro() {
    }

    public Libro(String titulo, Long downloadCount) {
        this.titulo = titulo;
        this.downloadCount = downloadCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Lenguaje> getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(List<Lenguaje> lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }
    
    
}