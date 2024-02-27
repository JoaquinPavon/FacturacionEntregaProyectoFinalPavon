package com.example.TrabajoFinalCoder.Modelo.dto;

import com.example.TrabajoFinalCoder.Modelo.Producto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class LineaDTO {

    private Integer lineaId;
    private Integer cantidad;
    private String descripcion;
    private double precio;
    private ProductoDTO producto;




}
