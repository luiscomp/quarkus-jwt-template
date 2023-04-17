package com.logicsoftware.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "perfil", schema = "template")
@FilterDefs({
    @FilterDef(name = "nome", parameters = @ParamDef(name = "nome", type = "string")),
})
@Filters({
    @Filter(name = "nome", condition = "name = :nome"),
})
@SequenceGenerator(name = "perfil_id_seq", sequenceName = "perfil_id_seq", initialValue = 1, allocationSize = 1)
public class Perfil extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_id_seq")
    private Long id;

    @Column(nullable = false)
    private String nome;
}
