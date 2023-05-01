package adega.de.vinhos.adegadevinhos.controller;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import adega.de.vinhos.adegadevinhos.service.VinhoService;
import adega.de.vinhos.adegadevinhos.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Page<Vinho>> list(Pageable pageable) {
        log.info(defaultUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(vinhoService.listAll(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vinho>> listAll() {
        log.info(defaultUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(vinhoService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Vinho> findById(@PathVariable long id) {
        return ResponseEntity.ok(vinhoService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping
    public ResponseEntity<Vinho> save(@RequestBody @Valid VinhoDTO vinhoDTO){
        return new ResponseEntity(vinhoService.save(vinhoDTO), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        vinhoService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Vinho> replace(@RequestBody VinhoDTO vinhoDTO) {
        vinhoService.replace(vinhoDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/tipo")
    public ResponseEntity<List<Vinho>> findByTipo(@RequestParam(required = false) String tipo) {
        return ResponseEntity.ok(vinhoService.findByTipo(tipo));
    }
}
