package com.example.springInitDemo.services;

import java.math.BigDecimal;

public interface AccountService {

    void withdrawMoney(BigDecimal money, Long id);

    void transferMoney(Long fromAccount, Long toAccount, BigDecimal money);

    void depositMoney(BigDecimal money, Long id);
}
