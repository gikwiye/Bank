package com.steve.bank.process;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.steve.bank.exception.BankException;
import com.steve.bank.model.BankAccount;
import com.steve.bank.model.Result;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

//Treat everything POJO
public class BankProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankProcessor.class);
    private final MongoDBRepository<BankAccount> repository;

    public BankProcessor(MongoDBRepository<BankAccount> repository) {
        this.repository = repository;
    }

    public Result deposit(BigDecimal amount, String accountId) {
        try {
            BankAccount bankAccount = repository.get(accountId);
            BigDecimal currentAmount = bankAccount.getFunds();
            BigDecimal newAmount = currentAmount.add(amount);
            bankAccount.setFunds(newAmount);
            UpdateResult result = repository.update(bankAccount, accountId);
            return Result.builder().modifiedCount(result.getModifiedCount()).build();
        } catch (Exception e) {
            String msg = "Failed to modify";
            LOGGER.error(msg,e);
            throw new BankException(msg,e);
        }

    }


    public Result createAccountDetails(BankAccount bankAccount) {
        try {
            InsertOneResult result = repository.save(bankAccount);
            return Result.builder().objectId(Objects.requireNonNull(result.getInsertedId()).asObjectId().toString())
                    .build();
        } catch (Exception e) {
            String msg = "Failed to insert";
            LOGGER.error(msg, e);
            throw new BankException(msg, e);
        }
    }

    public Result modifyAccountDetails(BankAccount bankAccount, String id) {
        try {
            UpdateResult result = repository.update(bankAccount, id);
            return Result.builder().modifiedCount(result.getModifiedCount())
                    .build();
        } catch (Exception e) {
            String msg = "Failed to update";
            LOGGER.error(msg, e);
            throw new BankException(msg, e);
        }
    }
}
