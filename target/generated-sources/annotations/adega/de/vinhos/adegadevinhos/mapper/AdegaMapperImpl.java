package adega.de.vinhos.adegadevinhos.mapper;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.dto.AdegaDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-14T16:58:54-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AdegaMapperImpl extends AdegaMapper {

    @Override
    public Adega toAdega(AdegaDTO adegaDTO) {
        if ( adegaDTO == null ) {
            return null;
        }

        Adega.AdegaBuilder adega = Adega.builder();

        adega.id( adegaDTO.getId() );
        adega.nome( adegaDTO.getNome() );
        adega.capacidade( adegaDTO.getCapacidade() );

        return adega.build();
    }
}
