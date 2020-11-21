package com.platform4j.soa.dao.mysql;

import com.platform4j.soa.domain.Hello;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DatabaseMapper {
    List<Hello> queryHelloById(@Param("Id") String Id);
}
