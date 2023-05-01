package adega.de.vinhos.adegadevinhos.client;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        //===============GET=====================
        //Faço o mapeamento para um serviço externo (usando o próprio serviço neste caso apenas como exemplo)
        ResponseEntity<Vinho> entity = new RestTemplate().getForEntity("http://localhost:8080/vinhos/2", Vinho.class);
        log.info(entity);

        Vinho obj = new RestTemplate().getForObject("http://localhost:8080/vinhos/3", Vinho.class);
        log.info(obj);

        //Não usar
        Vinho[] vinhos = new RestTemplate().getForObject("http://localhost:8080/vinhos/all", Vinho[].class);
        log.info(Arrays.toString(vinhos));

        ResponseEntity<List<Vinho>> exchange = new RestTemplate().exchange(
                "http://localhost:8080/vinhos/all",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Vinho>>() {
        });
        log.info(exchange.getBody());

        //===============POST=====================
        Vinho vinhoLicorosoForEntity = Vinho.builder().tipo("Licoroso for Entity").build();
        ResponseEntity<Vinho> vinhoLicorosoForEntitySaved = new RestTemplate().postForEntity("http://localhost:8080/vinhos/", vinhoLicorosoForEntity, Vinho.class);
        log.info("Vinho for entity salvo {}", vinhoLicorosoForEntitySaved);

        Vinho vinhoLicorosoExchange = Vinho.builder().tipo("Licoroso Exchange").build();
        ResponseEntity<Vinho> vinhosLicorososExchangeSaved = new RestTemplate().exchange("http://localhost:8080/vinhos/",
                HttpMethod.POST,
                new HttpEntity<>(vinhoLicorosoExchange, createJsonHeader()),
                Vinho.class);
        log.info("Vinho exchange salvo {}", vinhosLicorososExchangeSaved);

        //===============PUT=====================
        Vinho vinhoToBeUpdated = vinhosLicorososExchangeSaved.getBody();
        vinhoToBeUpdated.setTipo("Licoroso Changed");

        ResponseEntity<Void> vinhosLicorososExchangeUpdated = new RestTemplate().exchange("http://localhost:8080/vinhos/",
                HttpMethod.PUT,
                new HttpEntity<>(vinhoToBeUpdated, createJsonHeader()),
                Void.class);
        log.info("Vinho exchange alterado {}", vinhosLicorososExchangeUpdated);

        //===============DELETE=====================
        ResponseEntity<Void> vinhosLicorososExchangeDeleted = new RestTemplate().exchange("http://localhost:8080/vinhos/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                vinhoToBeUpdated.getId());
        log.info("Vinho exchange deletado {}", vinhosLicorososExchangeDeleted);

    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
