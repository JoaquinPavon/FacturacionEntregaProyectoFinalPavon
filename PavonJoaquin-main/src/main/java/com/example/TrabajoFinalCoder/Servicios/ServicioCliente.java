package com.example.TrabajoFinalCoder.Servicios;

import com.example.TrabajoFinalCoder.Modelo.Cliente;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapCliente;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapLinea;
import com.example.TrabajoFinalCoder.Modelo.dto.ClienteDTO;
import com.example.TrabajoFinalCoder.Modelo.dto.LineaDTO;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioCliente {

    @Autowired
    RepositorioCliente repositorioCliente;

    // GET ALL
    public ResponseEntity<?> getClientes(){
        Map<String,Object> mensaje = new HashMap<>();
        List<Cliente> clientes = repositorioCliente.findAll();
        if(clientes.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("La lista esta vacia"));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(MapCliente::mapperCliente).collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",clienteDTOS);
        return ResponseEntity.ok(mensaje);
    }


    // GET POR ID
    public ResponseEntity<?> getClienteById(Integer clienteId) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Cliente> optionalCliente = repositorioCliente.findById(clienteId);
        if(!optionalCliente.isPresent()){
            mensaje.put("sucess",Boolean.FALSE);
            mensaje.put("mensaje",String.format("El cliente con la id %d no existe",clienteId));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Cliente cliente = optionalCliente.get();
        ClienteDTO clienteDTO = MapCliente.mapperCliente(cliente);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",clienteDTO);
        return ResponseEntity.ok(mensaje);

    }

    // POST
    public ResponseEntity<?> saveCliente(Cliente cliente) {
        Map<String,Object> mensaje = new HashMap<>();
        repositorioCliente.save(cliente);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }

    // PUT
    public ResponseEntity<?> updateCliente(Integer id, Cliente cliente) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Cliente> optionalCliente = repositorioCliente.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente update = optionalCliente.get();
            update.setApellido(cliente.getApellido());
            update.setNombre(cliente.getNombre());
            update.setDni(cliente.getDni());
            repositorioCliente.save(update);
            mensaje.put("success",Boolean.TRUE);
            return ResponseEntity.ok(mensaje);
        } else {
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("El cliente con la id %d no existe",id));
            return  ResponseEntity.badRequest().body(mensaje);        }
    }


    // DELETE ID
    public ResponseEntity<?> deleteClienteById(Integer clienteId) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Cliente> optionalCliente = repositorioCliente.findById(clienteId);
        if (optionalCliente.isPresent()) {
            repositorioCliente.deleteById(clienteId);
            mensaje.put("success",Boolean.TRUE);
            return ResponseEntity.ok(mensaje);
        } else {
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("El cliente con la id %d no existe",clienteId));
            return  ResponseEntity.badRequest().body(mensaje);
        }
    }

    // DELETE ALL
    public ResponseEntity<?> deleteAllClientes() {
        Map<String,Object> mensaje = new HashMap<>();
        repositorioCliente.deleteAll();
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }


}
