package com.example.TrabajoFinalCoder.Modelo;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter

public class Factura {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Integer id_factura;
    @Column
    private LocalDate fecha;
    @OneToMany(mappedBy="factura", fetch=FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Linea> lineas;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "id_cliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;



    public void addLinea(Linea linea){
        this.lineas.add(linea);
        linea.setFactura(this);
    }


    public Factura() {
    }
}
