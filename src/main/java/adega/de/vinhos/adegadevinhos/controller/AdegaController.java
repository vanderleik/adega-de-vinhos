package adega.de.vinhos.adegadevinhos.controller;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.dto.AdegaDTO;
import adega.de.vinhos.adegadevinhos.service.AdegaService;
import adega.de.vinhos.adegadevinhos.util.DateUtil;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("adegas")
@Log4j2

public class AdegaController {

    @Autowired
    private DateUtil defaultUtil;
    @Autowired
    private AdegaService adegaService;

    @GetMapping
    public ResponseEntity<Page<Adega>> list(Pageable pageable) {
        log.info(defaultUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(adegaService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Adega> findById(@PathVariable long id) {
        return ResponseEntity.ok(adegaService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<Adega> save(@RequestBody @Valid AdegaDTO adegaDTO){
        return new ResponseEntity(adegaService.save(adegaDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        adegaService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Adega> replace(@RequestBody AdegaDTO adegaDTO) {
        adegaService.replace(adegaDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/nome")
    public ResponseEntity<List<Adega>> findByNome(@RequestParam(required = false) String nome) {
        return ResponseEntity.ok(adegaService.findByNome(nome));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Adega>> listAll() {
        log.info(defaultUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(adegaService.listAllNonPageable());
    }
}
