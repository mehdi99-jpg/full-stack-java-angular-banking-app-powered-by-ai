package ma.enset.ebankingapp.ai;

import lombok.AllArgsConstructor;
import ma.enset.ebankingapp.entities.AccountOperation;
import ma.enset.ebankingapp.entities.BankAccount;
import ma.enset.ebankingapp.entities.Customer;
import ma.enset.ebankingapp.repositories.AccountOperationRepository;
import ma.enset.ebankingapp.repositories.BankAccountRepository;
import ma.enset.ebankingapp.repositories.CustomerRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class BankingTools {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;

    @Tool(description = "Get the list of all banking customers. Returns customer ID, name, and email for each customer.")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Tool(description = "Get a specific customer by their unique ID. Returns customer details including name and email.")
    public Customer getCustomerById(@ToolParam(description = "The unique numeric ID of the customer") Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Tool(description = "Get the list of all bank accounts in the system. Each account has an ID, balance, creation date, status, type (CurrentAccount or SavingAccount), and an associated customer.")
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    @Tool(description = "Get a specific bank account by its unique string ID (UUID format). Returns account balance, status, type, and the owning customer.")
    public BankAccount getAccountById(@ToolParam(description = "The UUID string ID of the bank account") String id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    @Tool(description = "Get the list of all financial operations (DEBIT and CREDIT transactions) for a specific bank account. Returns operation date, amount, type, and description.")
    public List<AccountOperation> getAccountOperations(@ToolParam(description = "The UUID string ID of the bank account to get operations for") String accountId) {
        return accountOperationRepository.findByBankAccountId(accountId);
    }
}
