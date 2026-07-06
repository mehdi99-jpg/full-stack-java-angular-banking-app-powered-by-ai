package ma.enset.ebankingapp.repositories;

import ma.enset.ebankingapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
