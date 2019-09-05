package pl.polsl.orderadoctor.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.MedicalProduct;
import pl.polsl.orderadoctor.repositories.MedicalProductRepository;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MedicalProductServiceImpl implements MedicalProductService {

    private final MedicalProductRepository medicalProductRepository;

    @Override
    public void save(MedicalProduct medicalProduct) {
        medicalProductRepository.save(medicalProduct);
    }

    @Override
    public void saveAll(List<MedicalProduct> medicalProducts) {
        medicalProductRepository.saveAll(medicalProducts);

    }

    @Override
    public MedicalProduct findById(Long id) {
        MedicalProduct medicalProduct = medicalProductRepository.findById(id).get();
        return medicalProduct;
    }
}
