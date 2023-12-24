package com.engeto.genesis.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInfoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String surname;

    @NotNull
    @Size(max = 255)
    private String personId;

    @NotNull
    @Size(max = 255)
    private String uuid;


}
