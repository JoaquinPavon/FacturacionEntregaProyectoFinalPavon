package com.example.TrabajoFinalCoder.Modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Data

public class Linea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineaId;
    private String descripcion;
    private Integer cantidad;
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;


    public double getPrecio() {
        if (producto != null && cantidad != null) {
            return producto.getPrecio() * cantidad;
        }
        return 0.0;
    }


    public Linea() {
    }

    public Integer getId_producto() {
     return   this.producto.getId_producto();
    }
}