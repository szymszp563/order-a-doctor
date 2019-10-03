package pl.polsl.orderadoctor.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.polsl.orderadoctor.model.AccountType;
import pl.polsl.orderadoctor.model.DegreeType;
import pl.polsl.orderadoctor.model.Doctor;
import pl.polsl.orderadoctor.model.Grade;
import pl.polsl.orderadoctor.model.MedicalProduct;
import pl.polsl.orderadoctor.model.Speciality;
import pl.polsl.orderadoctor.model.User;
import pl.polsl.orderadoctor.model.Visit;
import pl.polsl.orderadoctor.repositories.DoctorRepository;
import pl.polsl.orderadoctor.repositories.GradeRepository;
import pl.polsl.orderadoctor.repositories.MedicalProductRepository;
import pl.polsl.orderadoctor.repositories.SpecialityRepository;
import pl.polsl.orderadoctor.repositories.UserRepository;
import pl.polsl.orderadoctor.repositories.VisitRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

@Component
public class OrderADoctorBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final DoctorRepository doctorRepository;
    private final MedicalProductRepository medicalProductRepository;
    private final SpecialityRepository specialityRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final GradeRepository gradeRepository;

    public OrderADoctorBootstrap(DoctorRepository doctorRepository, MedicalProductRepository medicalProductRepository, SpecialityRepository specialityRepository, UserRepository userRepository, VisitRepository visitRepository, GradeRepository gradeRepository) {
        this.doctorRepository = doctorRepository;
        this.medicalProductRepository = medicalProductRepository;
        this.specialityRepository = specialityRepository;
        this.userRepository = userRepository;
        this.visitRepository = visitRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Speciality surgery = Speciality.builder().description("Surgery").build();
        Speciality internist = Speciality.builder().description("Internist").build();
        Speciality oncologist = Speciality.builder().description("Oncologist").build();
        Speciality orthopaedist = Speciality.builder().description("Orthopaedist").build();
        Speciality ophthalmologist = Speciality.builder().description("Ophthalmologist").build();
        Speciality dermatologist = Speciality.builder().description("Dermatologist").build();
        specialityRepository.saveAll(Arrays.asList(surgery, internist, oncologist, orthopaedist, ophthalmologist, dermatologist));

        MedicalProduct armRemoval = MedicalProduct.builder()
                .name("Arm Removal").duration("80").price(600.00).build();
        MedicalProduct legRemoval = MedicalProduct.builder()
                .name("Leg Removal").duration("130").price(1600.00).build();
        MedicalProduct childbirth = MedicalProduct.builder()
                .name("Childbirth").duration("240").price(4900.00).build();
        MedicalProduct oncConsultation = MedicalProduct.builder()
                .name("Oncological Consultation").duration("30").price(100.00).build();
        MedicalProduct consultation = MedicalProduct.builder()
                .name("Consultation").duration("30").price(100.00).build();
        MedicalProduct laserTreatment = MedicalProduct.builder()
                .name("Laser Treatment").duration("45").price(200.00).build();

        Grade g1 = Grade.builder().grade((byte) 5).comment("Best doctor ever!").build();
        Grade g2 = Grade.builder().grade((byte) 4).comment("Good care, but expensive.").build();
        Grade g3 = Grade.builder().grade((byte) 3).comment("I was cured, but not a pleasant visit.").build();
        Grade g4 = Grade.builder().grade((byte) 2).comment("I had to change doctor").build();
        Grade g5 = Grade.builder().grade((byte) 1).comment("Worst doctor ever...").build();
        Grade g6 = Grade.builder().grade((byte) 5).comment("Extra!").build();

        Doctor d1 = Doctor.builder().externalId("1").firstName("John W.").lastName("Thackery").city("Katowice").degree(DegreeType.PhD)
                .email("J.W.Thack@gmail.com").street("Raciborska 6/65").workingFrom("8:00").workingTo("16:00")
                .about("Best Medical Doctor of 20th Century").specialities(Arrays.asList(surgery, internist, orthopaedist))
                .visits(new LinkedList<>()).medicalProducts(new LinkedList<>()).grades(new HashSet<>()).averageGrade(0D)
                .accountType(AccountType.GOOGLE).build();
        d1.addMedicalProduct(armRemoval);
        d1.addMedicalProduct(legRemoval);
        d1.addGrade(g1);
        d1.addGrade(g2);

        Doctor d2 = Doctor.builder().externalId("2").firstName("Kevin").lastName("Garvey").city("Katowice").degree(DegreeType.MD)
                .email("DrGarvey@gmail.com").street("MPK 8").workingFrom("8:00").workingTo("16:00")
                .about("Where is his mind").specialities(Arrays.asList(internist, oncologist))
                .visits(new LinkedList<>()).medicalProducts(new LinkedList<>()).grades(new HashSet<>()).averageGrade(0D)
                .accountType(AccountType.GOOGLE).build();
        d2.addMedicalProduct(childbirth);
        d2.addMedicalProduct(oncConsultation);
        d2.addGrade(g3);
        d2.addGrade(g4);

        Doctor d3 = Doctor.builder().externalId("3").firstName("Algernon").lastName("Edwards").city("Opole").degree(DegreeType.PhD)
                .email("Algernon@facebook.com").street("Koszalinska 32/64").workingFrom("8:00").workingTo("16:00")
                .about("Rich mind").specialities(Arrays.asList(ophthalmologist, dermatologist, internist))
                .visits(new LinkedList<>()).medicalProducts(new LinkedList<>()).grades(new HashSet<>()).averageGrade(0D)
                .accountType(AccountType.FACEBOOK).build();
        d3.addMedicalProduct(consultation);
        d3.addMedicalProduct(laserTreatment);
        d3.addGrade(g5);
        d3.addGrade(g6);

        doctorRepository.saveAll(Arrays.asList(d1, d2, d3));

        User u1 = User.builder().externalId("4").firstName("Mike").lastName("Gige").city("Opole")
                .email("Mike@facebook.com").visits(new LinkedList<>()).accountType(AccountType.FACEBOOK)
                .grades(new HashSet<>()).build();
        User u2 = User.builder().externalId("5").firstName("Mark").lastName("Maven").city("Opole")
                .email("Maven@gmail.com").visits(new LinkedList<>()).accountType(AccountType.GOOGLE)
                .grades(new HashSet<>()).build();
        User u3 = User.builder().externalId("6").firstName("Steve").lastName("Buscemi").city("Katowice")
                .email("SteveB@gmail.com").visits(new LinkedList<>()).accountType(AccountType.GOOGLE)
                .grades(new HashSet<>()).build();
        User u4 = User.builder().externalId("7").firstName("Clive").lastName("Owen").city("Katowice")
                .email("Owen@facebook.com").visits(new LinkedList<>()).accountType(AccountType.FACEBOOK)
                .grades(new HashSet<>()).build();
        User u5 = User.builder().externalId("8").firstName("Simon").lastName("Simons").city("Katowice")
                .email("Simons@gmail.com").visits(new LinkedList<>()).accountType(AccountType.GOOGLE)
                .grades(new HashSet<>()).build();
        User u6 = User.builder().externalId("9").firstName("Kate").lastName("Moss").city("Katowice")
                .email("K.Moss@gmail.com").visits(new LinkedList<>()).accountType(AccountType.GOOGLE)
                .grades(new HashSet<>()).build();

        u1.addGrade(g1);
        u2.addGrade(g2);
        u3.addGrade(g3);
        u4.addGrade(g4);
        u5.addGrade(g5);
        u6.addGrade(g6);

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6));

        gradeRepository.saveAll(Arrays.asList(g1, g2, g3, g4, g5, g6));

        Visit v1 = Visit.builder()
                .dateFrom(LocalDateTime.now().plusDays(10)).description("Visit One").user(u1).doctor(d1)
                .medicalProducts(new LinkedList<>()).build();
        v1.addMedicalProduct(d1.getMedicalProducts().get(0));
        v1.addMedicalProduct(d1.getMedicalProducts().get(0));

        Visit v2 = Visit.builder()
                .dateFrom(LocalDateTime.now().plusDays(11)).description("Visit Two").user(u2).doctor(d1)
                .medicalProducts(new LinkedList<>()).build();
        v2.addMedicalProduct(d1.getMedicalProducts().get(0));

        Visit v3 = Visit.builder()
                .dateFrom(LocalDateTime.now().plusDays(10)).description("Visit Three").user(u3).doctor(d2)
                .medicalProducts(new LinkedList<>()).build();
        v3.addMedicalProduct(d2.getMedicalProducts().get(0));
        v3.addMedicalProduct(d2.getMedicalProducts().get(1));

        Visit v4 = Visit.builder()
                .dateFrom(LocalDateTime.now().plusDays(10)).description("Visit Four").user(u4).doctor(d2)
                .medicalProducts(new LinkedList<>()).build();
        v4.addMedicalProduct(d2.getMedicalProducts().get(1));

        Visit v5 = Visit.builder()
                .dateFrom(LocalDateTime.now().plusDays(10)).description("Visit Five").user(u5).doctor(d3)
                .medicalProducts(new LinkedList<>()).build();
        v5.addMedicalProduct(d3.getMedicalProducts().get(0));
        v5.addMedicalProduct(d3.getMedicalProducts().get(1));

        Visit v6 = Visit.builder()
                .dateFrom(LocalDateTime.now().plusDays(10)).description("Visit Six").user(u6).doctor(d3)
                .medicalProducts(new LinkedList<>()).build();
        v6.addMedicalProduct(d3.getMedicalProducts().get(0));

        u1.addVisit(v1);
        u2.addVisit(v2);
        u3.addVisit(v3);
        u4.addVisit(v4);
        u5.addVisit(v5);
        u6.addVisit(v6);

        d1.addVisit(v1);
        d1.addVisit(v2);
        d2.addVisit(v3);
        d2.addVisit(v4);
        d3.addVisit(v5);
        d3.addVisit(v6);

        visitRepository.saveAll(Arrays.asList(v1, v2, v3, v4, v5, v6));


    }
}
