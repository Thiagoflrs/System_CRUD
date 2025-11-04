package com.devsuperior.desafio.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.desafio.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	
}
