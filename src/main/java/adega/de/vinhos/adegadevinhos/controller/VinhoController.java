package adega.de.vinhos.adegadevinhos.controller;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.service.VinhoService;
import adega.de.vinhos.adegadevinhos.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("vinhos")
@Log4j2
@RequiredArgsConstructor
public class VinhoController {

    private final DateUtil defaultUtil;
    private final VinhoService vinhoService;

    @GetMapping
    public ResponseEntity<List<Vinho>> list() {
        log.info(defaultUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(vinhoService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Vinho> findById(@PathVariable long id) {
        return ResponseEntity.ok(vinhoService.findById(id));
    }
}
