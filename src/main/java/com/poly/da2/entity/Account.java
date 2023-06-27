package com.poly.da2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Getter
@Setter
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user")
	private Userss user;

	@JsonIgnore
	@OneToOne(mappedBy = "username", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private PasswordResetToken passwordResetToken;
}
