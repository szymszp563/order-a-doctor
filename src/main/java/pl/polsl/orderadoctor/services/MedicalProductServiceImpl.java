package pl.polsl.orderadoctor.services;

import org.springframework.stereotype.Service;
import pl.polsl.orderadoctor.model.MedicalProduct;
import pl.polsl.orderadoctor.repositories.MedicalProductRepository;

import java.util.List;

@Service
public class MedicalProductServiceImpl implements MedicalProductService {

    private final MedicalProductRepository medicalProductRepository;

    public MedicalProductServiceImpl(MedicalProductRepository medicalProductRepository) {
        this.medicalProductRepository = medicalProductRepository;
    }

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
