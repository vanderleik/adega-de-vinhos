package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.dto.AdegaDTO;
import adega.de.vinhos.adegadevinhos.exception.BadRequestException;
import adega.de.vinhos.adegadevinhos.repository.AdegaRepository;
import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Log4j2
class AdegaServiceTest {

    @InjectMocks
    private AdegaService adegaService;
    @Mock
    private AdegaRepository adegaRepository;
    @Mock
    private Pageable pageable;

    @Test
    @DisplayName("Deve retornar uma lista de adegas")
    void testListAll(){
        PageImpl<Adega> adegaPage = new PageImpl<>(List.of(createAdegaDeCasa(), createAdegaDoEscritorio(), createAdegaDaPraia()));
        when(adegaRepository.findAll(pageable)).thenReturn(adegaPage);

        Page<Adega> adegas = Assertions.assertDoesNotThrow(() -> adegaService.listAll(pageable));
        assertNotNull(adegas);
        assertFalse(adegas.toList().isEmpty());
        assertEquals(3, adegas.stream().toList().size());
        assertEquals(createAdegaDeCasa().getId(), adegas.toList().get(0).getId());
        assertEquals(createAdegaDoEscritorio().getNome(), adegas.toList().get(1).getNome());
        assertEquals(createAdegaDaPraia().getCapacidade(), adegas.toList().get(2).getCapacidade());
    }

    @Test
    @DisplayName("Ao passar um id, deve retornar uma adega")
    void testFindById(){
        when(adegaRepository.findById(1L)).thenReturn(Optional.of(createAdegaDeCasa()));

        Adega adega = assertDoesNotThrow(() -> adegaService.findByIdOrThrowBadRequestException(1L));
        assertNotNull(adega);
        assertEquals(createAdegaDeCasa().getId(), adega.getId());
        assertEquals(createAdegaDeCasa().getNome(), adega.getNome());
        assertEquals(createAdegaDeCasa().getCapacidade(), adega.getCapacidade());
    }

    @Test
    @DisplayName("Ao passar um id que não tem no banco de dados, deve retornar uma exceção")
    void testFindByIdThrowBadRequestException(){
        when(adegaRepository.findById(4L)).thenThrow(new BadRequestException(TranslationConstants.ADEGA_NAO_ENCONTRADA));
        BadRequestException exception = assertThrows(BadRequestException.class, () -> adegaService.findByIdOrThrowBadRequestException(4L));
        assertEquals(TranslationConstants.ADEGA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar uma adega de vinhos")
    void testSave() {
        Adega adegaDeCasa = createAdegaDeCasa();
        AdegaDTO dto = new AdegaDTO();
        dto.setId(adegaDeCasa.getId());
        dto.setNome(adegaDeCasa.getNome());
        Mockito.when(adegaRepository.save(Mockito.any())).thenReturn(adegaDeCasa);

        Adega adegaCriada = assertDoesNotThrow(() -> adegaService.save(dto));
        assertNotNull(adegaCriada);
        assertEquals(adegaDeCasa.getId(), adegaCriada.getId());
        assertEquals(adegaDeCasa.getNome(), adegaCriada.getNome());
        assertEquals(adegaDeCasa.getCapacidade(), adegaCriada.getCapacidade());
    }

    private Adega createAdegaDeCasa() {
        return Adega.builder()
                .id(1L)
                .nome("Adega de casa")
                .capacidade(20)
                .build();
    }

    private Adega createAdegaDoEscritorio(){
        return Adega.builder()
                .id(2L)
                .nome("Adega do escritório")
                .capacidade(10)
                .build();
    }

    private Adega createAdegaDaPraia() {
        return Adega.builder()
                .id(3L)
                .nome("Adega do escritório")
                .capacidade(6)
                .build();
    }
}