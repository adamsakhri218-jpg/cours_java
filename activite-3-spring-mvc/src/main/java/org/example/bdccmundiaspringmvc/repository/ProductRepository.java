package org.example.bdccmundiaspringmvc.repository;

import org.example.bdccmundiaspringmvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
