package com.logicsoftware.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@MappedSuperclass
public class BaseModel {
    @CreationTimestamp
    @Column(name = "criado_em")
    public ZonedDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    public ZonedDateTime atualizadoEm;
}
