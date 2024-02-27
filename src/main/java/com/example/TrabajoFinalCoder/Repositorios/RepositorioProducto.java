package com.example.TrabajoFinalCoder.Repositorios;

import com.example.TrabajoFinalCoder.Modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProducto extends JpaRepository<Producto, Integer> {
}
