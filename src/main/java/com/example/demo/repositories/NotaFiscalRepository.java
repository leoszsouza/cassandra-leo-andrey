package com.example.demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import br.com.univali.notaFiscal.model.NotaFiscal;

@Repository
public interface NotaFiscalRepository extends CassandraRepository<NotaFiscal, UUID> {

	List<NotaFiscal> getByNumber(Integer number);
}
