package com.twitter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.model.users;

public interface UserRepository extends JpaRepository<users, Integer> {

}
