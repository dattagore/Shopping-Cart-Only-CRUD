package com.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

}
