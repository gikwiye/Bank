package com.steve.bank.process;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.steve.bank.model.BankAccount;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {


    }
}
