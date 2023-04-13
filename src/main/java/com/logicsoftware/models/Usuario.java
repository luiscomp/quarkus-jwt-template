package com.logicsoftware.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "usuario", schema = "readface")
@FilterDefs({
    @FilterDef(name = "nome", parameters = @ParamDef(name = "nome", type = "string")),
    @FilterDef(name = "email", parameters = @ParamDef(name = "email", type = "string")),
    @FilterDef(name = "perfil", parameters = @ParamDef(name = "perfil", type = "com.logicsoftware.models.Perfil"))
})
@Filters({
    @Filter(name = "nome", condition = "nome = :nome"),
    @Filter(name = "email", condition = "email = :email"),
    @Filter(name = "perfil", condition = "perfil_id = :perfil")
})
@SequenceGenerator(name = "usuario_id_seq", sequenceName = "usuario_id_seq", initialValue = 1, allocationSize = 1)
public class Usuario extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_id_seq")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @Column(nullable = false)
    private String senha;
}
