package com.poly.da2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String image;
	private Double price;

	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	private Date createDate = new Date();
	@Temporal(TemporalType.DATE)
	private Date updateDate;

	private Boolean available;
	@ManyToOne
	@JoinColumn(name = "Categoryid")
	private Category category;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;

}
