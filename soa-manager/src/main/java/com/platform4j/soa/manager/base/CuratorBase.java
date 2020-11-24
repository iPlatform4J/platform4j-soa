package com.platform4j.soa.manager.base;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class CuratorBase {

    public static CuratorFramework framework= null;
    static {
        framework = CuratorFrameworkFactory.newClient(
                "",
                20000,
                20000,
                new RetryNTimes(3, 1000));
        framework.start();
    }

    public static String buildPath(String name)
    {
        String path = "";
        String[] roots = new String[]{"platform4j","lock"};
        for(String str : roots)
        {
            if(str.startsWith("/")){
                path +="/";
            }
            path +="/" +str;
        }
        path +="/" +name;
        return path;
    }
}
