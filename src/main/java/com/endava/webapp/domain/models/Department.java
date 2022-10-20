package com.endava.webapp.domain.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Setter(AccessLevel.NONE)
    @Id
    @SequenceGenerator(name = "departments_seq", sequenceName = "departments_seq", allocationSize = 1)
    @GeneratedValue(generator = "departments_seq")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "location")
    String location;
}
