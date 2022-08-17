package com.steve.bank.process;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public interface MongoDBRepository<T> extends AutoCloseable{

    InsertOneResult save(T record);
    T get(String id);
    UpdateResult update(T record, String id);





}
