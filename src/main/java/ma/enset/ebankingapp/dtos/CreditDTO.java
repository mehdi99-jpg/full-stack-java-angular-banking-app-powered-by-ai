package ma.enset.ebankingapp.dtos;

import lombok.Data;

@Data
public class CreditDTO {
    private String accountId;
    private double amount;
    private String description;
}
