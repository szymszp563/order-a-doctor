package pl.polsl.orderadoctor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.orderadoctor.model.MedicalProduct;

public interface MedicalProductRepository extends JpaRepository<MedicalProduct,Long> {
}
