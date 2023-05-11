package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.repository.AdegaRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

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