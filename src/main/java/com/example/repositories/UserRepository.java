package com.example.repositories;

import com.example.models.UserModal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModal, String> {

    UserModal findByUsername(String username);

}
