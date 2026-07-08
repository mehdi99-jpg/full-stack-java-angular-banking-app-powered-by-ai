package ma.enset.ebankingapp.web;

import lombok.AllArgsConstructor;
import ma.enset.ebankingapp.dtos.AccountHistoryDTO;
import ma.enset.ebankingapp.dtos.AccountOperationDTO;
import ma.enset.ebankingapp.dtos.BankAccountDTO;
import ma.enset.ebankingapp.dtos.CreditDTO;
import ma.enset.ebankingapp.dtos.DebitDTO;
import ma.enset.ebankingapp.dtos.TransferRequestDTO;
import ma.enset.ebankingapp.exceptions.BalanceNotSufficentException;
import ma.enset.ebankingapp.exceptions.BankAccountNotFoundException;
import ma.enset.ebankingapp.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/operation-pages")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

    @PostMapping("/accounts/debit")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/accounts/credit")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/accounts/transfer")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount()
        );
    }
}
