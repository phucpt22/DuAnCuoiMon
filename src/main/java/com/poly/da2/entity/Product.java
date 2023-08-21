package com.poly.da2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedStoredProcedureQuery(
		name = "Product.sp_SpDuoc_Mua_nhieu",
		procedureName = "sp_SpDuoc_Mua_nhieu",
		resultClasses = { Product.class }
)
@Table(name = "Products")
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private Double price;
	private Double currentprice;
	private String description;
	private String thumbnail_url;
	private Double rating_average;
	private Integer review_count;
	private String image_urls;
	private double discount;
	public String getFormatCurrent_Price() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		if(currentprice == null) return "";
		String formattedPrice = decimalFormat.format(currentprice);
		return formattedPrice;
	}
	public String getFormatPrice() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		if(price == null) return  "";
		String formattedPrice = decimalFormat.format(price);
		return formattedPrice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Createdate")
	private Date createDate = new Date();
	@Temporal(TemporalType.DATE)
	@Column(name="update_date")
	private Date updateDate = new Date();

	private Boolean available;
	@ManyToOne
	@JoinColumn(name = "Categoryid")
	private Category category;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "product_id")
	List<Reviews> reviews;

}
