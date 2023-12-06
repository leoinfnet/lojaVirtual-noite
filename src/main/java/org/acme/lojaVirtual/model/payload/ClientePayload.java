package org.acme.lojaVirtual.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.acme.lojaVirtual.model.Cliente;

import java.util.List;

@Data@AllArgsConstructor
public class ClientePayload {
    List<Cliente> clientes;
    Info infos;
}
