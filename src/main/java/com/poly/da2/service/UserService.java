package com.poly.da2.service;

import com.poly.da2.dao.AccountDAO;
import com.poly.da2.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;



@Service
public class UserService implements UserDetailsService {
	@Autowired
	AccountDAO dao;
	@Autowired
	BCryptPasswordEncoder pe;

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		try {
//			Account account = dao.findById(username).get();
//			String password = account.getPassword();
//			String[] roles = account.getAuthorities().stream().map(au -> au.getRole().getId())
//					.collect(Collectors.toList()).toArray(new String[0]);
//			return User.withUsername(username).password(pe.encode(password)).roles(roles).build();
//		} catch (Exception e) {
//			throw new UsernameNotFoundException(username + " not found!");
//		}
		return  null;
	}

	public void loginFormOAuth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("email");
		String fullname = oauth2.getPrincipal().getAttribute("username");
		//String password = Long.toHexString(System.currentTimeMillis());
		UserDetails user = User.withUsername(email).password("123").roles("GUEST").build();
		//UserDetails user2 = User.wit;
		Authentication auth =new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}
