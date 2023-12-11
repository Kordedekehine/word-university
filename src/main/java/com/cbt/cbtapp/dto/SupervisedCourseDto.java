package com.cbt.cbtapp.dto;

import com.cbt.cbtapp.model.Role;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SupervisedCourseDto {

    @NotBlank(message = "The title cannot be blank.")
    @Size(min = 3, max = 30, message = "The title should have a length between 3 and " +
            "30")
    private String title;

    @NotBlank(message = "The language cannot be blank.")
    private String language;

    @Min(value = 1, message = "The Word Target must be at least 1")
    @Max(value = 25, message = "The Word Target should be at most 25")
    private int minPointsPerWord;

}
