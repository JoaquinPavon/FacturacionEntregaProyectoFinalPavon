package com.example.TrabajoFinalCoder.Repositorios;

import com.example.TrabajoFinalCoder.Modelo.Linea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLinea extends JpaRepository<Linea,Integer> {

}
