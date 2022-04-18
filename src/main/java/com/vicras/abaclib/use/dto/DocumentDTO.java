package com.vicras.abaclib.use.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
public class DocumentDTO {

    @Null(groups = {CreatePhase.class, UpdatePhase.class})
    private Long id;

    @NotEmpty(groups = {CreatePhase.class, UpdatePhase.class})
    private String title;

    @NotEmpty(groups = {CreatePhase.class, UpdatePhase.class})
    private String text;

    @Null(groups = CreatePhase.class)
    private LocalDateTime createdAt;

    @Null(groups = CreatePhase.class)
    private LocalDateTime updatedAt;

    @Null(groups = CreatePhase.class)
    private Long creatorId;

    public interface CreatePhase {
    }

    ;

    public interface UpdatePhase {
    }

    ;
}
