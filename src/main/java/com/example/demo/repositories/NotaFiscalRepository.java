package com.example.demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.NotaFiscal;


@Repository
public interface NotaFiscalRepository extends CassandraRepository<NotaFiscal, UUID> {

	List<NotaFiscal> getByNumber(Integer number);
}
