package com.platform4j.soa.manager;

import com.platform4j.soa.dao.mongo.domain.HelloMongo;

public interface MongoManager {

    int saveObj(HelloMongo helloMongo);

    HelloMongo getObjById(String id);

    int updateObj(String id, String field, String parameter);

    void deleteObj(String id);
}
