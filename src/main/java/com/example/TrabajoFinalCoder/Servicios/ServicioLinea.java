package com.example.TrabajoFinalCoder.Servicios;


import com.example.TrabajoFinalCoder.Modelo.Cliente;
import com.example.TrabajoFinalCoder.Modelo.Linea;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapLinea;
import com.example.TrabajoFinalCoder.Modelo.Producto;
import com.example.TrabajoFinalCoder.Modelo.dto.LineaDTO;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioLinea;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioProducto;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioLinea {



    @Autowired
    RepositorioLinea repositorioLinea;
    @Autowired
    RepositorioProducto repositorioProducto;



    public ResponseEntity<?> getAll(){
        Map<String,Object> mensaje = new HashMap<>();
        List<Linea> lineas = repositorioLinea.findAll();
        if(lineas.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("La lista esta vacia "));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<LineaDTO> lineasDTO = lineas.stream()
                .map(MapLinea::mapperLinea).collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",lineasDTO);
        return ResponseEntity.ok(mensaje);

    }


    public ResponseEntity<?> getLineaid(Integer id){
        Map<String, Object>mensaje = new HashMap<>();
        Optional<Linea>optionalLinea = repositorioLinea.findById(id);
        if (!optionalLinea.isPresent()){
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la linea con la id %d no existe",id));
            return  ResponseEntity.badRequest().body(mensaje);
        }
        Linea linea = optionalLinea.get();
        LineaDTO lineaDTO = MapLinea.mapperLinea(linea);

        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",lineaDTO);
        return (ResponseEntity<?>) ResponseEntity.ok(mensaje);
    }


    public ResponseEntity<?> deleteById(Integer id) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Linea>opLinea = repositorioLinea.findById(id);
        if (!opLinea.isPresent()){
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la linea con la id %d no existe",id));
            return  ResponseEntity.badRequest().body(mensaje);
        }
        repositorioLinea.deleteById(id);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);

    }

    public ResponseEntity<?> modificarLinea(Integer id, Linea linea){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Linea> oLinea = repositorioLinea.findById(id);
        if (oLinea != null) {
            Linea optionalLinea = oLinea.get();
            optionalLinea.setCantidad(linea.getCantidad());
            optionalLinea.setDescripcion(linea.getDescripcion());
            repositorioLinea.save(optionalLinea);
            mensaje.put("success",Boolean.TRUE);

            return ResponseEntity.ok(mensaje);
        }
        mensaje.put("succes",Boolean.FALSE);
        mensaje.put("mensaje",String.format("la linea con la id %d no existe",id));
        return  ResponseEntity.badRequest().body(mensaje);

    }


    public ResponseEntity<?> agregarLinea(Linea linea){
        Map<String,Object> mensaje = new HashMap<>();

            //if(linea.getProducto().getStock()>=linea.getCantidad()){
                repositorioLinea.save(linea);
                mensaje.put("success",Boolean.TRUE);
                return ResponseEntity.ok(mensaje);

        //}

        //mensaje.put("succes",Boolean.FALSE);
        //mensaje.put("mensaje",String.format("No existe el producto"));
        //return  ResponseEntity.badRequest().body(mensaje);
    }






    public ResponseEntity<?> getPrecioLinea(Integer id){
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Linea> oLinea = repositorioLinea.findById(id);
        if (oLinea != null) {
            Linea optionalLinea = oLinea.get();
           double precio = optionalLinea.getPrecio();
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("precio",precio);
            return ResponseEntity.ok(mensaje);
        }
        mensaje.put("succes",Boolean.FALSE);
        mensaje.put("mensaje",String.format("la linea con la id %d no existe",id));
        return  ResponseEntity.badRequest().body(mensaje);


    }


}