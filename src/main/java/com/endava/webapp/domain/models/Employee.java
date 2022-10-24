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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Setter(AccessLevel.NONE)
    @Id
    @SequenceGenerator(name = "employees_seq", sequenceName = "employees_seq", allocationSize = 1)
    @GeneratedValue(generator = "employees_seq")
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @ManyToOne
    @JoinColumn(name = "department", referencedColumnName = "id")
    Department department;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    @Column(name = "salary")
    double salary;
}
