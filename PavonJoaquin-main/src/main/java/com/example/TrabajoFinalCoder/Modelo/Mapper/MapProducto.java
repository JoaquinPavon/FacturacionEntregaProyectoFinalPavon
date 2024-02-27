package com.example.TrabajoFinalCoder.Modelo.Mapper;

import com.example.TrabajoFinalCoder.Modelo.Producto;
import com.example.TrabajoFinalCoder.Modelo.dto.ProductoDTO;

public class MapProducto {

    public static ProductoDTO mapperProducto(Producto producto){
        ProductoDTO dto = new ProductoDTO();
        dto.setId_producto(producto.getId_producto());
        dto.setPrecio(producto.getPrecio());
        dto.setNombre(producto.getNombre());
        dto.setStock(producto.getStock());
        return dto;
    }
}
