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
@Table(name = "perfil", schema = "template")
@FilterDefs({
    @FilterDef(name = "perfil_nome", parameters = @ParamDef(name = "nome", type = String.class)),
})
@Filters({
    @Filter(name = "perfil_nome", condition = "name = :nome"),
})
@SequenceGenerator(name = "perfil_id_seq", sequenceName = "perfil_id_seq", initialValue = 1, allocationSize = 1)
public class Perfil extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_id_seq")
    private Long id;

    @Column(nullable = false)
    private String nome;
}
