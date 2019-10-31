package pl.polsl.orderadoctor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;

    @Enumerated(value = EnumType.STRING)
    private VisitState visitState;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "visits_medical_products", joinColumns = @JoinColumn(name = "visit_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_product_id"))
    private List<MedicalProduct> medicalProducts = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addMedicalProduct(MedicalProduct medicalProduct) {
        if(this.medicalProducts==null){
            this.medicalProducts = new LinkedList<>();
        }
        this.medicalProducts.add(medicalProduct);
        if (dateTo == null) {
            dateTo = dateFrom;
        }
        this.dateTo = dateFrom.plus(medicalProduct.getDurationTime());
    }

}
