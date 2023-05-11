package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.repository.AdegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdegaService {

    @Autowired
    private AdegaRepository adegaRepository;

    //listAll
    public Page<Adega> listAll(Pageable pageable){
        return adegaRepository.findAll(pageable);
    }

    //To-do
    //findById
    //save
    //delete
    //replace
    //findByNome
    //listAllNonPageable
}
