package com.example.library.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Registries")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Book bookRent;

    @NotNull
    private String startPeriod;

    private boolean tombstone;
}

