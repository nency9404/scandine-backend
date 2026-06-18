package com.scandine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cafeteria_tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CafeteriaTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;
}
