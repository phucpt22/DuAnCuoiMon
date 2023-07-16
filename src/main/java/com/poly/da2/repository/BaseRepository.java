package com.poly.da2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseRepository<T> extends JpaRepository<T, String> {

}
