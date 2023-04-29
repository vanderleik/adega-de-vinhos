package adega.de.vinhos.adegadevinhos.mapper;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class VinhoMapper {

    public static final VinhoMapper INSTANCE = Mappers.getMapper(VinhoMapper.class);

    public abstract Vinho toVinho(VinhoDTO vinhoDTO);
}
