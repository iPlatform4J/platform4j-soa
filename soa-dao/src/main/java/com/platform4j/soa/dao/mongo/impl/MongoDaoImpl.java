package com.platform4j.soa.dao.mongo.impl;

import com.platform4j.soa.dao.mongo.MongoDao;
import com.mongodb.*;
import com.mongodb.util.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.regex.Pattern;

public class MongoDaoImpl<T> implements MongoDao<T> {

    protected final Log log = LogFactory.getLog(MongoDaoImpl.class);

    /**
     * 泛型Class实例
     */
    protected Class entityClass;



    /**
     * 无参构造
     * 初始化entityClass
     */
    public MongoDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * mongo模板
     */
    @Resource
    protected MongoTemplate mongoTemplate;



    public void findlike(String field , String regex){
        DBCollection dbc = this.getMCollection();
        Pattern pattern=Pattern.compile("^.*"+regex+".*$");
    }

    /**
     * 获取数据库
     * @return
     */
    public DB getMDB(){
        return this.mongoTemplate.getDb();
    }

    /**
     * 根据实体获取Collection
     * @return
     */
    public DBCollection getMCollection(){
        System.out.println(entityClass.getSimpleName().toLowerCase());
        return getMDB().getCollection(entityClass.getSimpleName().toLowerCase());
    }

    public DBCollection getMCollection(String collectionName){
        return getMDB().getCollection(collectionName);
    }


    public String insert(String entity) {
        DBObject obj = (DBObject) JSON.parse(entity);
        String pk = obj.get("id").toString();
        if(pk!=null){
            obj.put("_id", pk);
        }
        WriteResult wr = this.getMCollection().insert(obj);
        return obj.get("_id").toString();
    }

    public int insertObj(String entity) {
        DBObject obj = (DBObject) JSON.parse(entity);
        return this.getMCollection().insert(obj).getN();
    }

    public T findOne(String id) {
        return (T) this.mongoTemplate.findById(id, entityClass);
    }

    public List<T> findAll() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.regex("");
        query.addCriteria(criteria);
//		this.mongoTemplate.find(query, entityClass)
        return this.mongoTemplate.findAll(entityClass);
    }

    public void removeOne(String id) {
        Query query =new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        this.mongoTemplate.remove(query, entityClass);

    }

    public void removeAll() {
        this.getMCollection().drop();
    }

    public void findAndModify(String id,String entity) {
        DBObject obj = new BasicDBObject();
        obj.put("_id", id);
        this.getMCollection().findAndModify(obj, (DBObject)JSON.parse(entity));
    }
}
