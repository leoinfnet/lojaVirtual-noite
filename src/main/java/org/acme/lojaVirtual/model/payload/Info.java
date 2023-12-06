package org.acme.lojaVirtual.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class Info {
    private int totalRegistro;
    private int totalPaginas;
    private int paginaCorrente;
}
