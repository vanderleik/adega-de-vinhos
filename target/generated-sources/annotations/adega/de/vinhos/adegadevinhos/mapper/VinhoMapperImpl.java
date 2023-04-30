package adega.de.vinhos.adegadevinhos.mapper;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-30T10:11:16-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
public class VinhoMapperImpl extends VinhoMapper {

    @Override
    public Vinho toVinho(VinhoDTO vinhoDTO) {
        if ( vinhoDTO == null ) {
            return null;
        }

        Vinho.VinhoBuilder vinho = Vinho.builder();

        vinho.id( vinhoDTO.getId() );
        vinho.tipo( vinhoDTO.getTipo() );

        return vinho.build();
    }
}
