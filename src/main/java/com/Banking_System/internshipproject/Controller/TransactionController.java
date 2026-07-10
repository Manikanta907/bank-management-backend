package com.Banking_System.internshipproject.controller;

import com.Banking_System.internshipproject.entity.Transaction;
import com.Banking_System.internshipproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountId}")
    public List<Transaction> getTransactionHistory(@PathVariable Long accountId) {

        return transactionService.getTransactionHistory(accountId);

    }

}