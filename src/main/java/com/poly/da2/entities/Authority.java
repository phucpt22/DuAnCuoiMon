package com.poly.da2.entities;

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
@Table(name = "Authorities",uniqueConstraints = {@UniqueConstraint(columnNames = {"Username","Roleid"})})
public class Authority implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Date createDate;
	private Date updateDate;

	@ManyToOne
	@JoinColumn(name = "Username")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "Roleid")
	private Role role;
	 
}
