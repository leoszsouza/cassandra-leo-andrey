package com.example.demo.model;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Table("notafiscal")
public class NotaFiscal implements Serializable {

	private static final long serialVersionUID = 622582932174223257L;

	@PrimaryKey
	@CassandraType(type = DataType.Name.UUID)
	private UUID id;
	private String name;
	private String address;

	@Indexed
	private Integer number;
	private String service_description;
	private Long quantity;
	private Long unit_value;
	private String recurso;
	private String funcao;
	private Double tax_percent;
	private Double discount_percent;
	private Double subtotal;
	private Double value;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getService_description() {
		return service_description;
	}

	public void setService_description(String service_description) {
		this.service_description = service_description;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getUnit_value() {
		return unit_value;
	}

	public void setUnit_value(Long unit_value) {
		this.unit_value = unit_value;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public Double getTax_percent() {
		return tax_percent;
	}

	public void setTax_percent(Double tax_percent) {
		this.tax_percent = tax_percent;
	}

	public Double getDiscount_percent() {
		return discount_percent;
	}

	public void setDiscount_percent(Double discount_percent) {
		this.discount_percent = discount_percent;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
