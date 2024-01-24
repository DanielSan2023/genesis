package com.engeto.genesis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class ContactDetail {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;


    @ManyToOne
    @JoinColumn(name = "userInfo_id")
    private UserInfo userId;

    @ManyToOne
    @JoinColumn(name = "contactType_id")
    private  ContactType type;

    @Column
    private String contType;

}
