package com.dv.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dv.dscommerce.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
}
