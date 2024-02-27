package com.example.TrabajoFinalCoder.Modelo.Mapper;

import com.example.TrabajoFinalCoder.Modelo.Linea;
import com.example.TrabajoFinalCoder.Modelo.dto.LineaDTO;

public class MapLinea {

public static LineaDTO mapperLinea(Linea linea){
LineaDTO dto = new LineaDTO();
dto.setLineaId(linea.getLineaId());
dto.setCantidad(linea.getCantidad());
dto.setPrecio(linea.getPrecio());
dto.setDescripcion(linea.getDescripcion());
dto.setProducto(MapProducto.mapperProducto(linea.getProducto()));
return dto;

}


/*  public static LineaDTO mapLineaToDTO(Linea linea){
        LineaDTO dto = new LineaDTO();
        dto.setLineaId(linea.getLineaId());
        dto.setCantidad(linea.getCantidad());
        dto.setDescripcion(linea.getDescripcion());
        dto.setPrecio(linea.getPrecio());
        dto.setProducto(MapProducto.mapProductoToDTO(linea.getProducto()));
        return dto;
    }*/

}
