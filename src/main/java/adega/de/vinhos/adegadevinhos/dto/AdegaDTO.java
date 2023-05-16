package adega.de.vinhos.adegadevinhos.dto;

import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdegaDTO {
    private Long id;
    @NotEmpty(message = TranslationConstants.NOME_ADEGA_NOT_NULL)
    private String nome;
    private Integer capacidade;
}
