package com.steve.bank.process;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.steve.bank.model.BankClient;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

//Treat everything MongoDB
public class ClientProcessor implements MongoDBRepository<BankClient> {

    private MongoCollection<BankClient> collection;

    public ClientProcessor(MongoCollection<BankClient> collection){
        this.collection = collection;
    }

    @Override
    public InsertOneResult save(BankClient record) {
        return collection.insertOne(record);
    }

    @Override
    public BankClient get(String id) {
        return collection.find(eq("clientId",id)).first();
    }

    @Override
    public UpdateResult update(BankClient record, String id) {
        Document doc = new Document("clientId",id);
       return collection.updateOne(eq("clientId",id),new Document("$Set",doc));
    }

    @Override
    public void close() throws Exception {

    }
}
