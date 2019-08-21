package pl.polsl.orderadoctor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private AccountType accountType;

    private String externalId;

    private String firstName;

    private String lastName;

    private String city;

    private String email;

    private String street;

    private LocalDateTime workingHoursFrom;

    private LocalDateTime workingHoursTo;

    @Lob
    private Byte[] image;

    @Lob
    private String about;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Visit> visits = new LinkedList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_specialties", joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<Speciality> specialities = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<MedicalProduct> medicalProducts = new LinkedList<>();

    public void addVisit(Visit visit){
        this.visits.add(visit);
        visit.setDoctor(this);
    }

    public void addMedicalProduct(MedicalProduct medicalProduct){
        this.medicalProducts.add(medicalProduct);
        medicalProduct.setDoctor(this);
    }

}
