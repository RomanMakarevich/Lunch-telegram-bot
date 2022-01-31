package com.igromatic.lunches.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;


import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "preorder")
@Data
public class PreorderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created")
    private LocalDate created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user_id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "preorder_product", joinColumns = {@JoinColumn(name = "preorder_id")}, inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<ProductEntity> products;
}
