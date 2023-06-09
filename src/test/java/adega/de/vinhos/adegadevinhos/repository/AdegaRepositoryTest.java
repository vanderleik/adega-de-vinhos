package adega.de.vinhos.adegadevinhos.repository;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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
        assertEquals(adegaDeCasaSaved.getId(), list.get(0).getId());
        assertEquals(adegaDoEscritorioSaved.getNome(), list.get(1).getNome());
        assertEquals(adegaDaPraiaSaved.getCapacidade(), list.get(2).getCapacidade());
    }

    @Test
    @DisplayName("Deve retornar uma adega quando for passado um id")
    void testFindById(){
        Optional<Adega> adega = assertDoesNotThrow(() -> adegaRepository.findById(adegaDeCasaSaved.getId()));

        assertNotNull(adega);
        assertEquals(adegaDeCasaSaved.getId(), adega.get().getId());
        assertEquals(adegaDeCasaSaved.getNome(), adega.get().getNome());
        assertEquals(adegaDeCasaSaved.getCapacidade(), adega.get().getCapacidade());
    }

    @Test
    @DisplayName("Deve deletar uma adega do banco ao passar um id, caso ela exista")
    void testDelete(){
        assertDoesNotThrow(() -> adegaRepository.delete(adegaDaPraiaSaved));

        Optional<Adega> adega = adegaRepository.findById(adegaDaPraiaSaved.getId());
        assertTrue(adega.isEmpty());
    }

    @Test
    @DisplayName("Deve alterar os dados de uma adega do banco ao passar um id, caso ela exista")
    void testReplace(){
        adegaDaPraiaSaved.setNome("Nome da Adega alterado");
        Adega adegaUpdated = assertDoesNotThrow(() -> adegaRepository.save(adegaDaPraiaSaved));

        assertNotNull(adegaUpdated);
        assertNotNull(adegaUpdated.getId());
        assertNotNull(adegaUpdated.getNome());
        assertEquals(adegaDaPraiaSaved.getNome(), adegaUpdated.getNome());
    }

    @Test
    @DisplayName("Deve retornar uma lista não vazia")
    void testFindByNome(){
        String nome = "Adega do escritório";
        List<Adega> adegaReturned = assertDoesNotThrow(() -> adegaRepository.findByNome(nome));
        assertFalse(adegaReturned.isEmpty());
        assertNotNull(adegaReturned.get(0));
        assertEquals(nome, adegaReturned.get(0).getNome());
    }

    @Test
    @DisplayName("Deve retornar uma lista não vazia")
    void testFindByNomeInexistente(){
        String nome = "Adega Inexistente";
        List<Adega> adegaReturned = assertDoesNotThrow(() -> adegaRepository.findByNome(nome));

        assertTrue(adegaReturned.isEmpty());
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