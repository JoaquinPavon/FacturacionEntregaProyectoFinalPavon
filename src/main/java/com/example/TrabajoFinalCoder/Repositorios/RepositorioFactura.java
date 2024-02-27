package com.example.TrabajoFinalCoder.Repositorios;

import com.example.TrabajoFinalCoder.Modelo.Factura;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RepositorioFactura extends JpaRepository<Factura, Integer> {
}
