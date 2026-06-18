package com.scandine.repository;

import com.scandine.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {
    List<MenuItem> findByCategory(String category);
    List<MenuItem> findByIsAvailableTrue();
}
