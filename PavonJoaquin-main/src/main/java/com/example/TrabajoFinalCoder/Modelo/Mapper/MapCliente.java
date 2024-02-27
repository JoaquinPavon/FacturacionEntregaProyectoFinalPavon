package com.example.TrabajoFinalCoder.Modelo.Mapper;

import com.example.TrabajoFinalCoder.Modelo.Cliente;
import com.example.TrabajoFinalCoder.Modelo.dto.ClienteDTO;

public class MapCliente {

    public static ClienteDTO mapperCliente(Cliente cliente){
        ClienteDTO dto = new ClienteDTO();

        dto.setId_cliente(cliente.getId_cliente());
        dto.setDni(cliente.getDni());
        dto.setApellido(cliente.getApellido());
        dto.setNombre(cliente.getNombre());

        return dto;
    }
}
