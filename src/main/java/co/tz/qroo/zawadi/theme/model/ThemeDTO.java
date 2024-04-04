package co.tz.qroo.zawadi.theme.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ThemeDTO {

    private UUID id;

    @Size(max = 255)
    private String backgroundImage;

    @NotNull
    @Size(max = 6)
    private String fontColor;

    @NotNull
    @Size(max = 6)
    private String primaryColor;

    @NotNull
    @Size(max = 6)
    private String secondaryColor;

    @Size(max = 255)
    private String googleFont;

}
