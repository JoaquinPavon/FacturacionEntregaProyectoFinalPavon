package com.example.TrabajoFinalCoder.Repositorios;

import com.example.TrabajoFinalCoder.Modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCliente extends JpaRepository<Cliente, Integer> {
}
