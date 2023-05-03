package adega.de.vinhos.adegadevinhos.controller;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import adega.de.vinhos.adegadevinhos.service.VinhoService;
import adega.de.vinhos.adegadevinhos.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VinhoControllerTest {

    @InjectMocks
    private VinhoController vinhoController;

    @Mock
    private VinhoService vinhoService;
    @Mock
    private DateUtil defaultUtil;

    private PageImpl<Vinho> validVinhoPage;

    @BeforeEach
    void setUp(){
        validVinhoPage = new PageImpl<>(List.of(createValidVinhoBranco()));
        BDDMockito.when(vinhoService.listAll(ArgumentMatchers.any())).thenReturn(validVinhoPage);
    }

    @Test
    void testListDeveRetornarUmaListaDeVinhos(){
        Page<Vinho> vinhoPage = assertDoesNotThrow(() -> vinhoController.list(null).getBody());
        BDDMockito.verify(defaultUtil).formatLocalDateTimeToDatabaseStyle(ArgumentMatchers.any());
        assertNotNull(vinhoPage);
        assertFalse(vinhoPage.toList().isEmpty());
        assertEquals(1, vinhoPage.getSize());
        assertEquals(validVinhoPage.toList().get(0).getTipo(), vinhoPage.toList().get(0).getTipo());
    }

    @Test
    void testListAllDeveRetornarTodosOsVinhos(){
        List<Vinho> vinhos = new ArrayList<>();
        vinhos.add(createValidVinhoBranco());
        vinhos.add(createValidVinhoTinto());
        BDDMockito.when(vinhoService.listAllNonPageable()).thenReturn(vinhos);

        List<Vinho> listResponseEntity = assertDoesNotThrow(() -> vinhoController.listAll().getBody());
        BDDMockito.verify(defaultUtil).formatLocalDateTimeToDatabaseStyle(ArgumentMatchers.any());
        assertNotNull(listResponseEntity);
        assertFalse(listResponseEntity.isEmpty());
        assertEquals(2, listResponseEntity.size());
        assertEquals(vinhos.get(0).getId(), listResponseEntity.get(0).getId());
        assertEquals(vinhos.get(0).getTipo(), listResponseEntity.get(0).getTipo());
        assertEquals(vinhos.get(1).getId(), listResponseEntity.get(1).getId());
        assertEquals(vinhos.get(1).getTipo(), listResponseEntity.get(1).getTipo());
    }

    @Test
    void testFindByIdDeveRetornarUmVinhoAoPassarOId(){
        BDDMockito.when(vinhoService.findByIdOrThrowBadRequestException(1L)).thenReturn(createValidVinhoBranco());

        Vinho response = assertDoesNotThrow(() -> vinhoController.findById(1L).getBody());
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Branco", response.getTipo());
    }

    @Test
    void testFindByTipoDeveRetornarUmaListaDeVinhosAoPassarOTipo(){
        BDDMockito.when(vinhoService.findByTipo("Tinto")).thenReturn(List.of(createValidVinhoTinto()));
        List<Vinho> vinhos = assertDoesNotThrow(() -> vinhoController.findByTipo("Tinto").getBody());

        assertNotNull(vinhos);
        assertFalse(vinhos.isEmpty());
        assertEquals(1, vinhos.size());
        assertEquals(vinhos.get(0).getId(), vinhos.get(0).getId());
        assertEquals(vinhos.get(0).getTipo(), vinhos.get(0).getTipo());
    }

    @Test
    void testFindByTipoDeveRetornarUmaListaVazia(){
        BDDMockito.when(vinhoService.findByTipo("")).thenReturn(Collections.emptyList());
        List<Vinho> vinhos = assertDoesNotThrow(() -> vinhoController.findByTipo("").getBody());

        assertNotNull(vinhos);
        assertTrue(vinhos.isEmpty());
        assertEquals(0, vinhos.size());
    }

    @Test
    void testSaveDeveSalvarOVinhoCriado(){
        Vinho vinhoBranco = createValidVinhoBranco();
        VinhoDTO dto = new VinhoDTO();
        dto.setId(vinhoBranco.getId());
        dto.setTipo(vinhoBranco.getTipo());

        BDDMockito.when(vinhoService.save(ArgumentMatchers.any(VinhoDTO.class))).thenReturn(vinhoBranco);
        Vinho vinhoCriado = assertDoesNotThrow(() -> vinhoController.save(dto).getBody());
        assertNotNull(vinhoCriado);
        assertEquals(vinhoBranco.getId(), vinhoCriado.getId());
        assertEquals(vinhoBranco.getTipo(), vinhoCriado.getTipo());
    }

    @Test
    void testReplaceDeveAlterarERetornarNoContent(){
        Vinho vinhoSaved = createValidVinhoBranco();
        VinhoDTO dto = new VinhoDTO();
        dto.setId(4L);
        dto.setTipo("Rose");
        BDDMockito.doNothing().when(vinhoService).replace(ArgumentMatchers.any(VinhoDTO.class));

        ResponseEntity<Vinho> vinhoReplaced = assertDoesNotThrow(() -> vinhoController.replace(dto));
        assertNotNull(vinhoReplaced);
        assertEquals(HttpStatus.NO_CONTENT, vinhoReplaced.getStatusCode());
    }

    @Test
    void testDeleteDeveDeletarERetornarNoContent(){
        Vinho vinhoSaved = createValidVinhoBranco();
        BDDMockito.doNothing().when(vinhoService).delete(vinhoSaved.getId());

        ResponseEntity<Void> entity = assertDoesNotThrow(() -> vinhoController.delete(vinhoSaved.getId()));
        assertNotNull(entity);
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    private Vinho createValidVinhoBranco() {
        return Vinho.builder().id(1L).tipo("Branco").build();
    }

    private Vinho createValidVinhoTinto() {
        return Vinho.builder().id(2L).tipo("Tinto").build();
    }
}
