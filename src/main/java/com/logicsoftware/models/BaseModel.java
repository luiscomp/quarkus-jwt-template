package com.logicsoftware.models;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseModel {
    @CreationTimestamp
    @Column(name = "criado_em")
    public ZonedDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    public ZonedDateTime atualizadoEm;
}
