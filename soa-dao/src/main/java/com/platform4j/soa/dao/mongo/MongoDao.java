package com.platform4j.soa.dao.mongo;

import java.util.List;

public interface MongoDao<T> {
    /**
     * 插入json格式字符串
     * @param entity
     * @return
     */
    public String insert(String entity);

    /**
     * 插入json格式字符串(无id字段)
     * @param entity
     * @return
     */
    public int insertObj(String entity);

    /**
     *  根据id查找对应的实体
     */
    public T findOne(String id);
    /**
     *  查找该Collection中的所有结果
     */
    public List<T> findAll();

    /**
     *	根据id删除一个对象
     */
    public void removeOne(String id);
    /**
     *  删除该Collection的所有记录
     */
    public void removeAll();
    /**
     * ͨ根据id修改一个对象
     */
    public void findAndModify(String id,String entity);
}
