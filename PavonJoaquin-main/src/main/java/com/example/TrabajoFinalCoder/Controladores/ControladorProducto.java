package com.example.TrabajoFinalCoder.Controladores;

import com.example.TrabajoFinalCoder.Modelo.Producto;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioProducto;
import com.example.TrabajoFinalCoder.Servicios.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {


@Autowired
ServicioProducto servicioProducto;


    //GET ALL
    @GetMapping()
    public ResponseEntity<?> getProductos(){
        return servicioProducto.getProductos();}

    //GET ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Integer id) {
        return servicioProducto.getProductoById(id);
    }

    //POST
    @PostMapping("/alta")
    public ResponseEntity<?> saveProducto(@RequestBody Producto producto) {
        return servicioProducto.saveProducto(producto);
    }
    //PUT
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        return servicioProducto.updateProducto(id, producto);
    }
    //DELETE ID
    @DeleteMapping("/baja/{id}")
    public ResponseEntity<?> deleteProductoById(@PathVariable Integer id) {
        return servicioProducto.deleteProductoById(id);}

    //DELETE ALL
    @DeleteMapping("/borrarTodos")
    public ResponseEntity<?> deleteAllProductos() {
        return servicioProducto.deleteAllProductos();
    }
}
