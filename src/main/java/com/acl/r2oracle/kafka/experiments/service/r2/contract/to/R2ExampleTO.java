package com.acl.r2oracle.kafka.experiments.service.r2.contract.to;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class R2ExampleTO {
    @JsonIgnore
    private String id;
    private String name;
    private String lastName;
    private LocalDateTime birthDate;
    private String rut;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public R2ExampleTO withId(String id) {
        this.setId(id);
        return this;
    }
}
