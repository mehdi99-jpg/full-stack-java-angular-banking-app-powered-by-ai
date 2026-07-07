package ma.enset.ebankingapp;

import ma.enset.ebankingapp.dtos.BankAccountDTO;
import ma.enset.ebankingapp.dtos.CurrentBankAccountDTO;
import ma.enset.ebankingapp.dtos.CustomerDTO;
import ma.enset.ebankingapp.dtos.SavingBankAccountDTO;
import ma.enset.ebankingapp.entities.*;
import ma.enset.ebankingapp.enums.AccountStatus;
import ma.enset.ebankingapp.enums.OperationType;
import ma.enset.ebankingapp.exceptions.BalanceNotSufficentException;
import ma.enset.ebankingapp.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingapp.exceptions.CustomerNotFoundException;
import ma.enset.ebankingapp.repositories.AccountOperationRepository;
import ma.enset.ebankingapp.repositories.BankAccountRepository;
import ma.enset.ebankingapp.repositories.CustomerRepository;
import ma.enset.ebankingapp.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingAppApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Mehdi","Hassan","Khadija").forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setName(name);
                customerDTO.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customerDTO);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,4.5,customer.getId());
                    List<BankAccountDTO> bankAccountList = bankAccountService.bankAccountList();
                    for(BankAccountDTO bankAccount:bankAccountList){
                        for(int i = 0 ; i < 10 ; i++){
                            String accountId;
                            if (bankAccount instanceof SavingBankAccountDTO){
                                accountId=((SavingBankAccountDTO) bankAccount).getId();
                            }else{
                                accountId=((CurrentBankAccountDTO) bankAccount).getId();
                            }
                            bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                            bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                        }


                    }
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                } catch (BankAccountNotFoundException | BalanceNotSufficentException e){
                    e.printStackTrace();
                }
            });
        };
    }

//    @Bean
//    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository
//    , AccountOperationRepository accountOperationRepository){
//        return args -> {
//            Stream.of("Mehdi","Hassan","Khadija","Wissal","Hamza").forEach(name -> {
//                Customer customer = new Customer();
//                customer.setName(name);
//                customer.setEmail(name+"@gmail.com");
//                customerRepository.save(customer);
//            });
//            customerRepository.findAll().forEach(cust -> {
//                CurrentAccount currentAccount = new CurrentAccount();
//                currentAccount.setId(UUID.randomUUID().toString());
//                currentAccount.setBalance(Math.random()*90000);
//                currentAccount.setCreatedAt(new Date());
//                currentAccount.setStatus(AccountStatus.CREATED);
//                currentAccount.setOverDraft(9000);
//                currentAccount.setCustomer(cust);
//                bankAccountRepository.save(currentAccount);
//
//                SavingAccount savingAccount = new SavingAccount();
//                savingAccount.setId(UUID.randomUUID().toString());
//                savingAccount.setBalance(Math.random()*90000);
//                savingAccount.setCreatedAt(new Date());
//                savingAccount.setStatus(AccountStatus.CREATED);
//                savingAccount.setInterestRate(4.5);
//                savingAccount.setCustomer(cust);
//                bankAccountRepository.save(savingAccount);
//            });
//
//            bankAccountRepository.findAll().forEach(acc->{
//                for(int i = 0; i < 5 ; i++){
//                    AccountOperation accountOperation = new AccountOperation();
//                    accountOperation.setOperationDate(new Date());
//                    accountOperation.setAmount(Math.random()*12000);
//                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
//                    accountOperation.setBankAccount(acc);
//                    accountOperationRepository.save(accountOperation);
//                }
//
//
//            });
//
//
//        };
//    }
}
