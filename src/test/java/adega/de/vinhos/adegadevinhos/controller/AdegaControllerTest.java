package adega.de.vinhos.adegadevinhos.controller;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.dto.AdegaDTO;
import adega.de.vinhos.adegadevinhos.exception.BadRequestException;
import adega.de.vinhos.adegadevinhos.service.AdegaService;
import adega.de.vinhos.adegadevinhos.util.DateUtil;
import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Log4j2
class AdegaControllerTest {

    @InjectMocks
    private AdegaController adegaController;
    @Mock
    private DateUtil defaultUtil;
    @Mock
    private AdegaService adegaService;

    @Test
    @DisplayName("Deve retornar uma lista de adegas")
    void testList(){
        PageImpl<Adega> validAdegaPage = new PageImpl<>(List.of(createAdegaDeCasa(), createAdegaDaPraia(), createAdegaDoEscritorio()));
        BDDMockito.when(adegaService.listAll(ArgumentMatchers.any())).thenReturn(validAdegaPage);

        Page<Adega> adegaPage = assertDoesNotThrow(() -> adegaController.list(null).getBody());

        log.info(adegaPage.toList().toString());
        verify(defaultUtil).formatLocalDateTimeToDatabaseStyle(ArgumentMatchers.any());
        assertNotNull(adegaPage);
        assertFalse(adegaPage.toList().isEmpty());
        assertEquals(3, adegaPage.stream().toList().size());
        assertEquals(createAdegaDeCasa().getId(), adegaPage.toList().get(0).getId());
        assertEquals(createAdegaDaPraia().getCapacidade(), adegaPage.toList().get(1).getCapacidade());
        assertEquals(createAdegaDoEscritorio().getNome(), adegaPage.toList().get(2).getNome());
    }

    @Test
    @DisplayName("Ao passar um id, deve retornar uma adega")
    void testFindById(){
        BDDMockito.when(adegaService.findByIdOrThrowBadRequestException(1L)).thenReturn(createAdegaDeCasa());

        Adega response = assertDoesNotThrow(() -> adegaController.findById(1L).getBody());
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Adega de casa", response.getNome());
        assertEquals(20, response.getCapacidade());
    }

    @Test
    @DisplayName("Ao passar um id que não tem no banco de dados, deve retornar uma exceção")
    void testFindByIdThrowBadRequestException(){
        BDDMockito.when(adegaService.findByIdOrThrowBadRequestException(4L)).thenThrow(new BadRequestException(TranslationConstants.ADEGA_NAO_ENCONTRADA));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> adegaController.findById(4L).getBody());
        assertEquals(TranslationConstants.ADEGA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar uma adega")
    void testSave(){
        Adega adegaDeCasa = createAdegaDeCasa();
        AdegaDTO dto = new AdegaDTO();
        dto.setId(adegaDeCasa.getId());
        dto.setNome(adegaDeCasa.getNome());
        dto.setCapacidade(adegaDeCasa.getCapacidade());

        BDDMockito.when(adegaService.save(ArgumentMatchers.any(AdegaDTO.class))).thenReturn(adegaDeCasa);
        Adega adegaCriada = assertDoesNotThrow(() -> adegaController.save(dto).getBody());
        assertNotNull(adegaCriada);
        assertEquals(adegaDeCasa.getId(), adegaCriada.getId());
        assertEquals(adegaDeCasa.getNome(), adegaCriada.getNome());
        assertEquals(adegaDeCasa.getCapacidade(), adegaCriada.getCapacidade());
    }

    @Test
    @DisplayName("Deve deletar uma adega do banco ao passar um id, caso ela exista")
    void testDelete(){
        Adega adegaDeCasa = createAdegaDeCasa();
        AdegaDTO dto = new AdegaDTO();
        dto.setId(adegaDeCasa.getId());
        dto.setNome(adegaDeCasa.getNome());
        BDDMockito.doNothing().when(adegaService).delete(adegaDeCasa.getId());

        ResponseEntity<Void> entity = assertDoesNotThrow(() -> adegaController.delete(adegaDeCasa.getId()));
        assertNotNull(entity);
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("Deve alterar os dados de uma adega do banco ao passar um id, caso ela exista")
    void testReplace(){
        AdegaDTO dto = new AdegaDTO();
        dto.setId(4L);
        dto.setNome("Nova Adega");
        BDDMockito.doNothing().when(adegaService).replace(ArgumentMatchers.any(AdegaDTO.class));

        ResponseEntity<Adega> adegaReplaced = assertDoesNotThrow(() -> adegaController.replace(dto));
        assertNotNull(adegaReplaced);
        assertEquals(HttpStatus.NO_CONTENT, adegaReplaced.getStatusCode());
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