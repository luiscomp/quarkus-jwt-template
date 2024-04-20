package com.logicsoftware.models;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario", schema = "template")
@FilterDefs({
    @FilterDef(name = "nome", parameters = @ParamDef(name = "nome", type = String.class)),
    @FilterDef(name = "email", parameters = @ParamDef(name = "email", type = String.class)),
    @FilterDef(name = "perfil", parameters = @ParamDef(name = "perfil", type = Class.class))
})
@Filters({
    @Filter(name = "usuario_nome", condition = "nome = :nome"),
    @Filter(name = "usuario_email", condition = "email = :email"),
    @Filter(name = "usuario_perfil", condition = "perfil_id = :perfil")
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
