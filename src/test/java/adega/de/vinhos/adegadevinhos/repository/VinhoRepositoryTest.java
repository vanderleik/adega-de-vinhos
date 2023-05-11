package adega.de.vinhos.adegadevinhos.repository;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.util.TranslationConstants;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VinhoRepositoryTest {
    @Autowired
    private VinhoRepository vinhoRepository;

    private Vinho vinhoToBeSaved;

    @BeforeEach
    void setUp(){
        vinhoToBeSaved = createVinho();

    }
    @Test
    void testSaveDevePersistirComSucesso(){
        Vinho vinhoSaved = assertDoesNotThrow(() -> vinhoRepository.save(vinhoToBeSaved));
        assertNotNull(vinhoSaved);
        assertNotNull(vinhoSaved.getId());
        assertNotNull(vinhoSaved.getTipo());
        assertEquals(vinhoToBeSaved.getTipo(), vinhoSaved.getTipo());
    }

    @Test
    void testPutDeveAtualizarComSucesso(){
        Vinho vinhoToBeUpdated = assertDoesNotThrow(() -> vinhoRepository.save(vinhoToBeSaved));
        assertNotNull(vinhoToBeUpdated);

        vinhoToBeUpdated.setTipo("Tinto");
        Vinho vinhoUpdated = assertDoesNotThrow(() -> vinhoRepository.save(vinhoToBeUpdated));

        assertNotNull(vinhoUpdated);
        assertNotNull(vinhoUpdated.getId());
        assertNotNull(vinhoUpdated.getTipo());
        assertEquals(vinhoToBeUpdated.getTipo(), vinhoUpdated.getTipo());
    }

    @Test
    void testDeleteDeveDeletarComSucesso(){
        Vinho vinhoToBeRemoved = assertDoesNotThrow(() -> vinhoRepository.save(vinhoToBeSaved));
        assertNotNull(vinhoToBeRemoved);

        assertDoesNotThrow(() -> vinhoRepository.delete(vinhoToBeRemoved));
        Optional<Vinho> optionalVinho = vinhoRepository.findById(vinhoToBeRemoved.getId());

        assertTrue(optionalVinho.isEmpty());
    }

    @Test
    void testFindByTipoDeveRetornarUmaListaNaoVazia(){
        Vinho vinhoSaved = assertDoesNotThrow(() -> vinhoRepository.save(vinhoToBeSaved));
        assertNotNull(vinhoSaved);

        List<Vinho> vinhosRetorned = assertDoesNotThrow(() -> vinhoRepository.findByTipo(vinhoSaved.getTipo()));
        assertFalse(vinhosRetorned.isEmpty());
        assertNotNull(vinhosRetorned.get(0));
        assertEquals(vinhoSaved.getTipo(), vinhosRetorned.get(0).getTipo());
    }

    @Test
    void testFindByTipoDeveRetornarUmaListaVazia(){
        List<Vinho> vinhosRetorned = assertDoesNotThrow(() -> vinhoRepository.findByTipo("Tinto"));

        assertTrue(vinhosRetorned.isEmpty());
    }

    @Test
    void testSaveDeveLancarExcecao(){
        vinhoToBeSaved.setTipo("");
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> vinhoRepository.save(vinhoToBeSaved))
                .withMessageContaining(TranslationConstants.TIPO_NOT_EMPTY_OR_NULL);
    }

    private Vinho createVinho(){
        return Vinho.builder().tipo("Branco").build();
    }

}