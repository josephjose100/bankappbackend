package com.demoSpringSecurity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demoSpringSecurity.entity.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	
	
}
