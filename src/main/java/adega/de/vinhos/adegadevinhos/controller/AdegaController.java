package adega.de.vinhos.adegadevinhos.controller;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.service.AdegaService;
import adega.de.vinhos.adegadevinhos.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
}
