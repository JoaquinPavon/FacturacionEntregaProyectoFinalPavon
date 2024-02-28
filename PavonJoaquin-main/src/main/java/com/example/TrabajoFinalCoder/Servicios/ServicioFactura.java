package com.example.TrabajoFinalCoder.Servicios;

import com.example.TrabajoFinalCoder.Modelo.Cliente;
import com.example.TrabajoFinalCoder.Modelo.Factura;
import com.example.TrabajoFinalCoder.Modelo.Linea;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapFactura;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapLinea;
import com.example.TrabajoFinalCoder.Modelo.Producto;
import com.example.TrabajoFinalCoder.Modelo.dto.FacturaDTO;
import com.example.TrabajoFinalCoder.Modelo.dto.LineaDTO;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioCliente;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioFactura;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioLinea;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Service
public class ServicioFactura {
    @Autowired
    RepositorioFactura repositorioFactura;

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioLinea repositorioLinea;

    @Autowired
    RepositorioCliente repositorioCliente;

    private static final HttpClient httpClient = HttpClient.newBuilder().build();


    // GET ALL
    public ResponseEntity<?> getAll() {
        Map<String,Object> mensaje = new HashMap<>();
        List<Factura> facturas = repositorioFactura.findAll();
        if(facturas.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("La lista esta vacia "));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<FacturaDTO> facturasDTO = facturas.stream()
                .map(MapFactura::mapperFactura).collect(Collectors.toList());
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("Facturas:",facturasDTO);
        return ResponseEntity.ok(mensaje);
    }

    // GET ID
    public ResponseEntity<?> getFacturaById(Integer facturaID) {
        Map<String, Object>mensaje = new HashMap<>();
        Optional<Factura>optionalFactura = repositorioFactura.findById(facturaID);
        if (!optionalFactura.isPresent()){
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
            return  ResponseEntity.badRequest().body(mensaje);
        }
        Factura factura = optionalFactura.get();
        FacturaDTO FacturaDTO = MapFactura.mapperFactura(factura);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("Factura",FacturaDTO);
        return  ResponseEntity.ok(mensaje);
    }


    // POST
    public ResponseEntity<?> guardarFactura(Factura factura) {
        Map<String, Object> mensaje = new HashMap<>();
        Factura facturaAGuardar = new Factura();

        try {
            int idCliente = factura.getCliente().getId_cliente();
            Optional<Cliente> cliente = repositorioCliente.findById(idCliente);

            if (cliente.isEmpty()) {
                mensaje.put("success", Boolean.FALSE);
                mensaje.put("mensaje", "No existe el cliente");
                return ResponseEntity.badRequest().body(mensaje);
            }

            facturaAGuardar.setCliente(cliente.get());
            facturaAGuardar.setFecha(this.getCurrentDate());
            Set<Linea> linesParaFactura = new HashSet<>();
            facturaAGuardar.setId_factura(factura.getId_factura());

            for (Linea linea : factura.getLineas()) {
                int productoid = linea.getProducto().getId_producto();
                Optional<Producto> optionalProducto = repositorioProducto.findById(productoid);

                if (optionalProducto.isEmpty()) {
                    mensaje.put("success", Boolean.FALSE);
                    mensaje.put("mensaje", String.format("El producto con ID %d no existe", productoid));
                    return ResponseEntity.badRequest().body(mensaje);
                }

                Producto producto = optionalProducto.get();
                int cantidadSolicitada = linea.getCantidad();
                int stockDisponible = producto.getStock();

                if (cantidadSolicitada <= stockDisponible) {
                    Linea lineAux = new Linea();
                    lineAux.setFactura(facturaAGuardar);
                    lineAux.setProducto(producto);
                    lineAux.setCantidad(cantidadSolicitada);
                    lineAux.setDescripcion(linea.getDescripcion());
                    linesParaFactura.add(lineAux);

                    // Actualizar el stock del producto
                    int nuevoStock = stockDisponible - cantidadSolicitada;
                    producto.setStock(nuevoStock);
                    repositorioProducto.save(producto);
                } else {
                    mensaje.put("success", Boolean.FALSE);
                    mensaje.put("mensaje", String.format("No hay suficiente stock para el producto '%s'", producto.getNombre()));
                    return ResponseEntity.badRequest().body(mensaje);
                }
            }
            facturaAGuardar.setLineas(linesParaFactura);
            repositorioFactura.save(facturaAGuardar);

            mensaje.put("success", Boolean.TRUE);
            mensaje.put("mensaje", "Factura correctamente agregada");
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", "No se pudo guardar la factura");
            return ResponseEntity.badRequest().body(mensaje);
        }
    }


    // PUT -Linea a la Factura.
    public ResponseEntity<?> addLineaToFactura(Integer factura_id, Linea linea) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Factura> optionalFactura = repositorioFactura.findById(factura_id);
        if (optionalFactura.isPresent()) {
            Factura factura = optionalFactura.get();
            factura.addLinea(linea);
            repositorioFactura.save(factura);
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("data",String.format("Se actualizo correctamente"));
            return ResponseEntity.ok(mensaje);
        } else {
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la factura con la id %d no existe",factura_id));
            return  ResponseEntity.badRequest().body(mensaje);

        }
    }


    // GET PRECIO DE Factura
    public ResponseEntity<?> getTotalPrecioID(Integer facturaID) {
        Map<String,Object> mensaje = new HashMap<>();
        double resultado = 0;
        Optional<Factura> optionalFactura = repositorioFactura.findById(facturaID);
        if(optionalFactura.isPresent()){
            Factura factura = optionalFactura.get();
            for(Linea hijo: factura.getLineas()){
                resultado+=hijo.getPrecio();
            }
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("El precio es:",resultado);
            return ResponseEntity.ok(mensaje);

        }
        mensaje.put("succes",Boolean.FALSE);
        mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
        return  ResponseEntity.badRequest().body(mensaje);

    }

    // DELETE
    public ResponseEntity<?> deleteFacturaByID(Integer facturaID) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Factura>optionalFactura = repositorioFactura.findById(facturaID);
        if (!optionalFactura.isPresent()){
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
            return  ResponseEntity.badRequest().body(mensaje);
        }
        repositorioFactura.deleteById(facturaID);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);

    }

    // GET TODOS LOS PRODUCTOS/LINEA DE UNA FACTURA
    public ResponseEntity<?> getProductosById(Integer facturaID) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Factura> optionalFactura = repositorioFactura.findById(facturaID);
        if(optionalFactura.isPresent()){
            Factura factura = optionalFactura.get();
            Set<Linea> lineas = factura.getLineas();
            List<LineaDTO> lineasDTO = lineas.stream()
                    .map(MapLinea::mapperLinea).collect(Collectors.toList());
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("data",lineasDTO);
            return ResponseEntity.ok(mensaje);
        }
        mensaje.put("succes",Boolean.FALSE);
        mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
        return  ResponseEntity.badRequest().body(mensaje);
    }


    // DELETE ALL
    public ResponseEntity<?> deleteAllFacturas() {
        Map<String,Object> mensaje = new HashMap<>();
        repositorioFactura.deleteAll();
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }

    private static LocalDate getCurrentDate() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://worldclockapi.com/api/json/utc/now"))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) { // Verificar si la respuesta es exitosa
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                String currentDateString = jsonNode.get("currentDateTime").asText();
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(currentDateString);
                return zonedDateTime.toLocalDate();
            } else {
                System.err.println("Error al obtener la fecha del servidor: " + response.statusCode());
                return LocalDate.now();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LocalDate.now();
        }
    }
}
