package com.steve.bank.process;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.steve.bank.model.BankAccount;
import com.steve.bank.model.Result;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class BankProcessorTest {

    private static MongoDBRepository<BankAccount> repository;
    private static BankProcessor bankProcessor;


    @BeforeAll
    public static void init() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        CodecProvider provider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodeRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(provider));
        deleteAll(client,pojoCodeRegistry);
        repository = new BankAccountProcessor(pojoCodeRegistry, client);
        bankProcessor = new BankProcessor(repository);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        repository.close();
    }


    @Test
    public void deposit() {
        String accountId = "BE0100271087";
        BankAccount bankAccount = new BankAccount();
        BigDecimal balance = new BigDecimal("100");
        BigDecimal depositAmount = new BigDecimal("200");
        bankAccount.setIban(accountId);
        bankAccount.setFunds(new BigDecimal(balance.toString()));
        bankAccount.setType("S");
        bankProcessor.createAccountDetails(bankAccount);
        Result result = bankProcessor.deposit(depositAmount, accountId);
        BankAccount updateBankAccount = repository.get(accountId);
        assertThat(updateBankAccount.getFunds()).isEqualTo(new BigDecimal(balance.add(depositAmount).toString()));
        assertThat(result.getModifiedCount()).isEqualTo(1L);


    }

    @Test
    public void createAccountDetailsTest() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("BE0145671087");
        bankAccount.setFunds(new BigDecimal("100"));
        bankAccount.setType("S");
        bankProcessor.createAccountDetails(bankAccount);
        BankAccount result = repository.get("BE0145671087");
        assertThat(result.getFunds()).isEqualTo(new BigDecimal("100"));
    }

    @Test
    public void modifyAccountDetailsTest() {

        String accountId = "BE0145671087";
        BankAccount bankAccount = repository.get(accountId);
        bankAccount.setFunds(new BigDecimal("200"));
        Result result = bankProcessor.modifyAccountDetails(bankAccount, accountId);
        long modifiedCount = result.getModifiedCount();
        assertThat(modifiedCount).isEqualTo(1);

    }

    private static void deleteAll(MongoClient client, CodecRegistry registry){


        MongoDatabase db = client.getDatabase("bank").withCodecRegistry(registry);
        MongoCollection<BankAccount> bankAccount = db.getCollection("bankAccount", BankAccount.class);
        bankAccount.deleteMany(new Document());

    }
}