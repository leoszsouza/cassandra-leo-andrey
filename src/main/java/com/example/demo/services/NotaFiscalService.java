package com.example.demo.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.univali.notaFiscal.dto.NotaFiscalDTO;
import br.com.univali.notaFiscal.dto.NotaFiscalItemDTO;
import br.com.univali.notaFiscal.dto.NotaFiscalList;
import br.com.univali.notaFiscal.model.NotaFiscal;
import br.com.univali.notaFiscal.repositories.NotaFiscalRepository;

@Service
public class NotaFiscalService {

	@Autowired
	private NotaFiscalRepository notaFiscalRepository;

	public String getNotasFiscaisMysql() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/invoice_system_univali");
		ds.setUsername("root");
		ds.setPassword("root");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		String sql = String.format("SELECT " 
				+ " c.name, " 
				+ "	c.address, " 
				+ "	i.number, "
				+ "	s.service_description, " 
				+ "	ii.quantity, ii.unit_value, " 
				+ "	r.name As recurso, "
				+ "	rq.qualificatin_name as funcao, " 
				+ "	ii.tax_percent, " 
				+ "	ii.discount_percent, "
				+ "	ii.subtotal,"
				+ "	i.value " 
				+ "FROM " + "customer c "
				+ "inner join invoice i on c.id_customer = i.customer_id "
				+ "inner join invoice_item ii on ii.invoice_id = i.number "
				+ "inner join service s on s.service_id = ii.service_id "
				+ "inner join resource r on r.id_resource = ii.resource_id "
				+ "inner join resource_qualification_assignement rqa on rqa.resource_id = r.id_resource "
				+ "inner join resource_qualification rq on rq.id_resource_qualification = rqa.qualification_id "
				+ "order by " 
				+ "	c.name, i.number ");

		List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);

		Gson gson = new Gson();
		String json = gson.toJson(listMap);

		Type listType = new TypeToken<ArrayList<NotaFiscal>>() {
		}.getType();
		List<NotaFiscal> listNotaFiscal = gson.fromJson(json, listType);

		// Limpar tabela
		try {
			notaFiscalRepository.deleteAll();
		} catch (Exception e) {
			System.out.println("Não tem dados");
		}

		for (NotaFiscal notaFiscal : listNotaFiscal) {
			this.save(notaFiscal);
		}

		return "Tabela notafiscal populada com sucesso";
	}

	public List<NotaFiscalList> listAll() {
		List<NotaFiscal> notasFiscais = notaFiscalRepository.findAll();
		List<NotaFiscalList> listNotasFiscais = new ArrayList<>();
		for (NotaFiscal notaFiscal : notasFiscais) {
			if (ignoraDuplicado(listNotasFiscais, notaFiscal.getNumber())) {

				NotaFiscalList nf = new NotaFiscalList();
				nf.setNumber(notaFiscal.getNumber());
				nf.setName(notaFiscal.getName());

				listNotasFiscais.add(nf);
			}
		}

		List<NotaFiscalList> listNotasFiscaisNoDuplicate = listNotasFiscais.stream().distinct()
				.collect(Collectors.toList());
		return listNotasFiscaisNoDuplicate;
	}

	public NotaFiscalDTO getById(UUID id) {
		List<NotaFiscal> listNotaFiscal = new ArrayList<>();
		NotaFiscal notaFiscal = notaFiscalRepository.findById(id).orElse(null);
		listNotaFiscal.add(notaFiscal);

		return this.converteNotaFiscalNotafiscalDTO(listNotaFiscal);
	}

	public NotaFiscalDTO getByNumber(Integer number) {
		List<NotaFiscal> listNotaFiscal = notaFiscalRepository.getByNumber(number);
		return this.converteNotaFiscalNotafiscalDTO(listNotaFiscal);
	}

	public NotaFiscal save(NotaFiscal notaFiscal) {
		notaFiscal.setId(UUID.randomUUID());
		return notaFiscalRepository.save(notaFiscal);
	}

	public void delete(UUID id) {
		notaFiscalRepository.deleteById(id);
	}

	public NotaFiscalDTO converteNotaFiscalNotafiscalDTO(List<NotaFiscal> listaNotafiscal) {
		NotaFiscalDTO notaFiscalDTO = new NotaFiscalDTO();

		notaFiscalDTO.setName(listaNotafiscal.get(0).getName());
		notaFiscalDTO.setAddress(listaNotafiscal.get(0).getAddress());
		notaFiscalDTO.setNumber(listaNotafiscal.get(0).getNumber());
		notaFiscalDTO.setValue(listaNotafiscal.get(0).getValue());

		List<NotaFiscalItemDTO> items = new ArrayList<>();

		for (NotaFiscal nf : listaNotafiscal) {
			NotaFiscalItemDTO item = new NotaFiscalItemDTO();

			item.setService_description(nf.getService_description());
			item.setQuantity(nf.getQuantity());
			item.setUnit_value(nf.getUnit_value());
			item.setRecurso(nf.getRecurso());
			item.setFuncao(nf.getFuncao());
			item.setTax_percent(nf.getTax_percent());
			item.setDiscount_percent(nf.getDiscount_percent());
			item.setSubtotal(nf.getSubtotal());

			items.add(item);
		}

		notaFiscalDTO.setItems(items);

		return notaFiscalDTO;
	}

	private Boolean ignoraDuplicado(List<NotaFiscalList> listNotaFiscal, Integer number) {
		for (NotaFiscalList nf : listNotaFiscal) {
			if (nf.getNumber().equals(number))
				return false;
		}

		return true;
	}
}
