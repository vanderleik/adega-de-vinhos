package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VinhoService {

    private List<Vinho> vinhos = List.of(new Vinho(1L, "Branco"),
            new Vinho(2L, "Tinto"),
            new Vinho(3L, "Rose"),
            new Vinho (4L, "Espumante"));

    public List<Vinho> listAll() {
        return vinhos;
    }

    public Vinho findById(long id) {
        return vinhos.stream().filter(vinho -> vinho.getId().equals(id)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vinho não encontrado"));
    }
}
