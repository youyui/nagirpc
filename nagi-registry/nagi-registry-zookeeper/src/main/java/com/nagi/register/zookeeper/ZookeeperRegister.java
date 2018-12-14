package com.nagi.register.zookeeper;

import com.nagi.common.Constants;
import com.nagi.common.URL;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @program: nagirpc
 * @descrition: 服务注册中心-zookeeper实现
 * @author: Nagi
 * @create: 2018-12-13
 */
public class ZookeeperRegister {
    public static final String host = "47.99.214.129";
    public static final int port = 2181;

    public static final String NAGI = "nagi";
    private static ZkClient zkClient;

    public static final String DELIMETER = "/";

    public ZookeeperRegister(){
        zkClient = new ZkClient(host,port);
        //boolean exists = zkClient.exists("/" + NAGI);
        if (!zkClient.exists("/" + NAGI)){
            zkClient.createPersistent("/"+NAGI);
        }
    }
    /*注册URL*/
    public void registerURL(URL url){
        if (url==null) return;
        /*获得注册服务地址，也就是IOC的实力名字*/
        String path = DELIMETER + NAGI + DELIMETER+url.getPath();
        /*服务节点目录*/
        if(!zkClient.exists(path)){
            zkClient.createEphemeral(path);
        }
        /*创建provider和consumer*/
        Map<String, String> params = url.getParams();
        if(MapUtils.isNotEmpty(params)){
            String providers = params.get(Constants.PROVIDERS);
            if (StringUtils.isNotEmpty(providers)){
                if(!zkClient.exists(path+DELIMETER+providers)){
                    zkClient.createEphemeral(path+DELIMETER+providers);
                }
            }
        }




    }

    public static void main(String[] args) {
       ZookeeperRegister register = new ZookeeperRegister();
    }

    /*将URL对象转换成注册到*/
    public String toURLPath(URL url){
        boolean first = true;
        StringBuilder urlBuilder = new StringBuilder(NAGI);
        urlBuilder.append("://").append(url.getHost()).append(":").append(port).append(DELIMETER).append(url.getPath());

        Map<String, String> params = url.getParams();
        Set<String> keySets = params.keySet();

        for(String key:keySets){
            String param = params.get(key);
            if (first){
                urlBuilder.append("?").append(key).append("=").append(param);
            }else{
                urlBuilder.append("&").append(key).append("=").append(param);
            }

        }

        return urlBuilder.toString();
    }

}
