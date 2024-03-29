package com.poly.da2.repository;

import com.poly.da2.entity.Account;
import com.poly.da2.entity.Authority;
import com.poly.da2.entity.Userss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	@Query("SELECT DISTINCT a FROM Authority a WHERE a.user IN ?1")
    List<Authority> authoritiesOf(List<Userss> users);

}
