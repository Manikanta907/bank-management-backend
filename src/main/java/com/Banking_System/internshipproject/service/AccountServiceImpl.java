package com.Banking_System.internshipproject.service;

import com.Banking_System.internshipproject.entity.Account;
import com.Banking_System.internshipproject.entity.Transaction;
import com.Banking_System.internshipproject.enums.TransactionType;
import com.Banking_System.internshipproject.exception.InsufficientBalanceException;
import com.Banking_System.internshipproject.exception.InvalidAmountException;
import com.Banking_System.internshipproject.exception.ResourceNotFoundException;
import com.Banking_System.internshipproject.repository.AccountRepository;
import com.Banking_System.internshipproject.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Banking_System.internshipproject.dto.DashboardStatsResponse;
import com.Banking_System.internshipproject.enums.TransactionType;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Account createAccount(Account account) {

        String accountNumber;

        while (true) {

            accountNumber = String.valueOf(
                    1000000000L + (long) (Math.random() * 9000000000L)
            );

            boolean exists = false;

            for (Account a : accountRepository.findAll()) {

                if (a.getAccountNumber().equals(accountNumber)) {
                    exists = true;
                    break;
                }

            }

            if (!exists) {
                break;
            }

        }

        account.setAccountNumber(accountNumber);

        return accountRepository.save(account);

    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found with id : " + id));

    }

    @Override
    public Account updateAccount(Long id, Account account) {

        Account existingAccount = getAccountById(id);

        existingAccount.setAccountHolderName(account.getAccountHolderName());
        existingAccount.setAccountNumber(account.getAccountNumber());
        existingAccount.setEmail(account.getEmail());
        existingAccount.setPhoneNumber(account.getPhoneNumber());
        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setStatus(account.getStatus());

        return accountRepository.save(existingAccount);

    }

    @Override
    public void deleteAccount(Long id) {

        Account account = getAccountById(id);

        accountRepository.delete(account);

    }

    @Override
    @Transactional
    public Account deposit(Long accountId, Double amount) {

        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }

        Account account = getAccountById(accountId);

        account.setBalance(account.getBalance() + amount);

        accountRepository.save(account);

        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setDescription("Cash Deposit");
        transaction.setBalanceAfterTransaction(account.getBalance());
        transaction.setAccount(account);

        transactionRepository.save(transaction);

        return account;

    }

    @Override
    @Transactional
    public Account withdraw(Long accountId, Double amount) {

        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }

        Account account = getAccountById(accountId);

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        account.setBalance(account.getBalance() - amount);

        accountRepository.save(account);

        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setDescription("Cash Withdrawal");
        transaction.setBalanceAfterTransaction(account.getBalance());
        transaction.setAccount(account);

        transactionRepository.save(transaction);

        return account;

    }

    @Override
    public Double getBalance(Long accountId) {

        Account account = getAccountById(accountId);

        return account.getBalance();

    }

    @Override
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {

        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }

        Account sender = getAccountById(fromAccountId);

        Account receiver = getAccountById(toAccountId);

        if (sender.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        sender.setBalance(sender.getBalance() - amount);

        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);

        accountRepository.save(receiver);

        Transaction senderTransaction = new Transaction();

        senderTransaction.setAmount(amount);
        senderTransaction.setTransactionType(TransactionType.TRANSFER);
        senderTransaction.setDescription("Transferred to Account : " + receiver.getAccountNumber());
        senderTransaction.setBalanceAfterTransaction(sender.getBalance());
        senderTransaction.setAccount(sender);

        transactionRepository.save(senderTransaction);

        Transaction receiverTransaction = new Transaction();

        receiverTransaction.setAmount(amount);
        receiverTransaction.setTransactionType(TransactionType.TRANSFER);
        receiverTransaction.setDescription("Received from Account : " + sender.getAccountNumber());
        receiverTransaction.setBalanceAfterTransaction(receiver.getBalance());
        receiverTransaction.setAccount(receiver);

        transactionRepository.save(receiverTransaction);

    }
    @Override
    public DashboardStatsResponse getDashboardStats() {

        Long totalAccounts = accountRepository.count();

        Double totalBalance = accountRepository.findAll()
                .stream()
                .mapToDouble(Account::getBalance)
                .sum();

        Double totalDeposits = transactionRepository.findAll()
                .stream()
                .filter(t -> t.getTransactionType() == TransactionType.DEPOSIT)
                .mapToDouble(Transaction::getAmount)
                .sum();

        Double totalWithdrawals = transactionRepository.findAll()
                .stream()
                .filter(t -> t.getTransactionType() == TransactionType.WITHDRAW)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return new DashboardStatsResponse(
                totalAccounts,
                totalBalance,
                totalDeposits,
                totalWithdrawals
        );

    }

}