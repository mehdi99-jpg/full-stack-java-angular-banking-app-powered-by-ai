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
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BankingTools {

    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;

    @Tool(description = "Get the list of all banking customers. Returns customer ID, name, and email for each customer.")
    public List<Map<String, Object>> getAllCustomers(@ToolParam(description = "Dummy parameter, always pass empty string") String dummy) {
        return customerRepository.findAll().stream()
            .map(c -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", c.getId());
                map.put("name", c.getName());
                map.put("email", c.getEmail());
                return map;
            })
            .collect(Collectors.toList());
    }

    @Tool(description = "Get a specific customer by their unique ID. Returns customer details including name and email.")
    public Map<String, Object> getCustomerById(@ToolParam(description = "The unique numeric ID of the customer") Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("id", customer.getId());
        map.put("name", customer.getName());
        map.put("email", customer.getEmail());
        return map;
    }

    @Tool(description = "Get the list of all bank accounts in the system. Each account has an ID, balance, creation date, status, type (CurrentAccount or SavingAccount), and an associated customer.")
    public List<Map<String, Object>> getAllAccounts(@ToolParam(description = "Dummy parameter, always pass empty string") String dummy) {
        return bankAccountRepository.findAll().stream()
            .map(a -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", a.getId());
                map.put("balance", a.getBalance());
                map.put("createdAt", a.getCreatedAt());
                map.put("status", a.getStatus() != null ? a.getStatus().toString() : null);
                map.put("type", a.getClass().getSimpleName());
                if (a.getCustomer() != null) {
                    map.put("customerName", a.getCustomer().getName());
                    map.put("customerEmail", a.getCustomer().getEmail());
                }
                return map;
            })
            .collect(Collectors.toList());
    }

    @Tool(description = "Get a specific bank account by its unique string ID (UUID format). Returns account balance, status, type, and the owning customer.")
    public Map<String, Object> getAccountById(@ToolParam(description = "The UUID string ID of the bank account") String id) {
        BankAccount a = bankAccountRepository.findById(id).orElse(null);
        if (a == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("id", a.getId());
        map.put("balance", a.getBalance());
        map.put("createdAt", a.getCreatedAt());
        map.put("status", a.getStatus() != null ? a.getStatus().toString() : null);
        map.put("type", a.getClass().getSimpleName());
        if (a.getCustomer() != null) {
            map.put("customerName", a.getCustomer().getName());
            map.put("customerEmail", a.getCustomer().getEmail());
        }
        return map;
    }

    @Tool(description = "Get the list of all financial operations (DEBIT and CREDIT transactions) for a specific bank account. Returns operation date, amount, type, and description.")
    public List<Map<String, Object>> getAccountOperations(@ToolParam(description = "The UUID string ID of the bank account to get operations for") String accountId) {
        return accountOperationRepository.findByBankAccountId(accountId).stream()
            .map(op -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", op.getId());
                map.put("date", op.getOperationDate());
                map.put("amount", op.getAmount());
                map.put("type", op.getType() != null ? op.getType().toString() : null);
                map.put("description", op.getDescription());
                return map;
            })
            .collect(Collectors.toList());
    }
}
