package com.example.demo.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;





@Service
public class NotaFiscalService {

//	@Autowired
//	private NotaFiscalRepository notaFiscalRepository;
//
//	public String getNotasFiscaisMysql() {
//		DriverManagerDataSource ds = new DriverManagerDataSource();
//		ds.setDriverClassName("com.mysql.jdbc.Driver");
//		ds.setUrl("jdbc:mysql://localhost:3306/invoice_system_univali");
//		ds.setUsername("root");
//		ds.setPassword("root");
//
//		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
//
//		String sql = String.format("SELECT " 
//				+ " c.name, " 
//				+ "	c.address, " 
//				+ "	i.number, "
//				+ "	s.service_description, " 
//				+ "	ii.quantity, ii.unit_value, " 
//				+ "	r.name As recurso, "
//				+ "	rq.qualificatin_name as funcao, " 
//				+ "	ii.tax_percent, " 
//				+ "	ii.discount_percent, "
//				+ "	ii.subtotal,"
//				+ "	i.value " 
//				+ "FROM " + "customer c "
//				+ "inner join invoice i on c.id_customer = i.customer_id "
//				+ "inner join invoice_item ii on ii.invoice_id = i.number "
//				+ "inner join service s on s.service_id = ii.service_id "
//				+ "inner join resource r on r.id_resource = ii.resource_id "
//				+ "inner join resource_qualification_assignement rqa on rqa.resource_id = r.id_resource "
//				+ "inner join resource_qualification rq on rq.id_resource_qualification = rqa.qualification_id "
//				+ "order by " 
//				+ "	c.name, i.number ");
//
//		List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);
//
//		Gson gson = new Gson();
//		String json = gson.toJson(listMap);
//
//		Type listType = new TypeToken<ArrayList<NotaFiscal>>() {
//		}.getType();
//		List<NotaFiscal> listNotaFiscal = gson.fromJson(json, listType);
//
//		// Limpar tabela
//		try {
//			notaFiscalRepository.deleteAll();
//		} catch (Exception e) {
//			System.out.println("Não tem dados");
//		}
//
//		for (NotaFiscal notaFiscal : listNotaFiscal) {
//			this.save(notaFiscal);
//		}
//
//		return "Tabela notafiscal populada com sucesso";
//	}

	



}
