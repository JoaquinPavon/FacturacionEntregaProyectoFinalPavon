package com.example.TrabajoFinalCoder.Modelo.Mapper;

import com.example.TrabajoFinalCoder.Modelo.Factura;
import com.example.TrabajoFinalCoder.Modelo.dto.FacturaDTO;

import java.util.stream.Collectors;

public class MapFactura {


    public static FacturaDTO mapperFactura(Factura factura){
        FacturaDTO dto = new FacturaDTO();
        dto.setId_factura(factura.getId_factura());
        dto.setCliente(MapCliente.mapperCliente(factura.getCliente()));
        dto.setFecha(factura.getFecha());
        dto.setLineas(factura.getLineas().stream().map(MapLinea::mapperLinea).collect(Collectors.toSet()));
        return dto;
    }

}
