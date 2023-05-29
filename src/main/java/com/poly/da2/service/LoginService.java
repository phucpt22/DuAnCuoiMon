package com.poly.da2.service;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.Userss;
import com.poly.da2.repository.AccountRepository;
import com.poly.da2.repository.UserRepository;
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
public class LoginService implements UserDetailsService {
	@Autowired
	AccountRepository accRepository;
	@Autowired
	UserRepository uRepository;
	@Autowired
	BCryptPasswordEncoder pe;

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Account account = accRepository.findAcc(username);
			String password = account.getPassword();
//			String[] roles = account.getUser().getAuthorities().stream().map(au -> au.getRole().getId())
//					.collect(Collectors.toList()).toArray(new String[0]);
			return User.withUsername(username).password(password).build();
		} catch (Exception e) {
			throw new UsernameNotFoundException(username + " not found!");
		}
	}

	public void loginFormOAuth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("email");
		String fullname = oauth2.getPrincipal().getAttribute("fullname");
		//String password = Long.toHexString(System.currentTimeMillis());
		UserDetails user = User.withUsername(email).disabled(true).password("123").roles("r2").build();
		Authentication auth =new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		if(uRepository.findByEmail(email) == null){
			Userss o = new Userss();
			Account a = new Account();
			o.setGmail(email);
			a.setUser(o);
			a.setUsername(email);
			uRepository.save(o);
			accRepository.save(a);
		}else{

		}

	}



}
