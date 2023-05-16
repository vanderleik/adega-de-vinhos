package adega.de.vinhos.adegadevinhos.repository;

import adega.de.vinhos.adegadevinhos.domain.Adega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdegaRepository extends JpaRepository<Adega, Long> {
    List<Adega> findByNome(String nome);
}
