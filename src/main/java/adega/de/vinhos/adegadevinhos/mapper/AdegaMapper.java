package adega.de.vinhos.adegadevinhos.mapper;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.dto.AdegaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AdegaMapper {
    public static final AdegaMapper INSTANCE = Mappers.getMapper(AdegaMapper.class);

    public abstract Adega toAdega(AdegaDTO adegaDTO);
}
