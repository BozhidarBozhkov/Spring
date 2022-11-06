package com.example.springInitDemo.services;

import com.example.springInitDemo.models.Account;
import com.example.springInitDemo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Optional<Account> account = this.accountRepository.findById(id);

        if (account.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        BigDecimal current = account.get().getBalance();

        if (money.compareTo(current) > 0) {
            throw new RuntimeException("Cannot withdraw money");
        }

        account.get().setBalance(current.subtract(money));

        this.accountRepository.save(account.get());
    }

    @Override
    @Transactional
    public void transferMoney(Long fromAccount, Long toAccount, BigDecimal money) {


    }

    @Override
    public void depositMoney(BigDecimal money, Long id) {
        Account account = this.accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Sorry, no such account"));

        BigDecimal balance = account.getBalance().add(money);
        account.setBalance(balance);

        this.accountRepository.save(account);
    }


}
