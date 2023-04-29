package adega.de.vinhos.adegadevinhos.repository;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VinhoRepository extends JpaRepository<Vinho, Long> {
    List<Vinho> findByTipo(String tipo);

}
