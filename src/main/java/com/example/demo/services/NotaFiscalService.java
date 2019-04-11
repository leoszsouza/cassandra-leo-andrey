package com.example.demo.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.example.demo.dto.NotaDTO;
import com.example.demo.dto.NotaMySQLDTO;
import com.example.demo.model.NotaFiscal;
import com.example.demo.repositories.NotaFiscalRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class NotaFiscalService {

	@Autowired
	private NotaFiscalRepository notaFiscalRepository;

	public List<NotaMySQLDTO> getNotasFiscaisMysql() {

		List<NotaMySQLDTO> notas = new ArrayList<NotaMySQLDTO>();

		String sql = String.format("SELECT " + " c.name, " + "	c.address, " + "	i.number, "
				+ "	s.service_description, " + "	ii.quantity, ii.unit_value, " + "	r.name As recurso, "
				+ "	rq.qualificatin_name as funcao, " + "	ii.tax_percent, " + "	ii.discount_percent, "
				+ "	ii.subtotal," + "	i.value " + "FROM " + "customer c "
				+ "inner join invoice i on c.id_customer = i.customer_id "
				+ "inner join invoice_item ii on ii.invoice_id = i.number "
				+ "inner join service s on s.service_id = ii.service_id "
				+ "inner join resource r on r.id_resource = ii.resource_id "
				+ "inner join resource_qualification_assignement rqa on rqa.resource_id = r.id_resource "
				+ "inner join resource_qualification rq on rq.id_resource_qualification = rqa.qualification_id "
				+ "order by " + "	c.name, i.number ");

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/invoice_system_univali?useTimezone=true&serverTimezone=UTC", "root",
					"");

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

                NotaMySQLDTO nota = new NotaMySQLDTO();
				nota.setNome(rs.getString(1));
				nota.setEndereco(rs.getString(2));
				nota.setNumero(Integer.valueOf(rs.getString(3)));
				nota.setDescricaoServico(rs.getString(4));
				nota.setQuantidade(rs.getString(5));
				nota.setValorUnitario(rs.getString(6));
				nota.setRecurso(rs.getString(7));
				nota.setFuncao(rs.getString(8));
				nota.setPorcentoTaxa(Double.valueOf(rs.getString(9)));
				nota.setPorcentoDesconto(Double.valueOf(rs.getString(10)));
				nota.setSubtotal(Double.valueOf(rs.getString(11)));
				nota.setTotal(Double.valueOf(rs.getString(12)));

				notas.add(nota);
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notas;
	}

	public List<NotaFiscal> migrateDataToCassandra() {

		List<NotaDTO> notas = new ArrayList<NotaDTO>();

		String sql = String.format("SELECT " + " c.name, " + "	c.address, " + "	i.number, "
				+ "	s.service_description, " + "	ii.quantity, ii.unit_value, " + "	r.name As recurso, "
				+ "	rq.qualificatin_name as funcao, " + "	ii.tax_percent, " + "	ii.discount_percent, "
				+ "	ii.subtotal," + "	i.value " + "FROM " + "customer c "
				+ "inner join invoice i on c.id_customer = i.customer_id "
				+ "inner join invoice_item ii on ii.invoice_id = i.number "
				+ "inner join service s on s.service_id = ii.service_id "
				+ "inner join resource r on r.id_resource = ii.resource_id "
				+ "inner join resource_qualification_assignement rqa on rqa.resource_id = r.id_resource "
				+ "inner join resource_qualification rq on rq.id_resource_qualification = rqa.qualification_id "
				+ "order by " + "	c.name, i.number ");

		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/invoice_system_univali?useTimezone=true&serverTimezone=UTC");
		ds.setUsername("root");
		ds.setPassword("");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

		List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);

		Gson gson = new Gson();
		String json = gson.toJson(listMap);

		Type listType = new TypeToken<ArrayList<NotaFiscal>>() {
		}.getType();
		List<NotaFiscal> listNotaFiscal = gson.fromJson(json, listType);

		try {
			notaFiscalRepository.deleteAll();
		} catch (Exception e) {
			System.out.println("Não tem dados");
		}

		for (NotaFiscal notaFiscal : listNotaFiscal) {
			this.save(notaFiscal);
		}

		return listNotaFiscal;
	}

	public NotaFiscal save(NotaFiscal notaFiscal) {
		notaFiscal.setId(UUID.randomUUID());
		return notaFiscalRepository.save(notaFiscal);
	}

	public void insertNotasCassandra(List<NotaMySQLDTO> notas) {

		String serverIP = "127.0.0.1";
		String keyspace = "cassandra_notas";

		Cluster cluster = Cluster.builder().addContactPoints(serverIP).build();

		Session session = cluster.connect(keyspace);

		String cql = "";

		for (NotaMySQLDTO nota : notas) {
			cql = "INSERT INTO cassandra_notas.notas ("
					+ "id , nome, endereco, numero, servico, quantidade, valor, recurso,"
					+ " funcao, taxa, desconto, subtotal total) " + "VALUES ('uuid()','" + nota.getNome() + "','"
					+ nota.getEndereco() + "', '" + nota.getNumero() + "','" + nota.getDescricaoServico() + "'" + ", '"
					+ nota.getQuantidade() + "','" + nota.getValorUnitario() + "', '" + nota.getRecurso() + "','"
					+ nota.getFuncao() + "'" + ", '" + nota.getPorcentoTaxa() + "','" + nota.getPorcentoDesconto()
					+ "', '" + nota.getSubtotal() + "', '" + nota.getTotal() + "')";

			session.execute(cql);
		}

		session.close();
	}
}
