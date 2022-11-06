package com.example.springInitDemo;

import com.example.springInitDemo.models.Account;
import com.example.springInitDemo.models.User;
import com.example.springInitDemo.services.AccountService;
import com.example.springInitDemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        User user = new User("Pesho", 25);
        User user1 = new User("Ivan", 30);

//        Account account = new Account();
//        account.setBalance(BigDecimal.valueOf(25000));
//        account.setUser(user);

//        user.setAccounts(new HashSet<>(){{
//            add(account);
//        }});

        userService.registerUser("Pesho", 25);
        this.accountService
                .depositMoney(BigDecimal.TEN, user.getAccountIds().get(0));

        this.accountService
                .withdrawMoney(BigDecimal.ONE, user.getAccountIds().get(0));

        userService.registerUser("Ivan", 30);
        this.accountService
                .depositMoney(BigDecimal.TEN, user.getAccountIds().get(1));

        this.accountService
                .withdrawMoney(BigDecimal.ONE, user.getAccountIds().get(1));
    }
}
