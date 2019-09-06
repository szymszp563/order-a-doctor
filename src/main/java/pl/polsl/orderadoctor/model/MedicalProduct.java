package pl.polsl.orderadoctor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Duration;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MedicalProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private Duration duration;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public String getFormatedDuration() {
        long seconds = this.duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        return String.format(
                "%dh %02d min",
                absSeconds / 3600,
                (absSeconds % 3600) / 60);
    }
}
