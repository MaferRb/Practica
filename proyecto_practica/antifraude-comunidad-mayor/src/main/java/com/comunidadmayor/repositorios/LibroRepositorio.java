package com.comunidadmayor.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comunidadmayor.modelo.Libro;

public interface LibroRepositorio extends JpaRepository<Libro, Integer> {

}
