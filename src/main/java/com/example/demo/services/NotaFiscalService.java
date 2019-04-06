package com.example.demo.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.example.demo.dto.NotaDTO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Service
public class NotaFiscalService {

    public List<NotaDTO> getNotasFiscaisMysql() {

        List<NotaDTO> notas = new ArrayList<NotaDTO>();

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

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/invoice_system_univali?useTimezone=true&serverTimezone=UTC",
                "root",
                "");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                NotaDTO nota = new NotaDTO();
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

    public void insertNotasCassandra(List<NotaDTO> notas){

        String serverIP = "127.0.0.1";
        String keyspace = "cassandra_notas";

        Cluster cluster = Cluster.builder()
            .addContactPoints(serverIP)
            .build();

        Session session = cluster.connect(keyspace);

        String cql = "";

        for(NotaDTO nota : notas){
            cql  = "INSERT INTO cassandra_notas.notas (" +
                "id , nome, endereco, numero, servico, quantidade, valor, recurso," +
                " funcao, taxa, desconto, subtotal total) " +
                "VALUES ('uuid()','"+nota.getNome()+"','"+nota.getEndereco()+"', '"+nota.getNumero()+"','"+nota.getDescricaoServico()+"'" +
                ", '"+nota.getQuantidade()+"','"+nota.getValorUnitario()+"', '"+nota.getRecurso()+"','"+nota.getFuncao()+"'" +
                ", '"+nota.getPorcentoTaxa()+"','"+nota.getPorcentoDesconto()+"', '"+nota.getSubtotal()+"', '"+nota.getTotal()+"')";

            session.execute(cql);
        }

        session.close();
    }
}
