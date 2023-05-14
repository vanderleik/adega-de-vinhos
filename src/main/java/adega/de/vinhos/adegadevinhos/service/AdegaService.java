package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.dto.AdegaDTO;
import adega.de.vinhos.adegadevinhos.exception.BadRequestException;
import adega.de.vinhos.adegadevinhos.mapper.AdegaMapper;
import adega.de.vinhos.adegadevinhos.repository.AdegaRepository;
import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdegaService {

    @Autowired
    private AdegaRepository adegaRepository;

    public Page<Adega> listAll(Pageable pageable){
        return adegaRepository.findAll(pageable);
    }

    public Adega findByIdOrThrowBadRequestException(long id) {
        return adegaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(TranslationConstants.ADEGA_NAO_ENCONTRADA));
    }
    //save

    @Transactional(rollbackFor = Exception.class)
    public Adega save(AdegaDTO adegaDTO) {
        return adegaRepository.save(AdegaMapper.INSTANCE.toAdega(adegaDTO));
    }

    //To-do
    //delete
    //replace
    //findByNome
    //listAllNonPageable
}
