package bank.dto.account_management;

import bank.entity.account_management.AccountStatus;

public class AccountStatusResponseDto {
    private int accountId;
    private String accountName;
    private double accountBalance;
    private boolean isATransactionInProgress;
    private AccountStatus accountStatus;

    public AccountStatusResponseDto(int accountId, String accountName, double accountBalance, boolean isATransactionInProgress,AccountStatus accountStatus) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.isATransactionInProgress = isATransactionInProgress;
        this.accountStatus = accountStatus;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public boolean isATransactionInProgress() {
        return isATransactionInProgress;
    }

    public void setATransactionInProgress(boolean ATransactionInProgress) {
        isATransactionInProgress = ATransactionInProgress;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
