package logistics.company.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "price")
    private Double price;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public Transaction(Long orderId, Double price, LocalDateTime creationTime, TransactionStatus status) {
        this.orderId = orderId;
        this.price = price;
        this.creationTime = creationTime;
        this.status = status;
    }

    public Transaction setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Transaction setPrice(double price) {
        this.price = price;
        return this;
    }
}
