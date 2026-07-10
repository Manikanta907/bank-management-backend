package com.Banking_System.internshipproject.service;

import com.Banking_System.internshipproject.entity.Account;

import com.Banking_System.internshipproject.dto.DashboardStatsResponse;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    List<Account> getAllAccounts();

    Account getAccountById(Long id);

    Account updateAccount(Long id, Account account);

    void deleteAccount(Long id);

    Account deposit(Long accountId, Double amount);

    Account withdraw(Long accountId, Double amount);

    Double getBalance(Long accountId);

    void transfer(Long fromAccountId, Long toAccountId, Double amount);

    DashboardStatsResponse getDashboardStats();

}