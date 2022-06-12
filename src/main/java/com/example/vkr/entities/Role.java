package com.example.vkr.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
