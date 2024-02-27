package com.example.TrabajoFinalCoder.Modelo.dto;


import com.example.TrabajoFinalCoder.Modelo.Cliente;
import com.example.TrabajoFinalCoder.Modelo.Linea;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FacturaDTO {

private Integer id_factura;
private LocalDate fecha;
private Set<LineaDTO> lineas;
private ClienteDTO cliente;
}
