package ma.enset.ebankingapp.services;

import ma.enset.ebankingapp.entities.BankAccount;
import ma.enset.ebankingapp.entities.CurrentAccount;
import ma.enset.ebankingapp.entities.Customer;
import ma.enset.ebankingapp.entities.SavingAccount;
import ma.enset.ebankingapp.exceptions.BalanceNotSufficentException;
import ma.enset.ebankingapp.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingapp.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance,double interestRate, Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;
    List<BankAccount> bankAccountList();
}
