package com.platform4j.soa.manager.mongo.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.platform4j.soa.dao.mongo.impl.MongoDaoImpl;
import com.platform4j.soa.dao.mongo.domain.HelloMongo;
import com.platform4j.soa.manager.HelloManager;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.platform4j.arch.data.mongo.MongoBeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MongoManagerImpl extends MongoDaoImpl<HelloMongo> implements HelloManager {

    public int saveObj(HelloMongo helloMongo){
        int result = 0;
        try {
            result = super.insertObj(JSON.json(helloMongo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public HelloMongo getObjById(String id){
        BasicDBObjectBuilder start = BasicDBObjectBuilder.start();
        start = start.add("id", id);
        if (id != null) {
            start = start.add("id", id);
        }
        DBObject ref = start.get();
        DBCursor cur = getMCollection().find(ref).limit(1);
        HelloMongo helloMongo = null;
        if (cur.hasNext()) {
            DBObject dbObject = (DBObject) cur.next();
            helloMongo = MongoBeanUtil.dbObject2Bean(dbObject, new HelloMongo());
        }
        return helloMongo;
    }

    public int updateObj(String id, String field, String parameter){
        DBObject obj = BasicDBObjectBuilder.start()
                .add("id", id).get();
        DBCursor cur = getMCollection().find(obj).limit(1);
        DBObject dbObject = null;
        if (cur.hasNext()) {
            dbObject = (DBObject) cur.next();
        }
        dbObject.put(field, parameter);
        return super.getMCollection().update(obj, dbObject).getN();
    }

    public void deleteObj(String id){
        this.removeOne(id);
    }
}
