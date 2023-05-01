package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import adega.de.vinhos.adegadevinhos.exception.BadRequestException;
import adega.de.vinhos.adegadevinhos.mapper.VinhoMapper;
import adega.de.vinhos.adegadevinhos.repository.VinhoRepository;
import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VinhoService {

    private final VinhoRepository vinhoRepository;

    public Page<Vinho> listAll(Pageable pageable) {
        return vinhoRepository.findAll(pageable);
    }

    public Vinho findByIdOrThrowBadRequestException(long id) {
        return vinhoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(TranslationConstants.VINHO_NAO_ENCONTRADO));
    }

    @Transactional(rollbackFor = Exception.class)
    public Vinho save(VinhoDTO vinhoDTO) {
        return vinhoRepository.save(VinhoMapper.INSTANCE.toVinho(vinhoDTO));
    }

    public void delete(long id) {
        vinhoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(VinhoDTO vinhoDTO) {
        Vinho savedVinho = findByIdOrThrowBadRequestException(vinhoDTO.getId());
        Vinho vinho = VinhoMapper.INSTANCE.toVinho(vinhoDTO);
        vinho.setId(savedVinho.getId());
        vinhoRepository.save(vinho);
    }

    public List<Vinho> findByTipo(String tipo) {
        return vinhoRepository.findByTipo(tipo);
    }

    public List<Vinho> listAllNonPageable() {
        return vinhoRepository.findAll();
    }
}
