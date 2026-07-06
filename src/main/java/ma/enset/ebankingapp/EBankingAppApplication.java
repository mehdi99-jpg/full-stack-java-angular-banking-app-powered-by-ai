package ma.enset.ebankingapp;

import ma.enset.ebankingapp.entities.CurrentAccount;
import ma.enset.ebankingapp.entities.Customer;
import ma.enset.ebankingapp.entities.SavingAccount;
import ma.enset.ebankingapp.enums.AccountStatus;
import ma.enset.ebankingapp.repositories.AccountOperationRepository;
import ma.enset.ebankingapp.repositories.BankAccountRepository;
import ma.enset.ebankingapp.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
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
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                currentAccount.setCustomer(cust);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(4.5);
                savingAccount.setCustomer(cust);
                bankAccountRepository.save(savingAccount);
            });
        };
    }
}
