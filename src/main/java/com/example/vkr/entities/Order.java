package com.example.vkr.entities;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @CreatedDate
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate orderDate;

    private String status;

    private String amount;
}
