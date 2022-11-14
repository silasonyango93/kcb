package bank.controller.account_management;

import bank.dto.account_management.AccountStatusResponseDto;
import bank.dto.account_management.AccountCreationRequestDto;
import bank.dto.account_management.AccountCreditOrDebitRequestDto;
import bank.dto.account_management.TransferRequestDto;
import bank.entity.account_management.AccountStatus;
import bank.entity.account_management.AccountsEntity;
import bank.entity.account_management.TransferStatus;
import bank.entity.user_management.User;
import bank.repository.user_management.UserRepository;
import bank.security.JwtTokenProvider;
import bank.service.account_management.AccountService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
@Api(tags = "accounts")
public class AccountsController {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @GetMapping(value = "/balance/{accountNumber}")
    @ApiOperation(value = "${AccountsController.balance}", response = AccountStatusResponseDto.class, authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Account number does not exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public ResponseEntity<AccountStatusResponseDto> retrieveAccountBalance(@ApiParam("accountNumber") @PathVariable String accountNumber) {
        AccountStatusResponseDto accountStatusResponseDto = accountService.retrieveAccountBalance(accountNumber);
        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.DOES_NOT_EXIST) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(422));
        }

        return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(200));
    }


    @PostMapping("/create")
    @ApiOperation(value = "${AccountsController.create}", response = AccountsEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 422, message = "Account number already exists")})
    public ResponseEntity<AccountsEntity> createAccount(HttpServletRequest httpServletRequest, @ApiParam("Account details") @RequestBody AccountCreationRequestDto accountCreationRequestDto) {
        String accessToken = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUsername(accessToken);
        User currentUser = userRepository.findByUserEmail(email);

        AccountsEntity createdAccount = accountService.createAccount(currentUser.getUserId(), accountCreationRequestDto);
        if (createdAccount == null) {
            return new ResponseEntity<AccountsEntity>(createdAccount, HttpStatus.valueOf(500));
        }
        return new ResponseEntity<AccountsEntity>(createdAccount, HttpStatus.valueOf(200));
    }

    @PostMapping("/deposit")
    @ApiOperation(value = "${AccountsController.deposit}", response = AccountStatusResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 422, message = "Account number does not exist"),
            @ApiResponse(code = 425, message = "Ongoing transaction"),
            @ApiResponse(code = 426, message = "Exceeds per transaction limit"),
            @ApiResponse(code = 405, message = "Exceeds daily transaction limit"),
            @ApiResponse(code = 406, message = "Exceeds daily transaction frequency limit")
    })
    public ResponseEntity<AccountStatusResponseDto> creditAccount(@ApiParam("Account details") @RequestBody AccountCreditOrDebitRequestDto accountCreditOrDebitRequestDto) {
        AccountStatusResponseDto accountStatusResponseDto = accountService.creditAnAccount(accountCreditOrDebitRequestDto.getAccountNumber(), accountCreditOrDebitRequestDto.getAmount());
        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.ONGOING_TRANSACTION) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(425));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.DOES_NOT_EXIST) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(422));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.EXCEEDS_PER_TRANSACTION_DEPOSIT_LIMIT) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(426));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.EXCEED_DAILY_DEPOSIT_LIMIT) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(405));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.EXCEEDS_DAILY_TRANSACTION_FREQUENCY_LIMIT) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(406));
        }

        return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(200));
    }

    @PostMapping("/withdraw")
    @ApiOperation(value = "${AccountsController.withdraw}", response = AccountStatusResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 422, message = "Account number does not exist"),
            @ApiResponse(code = 423, message = "Insufficient funds"),
            @ApiResponse(code = 415, message = "Exceeds per transaction withdrawal limit"),
            @ApiResponse(code = 416, message = "Exceeds daily maximum withdrawal limit"),
            @ApiResponse(code = 417, message = "Exceeded daily withdrawal frequency limit")
    })
    public ResponseEntity<AccountStatusResponseDto> debitAccount(@ApiParam("Account details") @RequestBody AccountCreditOrDebitRequestDto accountCreditOrDebitRequestDto) {
        AccountStatusResponseDto accountStatusResponseDto = accountService.debitAnAccount(accountCreditOrDebitRequestDto.getAccountNumber(), accountCreditOrDebitRequestDto.getAmount());

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.ONGOING_TRANSACTION) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(425));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.DOES_NOT_EXIST) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(422));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.INSUFFICIENT_FUNDS) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(423));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.EXCEEDS_PER_TRANSACTION_WITHDRAWAL_LIMIT) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(415));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.EXCEEDED_DAILY_MAX_WITHDRAWAL_LIMIT) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(416));
        }

        if (accountStatusResponseDto.getAccountStatus() == AccountStatus.EXCEEDS_DAILY_WITHDRAWAL_FREQUENCY_LIMIT) {
            return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(417));
        }

        return new ResponseEntity<AccountStatusResponseDto>(accountStatusResponseDto, HttpStatus.valueOf(200));
    }

    @PostMapping("/transfer")
    @ApiOperation(value = "${AccountsController.transfer}", response = AccountStatusResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 420, message = "Account to be debited does not exist"),
            @ApiResponse(code = 421, message = "Account to be credited does not exist"),
            @ApiResponse(code = 423, message = "Insufficient funds")
    })
    public ResponseEntity<TransferStatus> transfer(@ApiParam("Transfer details") @RequestBody TransferRequestDto transferRequestDto) {
        TransferStatus transferStatus = accountService.transferFunds(transferRequestDto.getFromAccount(),
                transferRequestDto.getToAccount(),
                transferRequestDto.getAmount());

        if (transferStatus == TransferStatus.ORIGINATOR_ACCOUNT_NON_EXISTENT) {
            return new ResponseEntity<TransferStatus>(transferStatus, HttpStatus.valueOf(420));
        }

        if (transferStatus == TransferStatus.RECEPIENT_ACCOUNT_NON_EXISTENT) {
            return new ResponseEntity<TransferStatus>(transferStatus, HttpStatus.valueOf(421));
        }

        if (transferStatus == TransferStatus.INSUFICIENT_ORIGINATOR_ACCOUNT_FUNDS) {
            return new ResponseEntity<TransferStatus>(transferStatus, HttpStatus.valueOf(423));
        }

        return new ResponseEntity<TransferStatus>(transferStatus, HttpStatus.valueOf(200));
    }
}
