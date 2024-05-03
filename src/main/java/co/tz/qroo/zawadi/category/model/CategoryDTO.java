package co.tz.qroo.zawadi.category.model;

import co.tz.qroo.zawadi.user.model.ActiveStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CategoryDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    @CategoryNameUnique
    private String name;

    @NotNull
    private ActiveStatus status;

    @NotNull
    @Size(max = 255)
    private String icon;

    @Size(max = 7)
    private String backgroundColor;

    @NotNull
    private Integer order;

}
