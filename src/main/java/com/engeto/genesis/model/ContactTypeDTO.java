package com.engeto.genesis.model;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ContactTypeDTO {

    @Size(max = 255)
    private Long id;

    @Size(max = 255)
    private String type;

}
