package adega.de.vinhos.adegadevinhos.integration;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import adega.de.vinhos.adegadevinhos.dto.AdegaDTO;
import adega.de.vinhos.adegadevinhos.repository.AdegaRepository;
import adega.de.vinhos.adegadevinhos.wrapper.PageableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdegaControllerIt {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private AdegaRepository adegaRepository;
    @LocalServerPort
    private int port;

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
    void testList(){
        List<Adega> list = Arrays.asList(adegaDeCasaSaved, adegaDoEscritorioSaved, adegaDaPraiaSaved);

        PageableResponse<Adega> adegaPage = testRestTemplate.exchange("/adegas", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Adega>>() {
        }).getBody();

        assertNotNull(adegaPage);
        assertFalse(adegaPage.toList().isEmpty());
        assertEquals(3, adegaPage.toList().size());
        assertEquals(list.get(0).getId(), adegaPage.toList().get(0).getId());
        assertEquals(list.get(1).getId(), adegaPage.toList().get(1).getId());
        assertEquals(list.get(2).getId(), adegaPage.toList().get(2).getId());
    }

    @Test
    @DisplayName("Deve retornar todas as adegas")
    void testListAll(){
        List<Adega> list = Arrays.asList(adegaDeCasaSaved, adegaDoEscritorioSaved, adegaDaPraiaSaved);

        List<Adega> adegas = testRestTemplate.exchange("/adegas/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Adega>>() {
        }).getBody();

        assertNotNull(adegas);
        assertFalse(adegas.isEmpty());
        assertEquals(3, adegas.size());
        assertEquals(list.get(0).getId(), adegas.get(0).getId());
        assertEquals(list.get(1).getId(), adegas.get(1).getId());
        assertEquals(list.get(2).getId(), adegas.get(2).getId());
    }

    @Test
    @DisplayName("Deve retornar uma adega ao passar o id")
    void testFindById(){
        Long expectedId = adegaDeCasaSaved.getId();

        Adega adega = testRestTemplate.getForObject("/adegas/{id}", Adega.class, expectedId);
        assertNotNull(adega);
        assertEquals(expectedId, adega.getId());
        assertEquals(adegaDeCasaSaved.getNome(), adega.getNome());
        assertEquals(adegaDeCasaSaved.getCapacidade(), adega.getCapacidade());
    }

    @Test
    @DisplayName("Deve retornar uma lista de adegas ao passar o nome")
    void testFindByNome(){

        String expectedNome = adegaDeCasaSaved.getNome();
        String url = String.format("/adegas/nome?nome=%s", expectedNome);

        List<Adega> adegas = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Adega>>() {
        }).getBody();

        assertNotNull(adegas);
        assertFalse(adegas.isEmpty());
        assertEquals(adegaDeCasaSaved.getId(), adegas.get(0).getId());
        assertEquals(adegaDeCasaSaved.getNome(), adegas.get(0).getNome());
        assertEquals(adegaDeCasaSaved.getCapacidade(), adegas.get(0).getCapacidade());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia")
    void testFindByNomeInexistente(){
        List<Adega> adegas = testRestTemplate.exchange("/adegas/nome?nome=", HttpMethod.GET, null, new ParameterizedTypeReference<List<Adega>>() {
        }).getBody();

        assertNotNull(adegas);
        assertTrue(adegas.isEmpty());
    }

    @Test
    @DisplayName("Deve salvar a adega criada")
    void testSave(){
        Adega novaAdega = Adega.builder()
                .id(4L)
                .nome("Nova Adega")
                .capacidade(30)
                .build();
        AdegaDTO dto = new AdegaDTO();
        dto.setId(novaAdega.getId());
        dto.setNome(novaAdega.getNome());
        dto.setCapacidade(novaAdega.getCapacidade());

        ResponseEntity<Adega> adegaResponseEntity = testRestTemplate.postForEntity("/adegas", dto, Adega.class);
        assertNotNull(adegaResponseEntity);
        assertEquals(HttpStatus.CREATED, adegaResponseEntity.getStatusCode());
        assertNotNull(adegaResponseEntity.getBody());
        assertEquals(novaAdega.getId(), adegaResponseEntity.getBody().getId());
    }

    @Test
    @DisplayName("Deve alterar e retornar noContent")
    void testReplace(){
        adegaDeCasaSaved.setNome("Novo Nome");

        ResponseEntity<Void> adegaExchanged = testRestTemplate.exchange("/adegas", HttpMethod.PUT, new HttpEntity<>(adegaDeCasaSaved), Void.class);
        assertNotNull(adegaExchanged);
        assertEquals(HttpStatus.NO_CONTENT, adegaExchanged.getStatusCode());
    }

    @Test
    @DisplayName("Deve deletar e retornar noContent")
    void testDelete(){
        ResponseEntity<Void> adegaDeleted = testRestTemplate.exchange("/adegas/{id}", HttpMethod.DELETE, null, Void.class, adegaDaPraiaSaved.getId());
        assertNotNull(adegaDeleted);
        assertEquals(HttpStatus.NO_CONTENT, adegaDeleted.getStatusCode());
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
