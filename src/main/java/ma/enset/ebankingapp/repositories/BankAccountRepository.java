package ma.enset.ebankingapp.repositories;

import ma.enset.ebankingapp.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
