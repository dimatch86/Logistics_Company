package logistics.company.domain.dto;

import lombok.Data;

@Data
public class OrderDto {
    private String description;
    private String departureAddress;
    private String destinationAddress;
    private Double cost;
    private Integer userId;
}
