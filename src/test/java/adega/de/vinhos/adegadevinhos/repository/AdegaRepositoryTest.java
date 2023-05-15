package adega.de.vinhos.adegadevinhos.repository;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Log4j2
class AdegaRepositoryTest {

    @Autowired
    private AdegaRepository adegaRepository;

    private Adega adegaDeCasaSaved;
    private Adega adegaDoEscritorioSaved;
    private Adega adegaDaPraiaSaved;

    @BeforeEach
    void setUp(){
        adegaDeCasaSaved = adegaRepository.save(createAdegaDeCasa());
        adegaDoEscritorioSaved = adegaRepository.save(createAdegaDoEscritorio());
        adegaDaPraiaSaved = adegaRepository.save(createAdegaDaPraia());
    }

    @Test
    @DisplayName("Deve retornar uma lista de adegas")
    void testListAll(){
        List<Adega> list = assertDoesNotThrow(() -> adegaRepository.findAll());
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
        assertEquals(createAdegaDeCasa().getId(), list.get(0).getId());
        assertEquals(createAdegaDoEscritorio().getNome(), list.get(1).getNome());
        assertEquals(createAdegaDaPraia().getCapacidade(), list.get(2).getCapacidade());
    }

    @Test
    @DisplayName("Deve retornar uma adega quando for passado um id")
    void testFindById(){
        Optional<Adega> adega = assertDoesNotThrow(() -> adegaRepository.findById(1L));

        assertNotNull(adega);
        assertEquals(1L, adega.get().getId());
        assertEquals("Adega de casa", adega.get().getNome());
        assertEquals(20, adega.get().getCapacidade());
    }

    @Test
    @DisplayName("Deve deletar uma adega do banco ao passar um id, caso ela exista")
    void testDelete(){
        assertDoesNotThrow(() -> adegaRepository.delete(adegaDaPraiaSaved));

        Optional<Adega> adega = adegaRepository.findById(adegaDaPraiaSaved.getId());
        assertTrue(adega.isEmpty());
    }


    //TO-DO
    //replace
    //findByNome
    //listAllNonPageable

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