package bank.service.account_management;

import bank.dao.transaction_management.TransactionsDao;
import bank.dto.account_management.AccountStatusResponseDto;
import bank.dto.account_management.AccountCreationRequestDto;
import bank.entity.account_management.AccountStatus;
import bank.entity.account_management.AccountsEntity;
import bank.entity.account_management.TransferStatus;
import bank.entity.transaction_management.TransactionsEntity;
import bank.repository.account_management.AccountsRepository;
import bank.repository.transaction_management.TransactionTypesRepository;
import bank.repository.transaction_management.TransactionsRepository;
import bank.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    TransactionTypesRepository transactionTypesRepository;

    @Autowired
    TransactionsDao transactionsDao;

    public AccountsEntity createAccount(int userId, AccountCreationRequestDto accountCreationRequestDto) {
        return accountsRepository.save(new AccountsEntity(
                userId,
                accountCreationRequestDto.getAccountName(),
                accountCreationRequestDto.getAccountNumber(),
                0.0,
                0
        ));
    }

    public AccountStatusResponseDto creditAnAccount(String accountNumber, double amount) {
        AccountsEntity accountToBeCredited = accountsRepository.findByAccountNumber(accountNumber);

        if (accountToBeCredited == null) {
            return new AccountStatusResponseDto(
                    0,
                    null,
                    0.0,
                    false,
                    AccountStatus.DOES_NOT_EXIST
            );
        }

        if (accountToBeCredited.getIsTransactionOnGoing() == 1) {
            return new AccountStatusResponseDto(
                    accountToBeCredited.getAccountId(),
                    accountToBeCredited.getAccountName(),
                    accountToBeCredited.getAccountBalance(),
                    true,
                    AccountStatus.ONGOING_TRANSACTION
            );
        }

        if (amount > 40000.0) {
            return new AccountStatusResponseDto(
                    accountToBeCredited.getAccountId(),
                    accountToBeCredited.getAccountName(),
                    accountToBeCredited.getAccountBalance(),
                    false,
                    AccountStatus.EXCEEDS_PER_TRANSACTION_DEPOSIT_LIMIT
            );
        }

        double totalTransactionAmountToday = 0.0;

        List<TransactionsEntity> transactionsEntityList = transactionsDao.findByTransactionDate(Util.getToday());

        transactionsEntityList = transactionsEntityList
                .stream()
                .filter(t -> t.getTransactionTypeId() == transactionTypesRepository.findByTransactionTypeCode(1).getTransactionTypeId())
                .collect(Collectors.toList());

        if (transactionsEntityList.size() >= 4) {
            return new AccountStatusResponseDto(
                    accountToBeCredited.getAccountId(),
                    accountToBeCredited.getAccountName(),
                    accountToBeCredited.getAccountBalance(),
                    false,
                    AccountStatus.EXCEEDS_DAILY_TRANSACTION_FREQUENCY_LIMIT
            );
        }

        for (TransactionsEntity currentTransaction : transactionsEntityList) {
            totalTransactionAmountToday = totalTransactionAmountToday + currentTransaction.getTransactionAmount();
        }

        if (totalTransactionAmountToday >= 150000 || (totalTransactionAmountToday + amount) >= 150000) {
            return new AccountStatusResponseDto(
                    accountToBeCredited.getAccountId(),
                    accountToBeCredited.getAccountName(),
                    accountToBeCredited.getAccountBalance(),
                    false,
                    AccountStatus.EXCEED_DAILY_DEPOSIT_LIMIT
            );
        }

        accountToBeCredited.setIsTransactionOnGoing(1);
        accountsRepository.save(accountToBeCredited);
        accountToBeCredited.setAccountBalance(accountToBeCredited.getAccountBalance() + amount);
        accountToBeCredited.setIsTransactionOnGoing(0);
        AccountsEntity updatedAccount = accountsRepository.save(accountToBeCredited);

        transactionsRepository.save(new TransactionsEntity(
                transactionTypesRepository.findByTransactionTypeCode(1).getTransactionTypeId(),
                updatedAccount.getAccountId(),
                accountToBeCredited.getAccountBalance(),
                updatedAccount.getAccountBalance(),
                updatedAccount.getAccountNumber(),
                null,
                amount,
                Util.getToday()
        ));
        return new AccountStatusResponseDto(
                updatedAccount.getAccountId(),
                updatedAccount.getAccountName(),
                updatedAccount.getAccountBalance(),
                false,
                AccountStatus.SUCCESSFUL_TRANSACTION
        );
    }


    public AccountStatusResponseDto debitAnAccount(String accountNumber, double amount) {
        AccountsEntity accountToBeDebited = accountsRepository.findByAccountNumber(accountNumber);

        if (accountToBeDebited == null) {
            return new AccountStatusResponseDto(
                    0,
                    null,
                    0.0,
                    false,
                    AccountStatus.DOES_NOT_EXIST
            );
        }

        if (accountToBeDebited.getIsTransactionOnGoing() == 1) {
            return new AccountStatusResponseDto(
                    accountToBeDebited.getAccountId(),
                    accountToBeDebited.getAccountName(),
                    accountToBeDebited.getAccountBalance(),
                    true,
                    AccountStatus.ONGOING_TRANSACTION
            );
        }

        if (accountToBeDebited.getAccountBalance() < amount) {
            return new AccountStatusResponseDto(
                    accountToBeDebited.getAccountId(),
                    accountToBeDebited.getAccountName(),
                    accountToBeDebited.getAccountBalance(),
                    false,
                    AccountStatus.INSUFFICIENT_FUNDS
            );
        }

        if (amount > 20000) {
            return new AccountStatusResponseDto(
                    accountToBeDebited.getAccountId(),
                    accountToBeDebited.getAccountName(),
                    accountToBeDebited.getAccountBalance(),
                    false,
                    AccountStatus.EXCEEDS_PER_TRANSACTION_WITHDRAWAL_LIMIT
            );
        }


        List<TransactionsEntity> transactionsEntityList = transactionsDao.findByTransactionDate(Util.getToday());

        transactionsEntityList = transactionsEntityList
                .stream()
                .filter(t -> t.getTransactionTypeId() == transactionTypesRepository.findByTransactionTypeCode(2).getTransactionTypeId())
                .collect(Collectors.toList());

        double totalWithdrawnAmountToday = 0.0;

        for (TransactionsEntity currentTransaction : transactionsEntityList) {
            totalWithdrawnAmountToday = totalWithdrawnAmountToday + currentTransaction.getTransactionAmount();
        }

        if (totalWithdrawnAmountToday >= 50000 || (totalWithdrawnAmountToday + amount) >= 50000) {
            return new AccountStatusResponseDto(
                    accountToBeDebited.getAccountId(),
                    accountToBeDebited.getAccountName(),
                    accountToBeDebited.getAccountBalance(),
                    false,
                    AccountStatus.EXCEEDED_DAILY_MAX_WITHDRAWAL_LIMIT
            );
        }


        if (transactionsEntityList.size() >= 3) {
            return new AccountStatusResponseDto(
                    accountToBeDebited.getAccountId(),
                    accountToBeDebited.getAccountName(),
                    accountToBeDebited.getAccountBalance(),
                    false,
                    AccountStatus.EXCEEDS_DAILY_WITHDRAWAL_FREQUENCY_LIMIT
            );
        }

        accountToBeDebited.setIsTransactionOnGoing(1);
        accountsRepository.save(accountToBeDebited);
        AccountsEntity lockedAccount = accountsRepository.findByAccountNumber(accountNumber);

        lockedAccount.setAccountBalance(lockedAccount.getAccountBalance() - amount);
        lockedAccount.setIsTransactionOnGoing(0);
        AccountsEntity updatedAccount = accountsRepository.save(lockedAccount);

        transactionsRepository.save(new TransactionsEntity(
                transactionTypesRepository.findByTransactionTypeCode(2).getTransactionTypeId(),
                updatedAccount.getAccountId(),
                accountToBeDebited.getAccountBalance(),
                updatedAccount.getAccountBalance(),
                null,
                updatedAccount.getAccountNumber(),
                amount,
                Util.getToday()
        ));
        return new AccountStatusResponseDto(
                updatedAccount.getAccountId(),
                updatedAccount.getAccountName(),
                updatedAccount.getAccountBalance(),
                false,
                AccountStatus.SUCCESSFUL_TRANSACTION
        );
    }

    public TransferStatus transferFunds(String originatorAccountNumber, String recepientAccountNumber, double amount) {
        AccountsEntity originatorAccount = accountsRepository.findByAccountNumber(originatorAccountNumber);

        if (originatorAccount == null) {
            return TransferStatus.ORIGINATOR_ACCOUNT_NON_EXISTENT;
        }

        if (originatorAccount.getAccountBalance() < amount) {
            return TransferStatus.INSUFICIENT_ORIGINATOR_ACCOUNT_FUNDS;
        }

        AccountsEntity recepientAccount = accountsRepository.findByAccountNumber(recepientAccountNumber);

        if (recepientAccount == null) {
            return TransferStatus.RECEPIENT_ACCOUNT_NON_EXISTENT;
        }

        originatorAccount.setAccountBalance(originatorAccount.getAccountBalance() - amount);
        recepientAccount.setAccountBalance(recepientAccount.getAccountBalance() + amount);
        List<AccountsEntity> accountsEntityList = new ArrayList<>();
        accountsEntityList.add(originatorAccount);
        accountsEntityList.add(recepientAccount);
        accountsRepository.saveAll(accountsEntityList);

        return TransferStatus.SUCCESSFUL_TRANSFER;
    }


    public AccountStatusResponseDto retrieveAccountBalance(String accountNumber) {
        AccountsEntity originatorAccount = accountsRepository.findByAccountNumber(accountNumber);

        if (originatorAccount == null) {
            return new AccountStatusResponseDto(
                    0,
                    null,
                    0.0,
                    false,
                    AccountStatus.DOES_NOT_EXIST
            );
        }

        return new AccountStatusResponseDto(
                originatorAccount.getAccountId(),
                originatorAccount.getAccountName(),
                originatorAccount.getAccountBalance(),
                false,
                AccountStatus.ACCOUNT_BALANCE_REQUEST
        );
    }
}
