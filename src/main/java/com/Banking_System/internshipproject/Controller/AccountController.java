package com.Banking_System.internshipproject.controller;

import com.Banking_System.internshipproject.dto.DepositRequest;
import com.Banking_System.internshipproject.dto.TransferRequest;
import com.Banking_System.internshipproject.dto.WithdrawRequest;
import com.Banking_System.internshipproject.entity.Account;
import com.Banking_System.internshipproject.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Banking_System.internshipproject.dto.DashboardStatsResponse;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://bank-management-frontendd.onrender.com"
})
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create Account
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // Get All Accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get Account By Id
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    // Update Account
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id,
                                 @RequestBody Account account) {
        return accountService.updateAccount(id, account);
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    // Deposit Money
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @RequestBody DepositRequest request) {

        return accountService.deposit(id, request.getAmount());

    }

    // Withdraw Money
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id,
                            @RequestBody WithdrawRequest request) {

        return accountService.withdraw(id, request.getAmount());

    }

    // Get Balance
    @GetMapping("/{id}/balance")
    public Double getBalance(@PathVariable Long id) {

        return accountService.getBalance(id);

    }

    // Transfer Money
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest request) {

        accountService.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );

        return "Money transferred successfully.";

    }
    @GetMapping("/dashboard")
    public DashboardStatsResponse getDashboardStats() {

        return accountService.getDashboardStats();

    }

}