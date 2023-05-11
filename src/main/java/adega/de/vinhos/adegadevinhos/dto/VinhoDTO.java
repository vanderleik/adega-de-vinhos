package adega.de.vinhos.adegadevinhos.dto;

import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VinhoDTO {
    private Long id;
    @NotEmpty(message = TranslationConstants.TIPO_NOT_EMPTY_OR_NULL)
    private String tipo;
}
