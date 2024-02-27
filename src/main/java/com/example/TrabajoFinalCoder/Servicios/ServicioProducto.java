package com.example.TrabajoFinalCoder.Servicios;

import com.example.TrabajoFinalCoder.Modelo.Linea;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapCliente;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapLinea;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapProducto;
import com.example.TrabajoFinalCoder.Modelo.Producto;
import com.example.TrabajoFinalCoder.Modelo.dto.ClienteDTO;
import com.example.TrabajoFinalCoder.Modelo.dto.LineaDTO;
import com.example.TrabajoFinalCoder.Modelo.dto.ProductoDTO;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioProducto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioProducto {


    @Autowired
    RepositorioProducto repositorioProducto;



    //GET ALL
    public ResponseEntity<?> getProductos(){
    Map<String,Object> mensaje = new HashMap<>();
    List<Producto> productos = repositorioProducto.findAll();
    if(productos.isEmpty()){
        mensaje.put("success",Boolean.FALSE);
        mensaje.put("mensaje",String.format("La lista esta vacia"));
        return ResponseEntity.badRequest().body(mensaje);
    }
    List<ProductoDTO> productoDTOS = productos.stream()
                .map(MapProducto::mapperProducto).collect(Collectors.toList());
    mensaje.put("success",Boolean.TRUE);
    mensaje.put("data",productoDTOS);
    return ResponseEntity.ok(mensaje);

    }

    //GET ID
    public ResponseEntity<?> getProductoById(Integer productoId) {
        Map<String, Object>mensaje = new HashMap<>();
        Optional<Producto> optionalProducto = repositorioProducto.findById(productoId);
        if(!optionalProducto.isPresent()){
            mensaje.put("sucess",Boolean.FALSE);
            mensaje.put("mensaje",String.format("La linea con la id %d no existe",productoId));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Producto producto = optionalProducto.get();
        ProductoDTO productoDTO = MapProducto.mapperProducto(producto);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",productoDTO);
        return ResponseEntity.ok(mensaje);

    }

    //POST
    public ResponseEntity<?> saveProducto(Producto producto) {
       Map<String,Object> mensaje = new HashMap<>();
        repositorioProducto.save(producto);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }


    //PUT
    public ResponseEntity<?> updateProducto(Integer id, Producto producto) {
       Map<String,Object> mensaje = new HashMap<>();
        Optional<Producto> optionalProducto = repositorioProducto.findById(id);
        if (optionalProducto.isPresent()) {
            Producto update = optionalProducto.get();
            update.setNombre(producto.getNombre());
            update.setStock(producto.getStock());
            update.setPrecio(producto.getPrecio());
            repositorioProducto.save(update);
            mensaje.put("success",Boolean.TRUE);
            return ResponseEntity.ok(mensaje);
        } else {
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("El producto con la id %d no existe",id));
            return  ResponseEntity.badRequest().body(mensaje);

        }
    }

    //DELETE ID
    public ResponseEntity<?> deleteProductoById(Integer productoId) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Producto> optionalProducto = repositorioProducto.findById(productoId);
        if (optionalProducto.isPresent()) {
            repositorioProducto.deleteById(productoId);
            mensaje.put("success",Boolean.TRUE);
            return ResponseEntity.ok(mensaje);
        } else {
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("El producto con la id %d no existe",productoId));
            return  ResponseEntity.badRequest().body(mensaje);
        }
    }

    //DELETE ALL
    public ResponseEntity<?> deleteAllProductos() {
        Map<String,Object> mensaje = new HashMap<>();
        repositorioProducto.deleteAll();
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }
}
