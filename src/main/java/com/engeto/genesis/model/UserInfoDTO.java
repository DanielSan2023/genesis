package com.engeto.genesis.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserInfoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;

    @NotNull
    @Size(max = 12)
    private String personId;

    @NotNull
    @Size(max = 255)
    private String uuid;

    public UserInfoDTO(String name, String surname, String personId, String uuid) {
        this.name = name;
        this.surname = surname;
        this.personId = personId;
        this.uuid = uuid;
    }
}
