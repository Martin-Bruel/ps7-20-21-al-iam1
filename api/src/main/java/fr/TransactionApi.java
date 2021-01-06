package fr;

import fr.model.account.Account;
import fr.model.account.AccountRepository;
import fr.model.traffic.Traffic;
import fr.model.transaction.Transaction;
import fr.model.transaction.TransactionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    TransactionApi(TransactionRepository transactionRepository, AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }


    @PostMapping("/")
    Transaction addTransaction(@RequestBody Transaction newTransaction){

        Optional<Account> optAcount = accountRepository.findById(newTransaction.getAccountId());

        if (optAcount.isPresent()){
            Account account = optAcount.get();

            try {
                account.debitBalance(newTransaction.getAmount(), newTransaction.getCurrencyType());
                accountRepository.save(account);
                System.out.println("Transaction acceptée.");
                return transactionRepository.save(newTransaction);
            }
            catch (IllegalArgumentException ex){
                System.out.println("Erreur le compte " + account.getUsername() + " n'a pas assez de sous pour la transaction");
                return null;
            }
        }

        System.out.println("Le compte " + newTransaction.getAccountId() + " n'existe pas...");
        return null;
    }
}
