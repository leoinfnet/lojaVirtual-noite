package org.acme.lojaVirtual;

import org.acme.lojaVirtual.model.Cliente;
import org.acme.lojaVirtual.service.ClienteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ClienteServiceTests {
    Logger logger = LoggerFactory.getLogger(ClienteServiceTests.class);
    @Autowired
    ClienteService clienteService;
    @Test
    public void testaGetAll(){
        List<Cliente> clientes = clienteService.getAll();
        assertEquals(200, clientes.size());
    }
    @Test
    public void testaGetById(){
        Long id = 1L;
        Cliente cliente = clienteService.getById(id);
        logger.info(cliente.toString());
        assertNotNull(cliente);
        assertEquals(1L, cliente.getId());

        Cliente clienteInexiste = clienteService.getById(-100L);
        Optional<Cliente> optional = clienteService.getByIdWithOptional(-100L);
        assertTrue(optional.isEmpty());
        Optional<Cliente> optionalExistente = clienteService.getByIdWithOptional(1L);
        assertTrue(optionalExistente.isPresent());
        Cliente clienteExistente = optionalExistente.get();
        assertEquals(1L,clienteExistente.getId() );

    }
    @Test
    @DisplayName("Deve testar o total de paginas")
    public void quantidadeDePaginas(){
        int totalDePaginas = clienteService.getTotalDePaginas(4, 20);
        assertEquals(5,totalDePaginas);
        totalDePaginas = clienteService.getTotalDePaginas(4, 21);
        assertEquals(6,totalDePaginas);
    }
}
