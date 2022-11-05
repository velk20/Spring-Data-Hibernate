package com.example.springdataintro.services;

import com.example.springdataintro.exceptions.EntityMissingException;
import com.example.springdataintro.models.Account;
import com.example.springdataintro.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Optional<Account> account = this.accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Account does not exist");
        }

        BigDecimal currentBalance = account.get().getBalance();
        if (amount.compareTo(currentBalance) > 0) {
            throw new EntityMissingException("Cannot withdraw, no enough balance!");
        }
        account.get().setBalance(currentBalance.subtract(amount));
        this.accountRepository.save(account.get());
    }

    @Override
    public void depositMoney(BigDecimal amount, Long id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Sorry no such account!"));

        BigDecimal balance = account.getBalance();
        BigDecimal add = balance.add(amount);
        account.setBalance(add);

        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void transferMoney(Long accountFrom, Long accountTo, BigDecimal amount) {

    }
}
