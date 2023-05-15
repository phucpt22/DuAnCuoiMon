package com.poly.da2.entity;

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
@Table(name = "authorities",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","role_id"})})
public class Authority implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Date createDate;
	private Date updateDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Userss user;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	 
}
