package com.Banking_System.internshipproject.service;

import com.Banking_System.internshipproject.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactionHistory(Long accountId);

}