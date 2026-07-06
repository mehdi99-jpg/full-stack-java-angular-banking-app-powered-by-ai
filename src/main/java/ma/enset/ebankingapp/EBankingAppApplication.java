package ma.enset.ebankingapp;

import ma.enset.ebankingapp.entities.Customer;
import ma.enset.ebankingapp.repositories.AccountOperationRepository;
import ma.enset.ebankingapp.repositories.BankAccountRepository;
import ma.enset.ebankingapp.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class EBankingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingAppApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository
    , AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Mehdi","Hassan","Khadija","Wissal","Hamza").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
        };
    }
}
