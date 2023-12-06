package org.acme.lojaVirtual.controller;

import org.acme.lojaVirtual.exception.ResourceNotFoundException;
import org.acme.lojaVirtual.model.Cliente;
import org.acme.lojaVirtual.model.payload.ClientePayload;
import org.acme.lojaVirtual.model.payload.Info;
import org.acme.lojaVirtual.model.payload.ResponsePayload;
import org.acme.lojaVirtual.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    Logger logger = LoggerFactory.getLogger(ClienteController.class);
    @Autowired
    ClienteService clienteService;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false, defaultValue = "100") Integer size,
                                                @RequestParam(required = false, defaultValue = "") String sort,
                                                @RequestParam(required = false, defaultValue = "") String order,
                                                @RequestParam(required = false, defaultValue = "1") int start,
                                                @RequestParam(required = false, defaultValue = "10") int end,
                                                @RequestParam(required = false, defaultValue = "1") int page


                                ){
        List<Cliente> all = clienteService.getAll(start, end);
        Info info = new Info(200, 4, 1);
        ClientePayload clientePayload = new ClientePayload(all, info);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("total-size", "200");
        httpHeaders.set("total-paginas", "4");
        httpHeaders.set("current-page", "1");
        return  ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(all);


        //return clienteService.getAll(size,page,sort,order);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            Cliente cliente = clienteService.getByIdWithOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente Inexistente"));
            return ResponseEntity.ok(cliente);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        try {
            Cliente removed = clienteService.deleteById(id);
            return ResponseEntity.ok(removed);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }

    }

}
