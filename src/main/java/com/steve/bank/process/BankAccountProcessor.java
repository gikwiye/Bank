package com.steve.bank.process;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.steve.bank.model.BankAccount;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.client.model.Filters.eq;

//Treat everything MongoDB


public class BankAccountProcessor implements MongoDBRepository<BankAccount> {



    private MongoCollection<BankAccount> collection;
    private MongoClient client;

    public BankAccountProcessor(CodecRegistry registry, MongoClient client) {
        this.client = client;
        MongoDatabase db = client.getDatabase("bank").withCodecRegistry(registry);
        this.collection = db.getCollection("bankAccount", BankAccount.class);

    }


    public InsertOneResult save(BankAccount record) {
        return collection.insertOne(record);
    }

    public BankAccount get(String id) {
        return collection.find(eq("iban", id)).first();
    }

    @Override
    public UpdateResult update(BankAccount record, String id) {
        Document doc = new Document().append("funds", record.getFunds());
        return collection.updateOne(eq("iban", id), new Document("$set", doc));

    }

    @Override
    public void close() throws Exception {
        client.close();
    }
}
