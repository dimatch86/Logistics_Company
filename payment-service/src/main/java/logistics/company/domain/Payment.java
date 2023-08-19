package logistics.company.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment(Double amount, Long orderId, LocalDateTime creationTime, PaymentStatus status) {
        this.amount = amount;
        this.orderId = orderId;
        this.creationTime = creationTime;
        this.status = status;
    }
}
