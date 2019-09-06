package pl.polsl.orderadoctor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    private String workingFrom;

    private String workingTo;

    @Lob
    private Byte[] image;

    @Lob
    private String about;

    private Double averageGrade;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    private List<Visit> visits = new LinkedList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_specialties", joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<Speciality> specialities = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<MedicalProduct> medicalProducts = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Set<Grade> grades = new HashSet<>();

    public void addVisit(Visit visit) {
        this.visits.add(visit);
        visit.setDoctor(this);
    }

    public void addMedicalProduct(MedicalProduct medicalProduct) {
        this.medicalProducts.add(medicalProduct);
        medicalProduct.setDoctor(this);
    }

    public void addGrade(Grade grade){
        this.grades.add(grade);
        calculateAverageGrade();
    }

    public Double calculateAverageGrade() {
        Double average = 0D;
        if (!this.grades.isEmpty()) {
            Integer sum = 0;
            for (Grade grade : grades) {
                sum += grade.getGrade();
            }
            average = Double.valueOf(sum) / (double) this.grades.size();
        }
        this.averageGrade = average;
        return average;
    }

    public int getIntGrade(){
        Double g = (averageGrade)*2 + 1;
        return (g.intValue())/2;
    }

    public int getTo5IntGrade(){
        Double g = Double.valueOf((5-getIntGrade())*2);
        return (g.intValue())/2;
    }

}
