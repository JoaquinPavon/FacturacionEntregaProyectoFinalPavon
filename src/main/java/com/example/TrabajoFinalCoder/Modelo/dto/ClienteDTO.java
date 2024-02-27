package com.example.TrabajoFinalCoder.Modelo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
    private String nombre;
    private String apellido;
    private String dni;
    private Integer id_cliente;
}
