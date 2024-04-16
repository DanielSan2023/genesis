package com.engeto.genesis.model;

import com.engeto.genesis.domain.ContactType;
import com.engeto.genesis.domain.UserInfo;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ContactDetailDTO {

    private Long id;

    @Size(max = 255)
    private UserInfo userId;

    @Size(max = 255)
    private ContactType typeId;

    @Size(max = 255)
    private String value;

}


