package com.poly.da2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account implements Serializable{
	@Id
	private String username;
	private String password;
	private String gmail;
	private Date createDate;
	private Date updateDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idUser", referencedColumnName = "id")
	private User user;

}
