package myapp.ebank.model.dto;

import lombok.Data;
import myapp.ebank.model.entity.Funds;
import myapp.ebank.model.entity.Loans;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserFundsAndLoans {
    List<Funds> funds = new ArrayList<>();
    List<Loans> loans = new ArrayList<>();
}
