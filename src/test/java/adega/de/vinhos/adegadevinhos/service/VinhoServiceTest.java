package adega.de.vinhos.adegadevinhos.service;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import adega.de.vinhos.adegadevinhos.exception.BadRequestException;
import adega.de.vinhos.adegadevinhos.repository.VinhoRepository;
import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
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

@ExtendWith(MockitoExtension.class)
class VinhoServiceTest {

    @InjectMocks
    private VinhoService vinhoService;

    @Mock
    private VinhoRepository vinhoRepository;
    @Mock
    private Pageable pageable;

    @Test
    void testListAllDeveRetornarUmaLista() {
        PageImpl<Vinho> vinhoPage = new PageImpl<>(List.of(createVinhoBranco()));
        Mockito.when(vinhoRepository.findAll(pageable)).thenReturn(vinhoPage);

        Page<Vinho> vinhosPageable = assertDoesNotThrow(() -> vinhoService.listAll(pageable));
        assertNotNull(vinhosPageable);
        assertFalse(vinhosPageable.isEmpty());
        assertEquals(vinhoPage.toList().get(0).getId(), vinhosPageable.toList().get(0).getId());
        assertEquals(vinhoPage.toList().get(0).getTipo(), vinhosPageable.toList().get(0).getTipo());
    }

    @Test
    void testFindByIdOrThrowBadRequestExceptionDeveRetornarUmVinho() {
        Mockito.when(vinhoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(createVinhoBranco()));

        Vinho vinho = assertDoesNotThrow(() -> vinhoService.findByIdOrThrowBadRequestException(createVinhoBranco().getId()));
        assertNotNull(vinho);
        assertEquals(createVinhoBranco().getId(), vinho.getId());
        assertEquals(createVinhoBranco().getTipo(), vinho.getTipo());
    }

    @Test
    void testFindByIdOrThrowBadRequestExceptionDeveLancarExcecao() {
        Mockito.when(vinhoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> vinhoService.findByIdOrThrowBadRequestException(createVinhoBranco().getId()));
        assertEquals(TranslationConstants.VINHO_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    void testSaveDeveSalvarOVinho() {
        Vinho vinhoBranco = createVinhoBranco();
        VinhoDTO dto = new VinhoDTO();
        dto.setId(vinhoBranco.getId());
        dto.setTipo(vinhoBranco.getTipo());
        Mockito.when(vinhoRepository.save(Mockito.any())).thenReturn(vinhoBranco);

        Vinho vinhoCriado = assertDoesNotThrow(() -> vinhoService.save(dto));
        assertNotNull(vinhoCriado);
        assertEquals(vinhoBranco.getId(), vinhoCriado.getId());
        assertEquals(vinhoBranco.getTipo(), vinhoCriado.getTipo());
    }

    @Test
    void testDelete() {
        Vinho vinhoBranco = createVinhoBranco();
        Mockito.when(vinhoRepository.findById(vinhoBranco.getId())).thenReturn(Optional.of(vinhoBranco));

        assertDoesNotThrow(() -> vinhoService.delete(vinhoBranco.getId()));
        Mockito.verify(vinhoRepository).findById(vinhoBranco.getId());
    }

    @Test
    void testReplace() {
        Vinho vinhoBranco = createVinhoBranco();
        VinhoDTO vinhoBrancoDto = new VinhoDTO();
        vinhoBrancoDto.setId(vinhoBranco.getId());
        vinhoBrancoDto.setTipo(vinhoBranco.getTipo());

        Mockito.when(vinhoRepository.findById(vinhoBrancoDto.getId())).thenReturn(Optional.of(vinhoBranco));
        assertDoesNotThrow(() -> vinhoService.replace(vinhoBrancoDto));
        Mockito.verify(vinhoRepository).save(vinhoBranco);
    }

    @Test
    void testFindByTipo() {
        Mockito.when(vinhoRepository.findByTipo(createVinhoBranco().getTipo())).thenReturn(List.of(createVinhoBranco()));

        List<Vinho> vinhos = assertDoesNotThrow(() -> vinhoService.findByTipo(createVinhoBranco().getTipo()));
        assertNotNull(vinhos);
        assertFalse(vinhos.isEmpty());
        assertEquals(createVinhoBranco().getId(), vinhos.get(0).getId());
        assertEquals(createVinhoBranco().getTipo(), vinhos.get(0).getTipo());
    }

    @Test
    void testListAllNonPageable() {
        Mockito.when(vinhoRepository.findAll()).thenReturn(List.of(createVinhoBranco()));

        List<Vinho> vinhos = assertDoesNotThrow(() -> vinhoService.listAllNonPageable());
        assertNotNull(vinhos);
        assertFalse(vinhos.isEmpty());
        assertEquals(createVinhoBranco().getId(), vinhos.get(0).getId());
        assertEquals(createVinhoBranco().getTipo(), vinhos.get(0).getTipo());
    }

    private Vinho createVinhoBranco() {
        return Vinho.builder().id(1L).tipo("Branco").build();
    }

}