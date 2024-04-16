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

    @NotNull(message = "name shouldn't be null")
    @Size(max = 255)
    private String name;

    @NotNull(message = "surname shouldn't be null")
    @Size(max = 255)
    private String surname;

    @NotNull(message = "It needs to have exactly: 12 characters")
    @Size(min = 12, max = 12, message = "Person ID must be exactly 12 characters long")
    private String personId;

    @Size(max = 255)
    private String uuid;

    public UserInfoDTO(String name, String surname, String personId, String uuid) {
        this.name = name;
        this.surname = surname;
        this.personId = personId;
        this.uuid = uuid;
    }
}
