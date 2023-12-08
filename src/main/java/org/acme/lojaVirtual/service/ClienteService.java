package org.acme.lojaVirtual.service;

import com.github.javafaker.Faker;
import org.acme.lojaVirtual.exception.ResourceNotFoundException;
import org.acme.lojaVirtual.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClienteService {
    Logger logger = LoggerFactory.getLogger(Cliente.class);
    private Map<Long, Cliente> clientes = initClientes();
    private Long lastId =200L;

    private Map<Long, Cliente> initClientes() {
        Map<Long, Cliente> clientes = new HashMap<Long, Cliente>();
        for(int i = 1; i <= 200; i++){
            Faker faker = new Faker();
            String nomeCompleto = faker.name().fullName();
            String cpf = faker.number().digits(11);
            Cliente cliente = Cliente.builder().cpf(cpf).nome(nomeCompleto).id((long) i).build();
            clientes.put((long) i, cliente);
        }
        return clientes;
    }


    public List<Cliente> getAll() {
        return clientes.values().stream().toList();
    }

    public Cliente getById(Long id) {
        return clientes.get(id);
    }
    public Optional<Cliente> getByIdWithOptional(Long id) {
        Cliente cliente = clientes.get(id);
        if(cliente == null) return Optional.empty();
        return Optional.of(cliente);
    }

    public Cliente deleteById(Long id) {
        if(!clientes.containsKey(id)) throw new ResourceNotFoundException("Cliente Inexistente");
        Cliente removed = clientes.remove(id);
        return removed;
    }

    public List<Cliente> getAll(Integer size) {
        List<Cliente> list = clientes.values().stream().toList();
        return list.subList(0, size);
    }
    public List<Cliente> getAll(int start, int end){
        List<Cliente> list = clientes.values().stream().toList();
        return list.subList(start,end);
    }

    public List<Cliente> getAll(Integer size, String sort, String order) {
        if(sort.equals("")){
            return getAll(size);
        }else {
            List<Cliente> subsized = getAll(size);
            Comparator<Cliente> comparator = Comparator.comparing(Cliente::getNome);
            if (order.equals("desc")) {
                comparator = comparator.reversed();
            }
            return subsized.stream().sorted(comparator).toList();
        }
    }

    public List<Cliente> getAll(Integer size, int page, String sort, String order) {
        return null;
    }
    public Long getLastId(){
        return this.lastId;
    }
    public Long incrementId(){
        this.lastId++;
        return lastId;
    }
    public Cliente create(Cliente cliente){
        Long id = incrementId();
        cliente.setId(id);
        clientes.put(id, cliente);
        return cliente;
    }
    public long count(){
        return clientes.size();
    }
    public int getTotalDePaginas(int size, int totalRegistros){
        //int totalSize = (int) count();
        int totalSize = totalRegistros;
        double totalDePaginas =  (double)totalSize / (double)size;
        return (int) Math.ceil(totalDePaginas);
    }
    public int getTotalDePaginas(int size){
        double totalSize = (double) count();

    double totalDePaginas =  totalSize / (double)size;
        return (int) Math.ceil(totalDePaginas);
    }
    public List<Cliente> getByPageAndSize(int page, int size){
        int totalDePaginas = getTotalDePaginas(size);
        List<Cliente> all = getAll();
        long count =  count();
        int start = (page -1) * size;
        int end = page * (size);
        return all.subList(start,end);
        //pra pageSize = 50
        //0-49
        //50,99
        //100-149
    }



}
