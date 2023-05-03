package adega.de.vinhos.adegadevinhos.integration;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.dto.VinhoDTO;
import adega.de.vinhos.adegadevinhos.repository.VinhoRepository;
import adega.de.vinhos.adegadevinhos.wrapper.PageableResponse;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VinhoControllerIt {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private VinhoRepository vinhoRepository;
    @LocalServerPort
    private int port;

    @Test
    void testListDeveRetornarUmaListaDeVinhos(){
        Vinho vinhoSaved = vinhoRepository.save(createVinhoBranco());
        String expectedTipo = vinhoSaved.getTipo();

        PageableResponse<Vinho> vinhoPage = testRestTemplate.exchange("/vinhos", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Vinho>>() {
        }).getBody();

        assertNotNull(vinhoPage);
        assertFalse(vinhoPage.toList().isEmpty());
        assertEquals(1, vinhoPage.toList().size());
        assertEquals(expectedTipo, vinhoPage.toList().get(0).getTipo());
    }

    @Test
    void testListAllDeveRetornarTodosOsVinhos(){
        Vinho vinhoSaved = vinhoRepository.save(createVinhoBranco());
        String expectedTipo = vinhoSaved.getTipo();

        List<Vinho> vinhos = testRestTemplate.exchange("/vinhos/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Vinho>>() {
        }).getBody();

        assertNotNull(vinhos);
        assertFalse(vinhos.isEmpty());
        assertEquals(1, vinhos.size());
        assertEquals(expectedTipo, vinhos.get(0).getTipo());
    }

    @Test
    void testFindByIdDeveRetornarUmVinhoAoPassarOId(){
        Vinho vinhoSaved = vinhoRepository.save(createVinhoBranco());
        Long expectedId = vinhoSaved.getId();

        Vinho vinho = testRestTemplate.getForObject("/vinhos/{id}", Vinho.class, expectedId);

        assertNotNull(vinho);
        assertEquals(vinhoSaved.getId(), vinho.getId());
        assertEquals(vinhoSaved.getTipo(), vinho.getTipo());
    }

    @Test
    void testFindByTipoDeveRetornarUmaListaDeVinhosAoPassarOTipo(){
        Vinho vinhoSaved = vinhoRepository.save(createVinhoBranco());
        String expectedTipo = vinhoSaved.getTipo();
        String url = String.format("/vinhos/tipo?tipo=%s", expectedTipo);

        List<Vinho> vinhos = testRestTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Vinho>>() {
        }).getBody();

        assertNotNull(vinhos);
        assertFalse(vinhos.isEmpty());
        assertEquals(1, vinhos.size());
        assertEquals(vinhoSaved.getId(), vinhos.get(0).getId());
        assertEquals(vinhoSaved.getTipo(), vinhos.get(0).getTipo());
    }

    @Test
    void testFindByTipoDeveRetornarUmaListaVazia(){
        List<Vinho> vinhos = testRestTemplate.exchange("/vinhos/tipo?tipo=", HttpMethod.GET, null, new ParameterizedTypeReference<List<Vinho>>() {
        }).getBody();

        assertNotNull(vinhos);
        assertTrue(vinhos.isEmpty());
    }

    @Test
    void testSaveDeveSalvarOVinhoCriado(){
        Vinho vinhoBranco = createVinhoBranco();
        VinhoDTO dto = new VinhoDTO();
        dto.setId(vinhoBranco.getId());
        dto.setTipo(vinhoBranco.getTipo());

        ResponseEntity<Vinho> vinhoResponseEntity = testRestTemplate.postForEntity("/vinhos", dto, Vinho.class);

        assertNotNull(vinhoResponseEntity);
        assertEquals(HttpStatus.CREATED, vinhoResponseEntity.getStatusCode());
        assertNotNull(vinhoResponseEntity.getBody());
        assertNotNull(vinhoResponseEntity.getBody().getId());
    }

    @Test
    void testReplaceDeveAlterarERetornarNoContent(){
        Vinho vinhoSaved = vinhoRepository.save(createVinhoBranco());
        vinhoSaved.setTipo("Rose");

        ResponseEntity<Void> vinhoExchanged = testRestTemplate.exchange("/vinhos", HttpMethod.PUT, new HttpEntity<>(vinhoSaved), Void.class);

        assertNotNull(vinhoExchanged);
        assertEquals(HttpStatus.NO_CONTENT, vinhoExchanged.getStatusCode());
    }

    @Test
    void testDeleteDeveDeletarERetornarNoContent(){
        Vinho vinhoSaved = vinhoRepository.save(createVinhoBranco());

        ResponseEntity<Void> vinhoDeleted = testRestTemplate.exchange("/vinhos/{id}", HttpMethod.DELETE, null, Void.class, vinhoSaved.getId());

        assertNotNull(vinhoDeleted);
        assertEquals(HttpStatus.NO_CONTENT, vinhoDeleted.getStatusCode());
    }

    private Vinho createVinhoBranco() {
        return Vinho.builder().id(1L).tipo("Branco").build();
    }


}