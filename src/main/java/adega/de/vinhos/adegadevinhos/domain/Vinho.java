package adega.de.vinhos.adegadevinhos.domain;

import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Builder
public class Vinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = TranslationConstants.TIPO_NOT_EMPTY_OR_NULL)
    private String tipo;
}
