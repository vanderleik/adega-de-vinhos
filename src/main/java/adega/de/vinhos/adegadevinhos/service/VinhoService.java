package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.repository.VinhoRepository;
import adega.de.vinhos.adegadevinhos.requests.VinhoPostRequestBody;
import adega.de.vinhos.adegadevinhos.requests.VinhoPutRequestBody;
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

    public Vinho save(VinhoPostRequestBody vinhoPostRequestBody) {
        return vinhoRepository.save(Vinho.builder().tipo(vinhoPostRequestBody.getTipo()).build());
    }

    public void delete(long id) {
        vinhoRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(VinhoPutRequestBody vinhoPutRequestBody) {
        Vinho savedVinho = findByIdOrThrowBadRequestException(vinhoPutRequestBody.getId());
        Vinho vinho = Vinho.builder()
                .id(savedVinho.getId())
                .tipo(vinhoPutRequestBody.getTipo())
                .build();
        vinhoRepository.save(vinho);
    }
}
