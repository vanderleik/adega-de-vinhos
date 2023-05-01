package adega.de.vinhos.adegadevinhos.client;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {
        //Faço o mapeamento para um serviço externo (usando o próprio serviço neste caso apenas como exemplo)
        ResponseEntity<Vinho> entity = new RestTemplate().getForEntity("http://localhost:8080/vinhos/2", Vinho.class);
        log.info(entity);

        Vinho obj = new RestTemplate().getForObject("http://localhost:8080/vinhos/3", Vinho.class);
        log.info(obj);
    }
}
