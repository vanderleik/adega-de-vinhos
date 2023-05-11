package adega.de.vinhos.adegadevinhos.domain;

import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Builder
public class Adega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = TranslationConstants.NOME_ADEGA_NOT_NULL)
    private String nome;
    private Integer capacidade;
}
