package com.example.TrabajoFinalCoder.Modelo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductoDTO {

    private Integer id_producto;
    private String nombre;
    private double precio;
    private int stock;

}
