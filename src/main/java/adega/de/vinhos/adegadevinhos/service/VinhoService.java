package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import adega.de.vinhos.adegadevinhos.mapper.VinhoMapper;
import adega.de.vinhos.adegadevinhos.repository.VinhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VinhoService {

    private final VinhoRepository vinhoRepository;

    public List<Vinho> listAll() {
        return vinhoRepository.findAll();
    }

    public Vinho findByIdOrThrowBadRequestException(long id) {
        return vinhoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vinho não encontrado"));
    }

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
}
