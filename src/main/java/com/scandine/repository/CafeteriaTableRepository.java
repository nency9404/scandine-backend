package com.scandine.repository;

import com.scandine.entity.CafeteriaTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeteriaTableRepository extends JpaRepository<CafeteriaTable,Integer> {
    Optional<CafeteriaTable> findByTableNumber(Integer tableNumber);
}
