package com.steve.bank.process;


import com.steve.bank.model.BankClient;

//Treat everything POJO
public class PersonProcess {

    private MongoDBRepository<BankClient> repository;

    public PersonProcess(MongoDBRepository<BankClient> repository) {
        this.repository = repository;
    }

    public void createClient(BankClient record){
        repository.save(record);
    }

    public void updateClient(BankClient record, String clientId){
        repository.update(record,clientId);
    }

    public void getClient(String clientId){
        repository.get(clientId);
    }
}
