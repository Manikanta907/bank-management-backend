package com.Banking_System.internshipproject.dto;

public class DashboardStatsResponse {

    private Long totalAccounts;
    private Double totalBalance;
    private Double totalDeposits;
    private Double totalWithdrawals;

    public DashboardStatsResponse() {
    }

    public DashboardStatsResponse(Long totalAccounts,
                                  Double totalBalance,
                                  Double totalDeposits,
                                  Double totalWithdrawals) {
        this.totalAccounts = totalAccounts;
        this.totalBalance = totalBalance;
        this.totalDeposits = totalDeposits;
        this.totalWithdrawals = totalWithdrawals;
    }

    public Long getTotalAccounts() {
        return totalAccounts;
    }

    public void setTotalAccounts(Long totalAccounts) {
        this.totalAccounts = totalAccounts;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getTotalDeposits() {
        return totalDeposits;
    }

    public void setTotalDeposits(Double totalDeposits) {
        this.totalDeposits = totalDeposits;
    }

    public Double getTotalWithdrawals() {
        return totalWithdrawals;
    }

    public void setTotalWithdrawals(Double totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }
}