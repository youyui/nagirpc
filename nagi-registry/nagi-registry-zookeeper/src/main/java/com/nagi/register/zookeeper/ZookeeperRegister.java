package com.nagi.register.zookeeper;

import com.nagi.common.Constants;
import com.nagi.common.URL;
import com.nagi.common.URLEncoder;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
        String service = NAGI + DELIMETER+url.getService();
        /*服务节点目录 providers consumers configurations*/
        registerServicePath(service);
        /*获得URL里的参数，做相关注册处理*/
        Map<String, String> params = url.getParams();
        try {
            // /nagi/service
            String prePath = DELIMETER+NAGI+DELIMETER+service;
            //注册provider
            if(StringUtils.isNotEmpty(params.get(Constants.PROVIDER))){
                String providerPath = prePath+DELIMETER+Constants.PROVIDERS;// /nagi/service
                String sUrlPath =  URLEncoder.encode(providerPath+DELIMETER+toURLPath(url),"utf-8") ;//对URL加密
                if(!zkClient.exists(sUrlPath)){
                    zkClient.createEphemeral(sUrlPath);
                }
            }
            //注册consumer
            if(StringUtils.isNotEmpty(params.get(Constants.CONSUMER))){
                String consumerPath = prePath+DELIMETER+Constants.CONSUMERS;
                String sUrlPath =  URLEncoder.encode(consumerPath+DELIMETER+toURLPath(url),"utf-8") ;
                if(!zkClient.exists(Constants.CONSUMERS+DELIMETER+Constants.CONSUMERS+DELIMETER+sUrlPath)){
                    zkClient.createEphemeral(service+DELIMETER+Constants.CONSUMERS+DELIMETER+sUrlPath);
                }
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
    /*如果是新的service，创建相关路径*/
    public void registerServicePath(String service){
        String prePath = NAGI+DELIMETER+service;
        if(!zkClient.exists(prePath)){
            zkClient.createPersistent(prePath);//创建服务目录
            /*创建相关目录*/
            zkClient.createPersistent(prePath+DELIMETER+Constants.PROVIDERS);//服务提供者
            zkClient.createPersistent(prePath+DELIMETER+Constants.CONSUMERS);//服务消费者
            zkClient.createPersistent(prePath+DELIMETER+Constants.CONFIGURATORS);//rpc相关配置
        }
        //注册相关URL


    }

    /*将URL对象转换成注册到*/
    public String toURLPath(URL url){
        boolean first = true;
        url.getParams();


        StringBuilder urlBuilder = new StringBuilder("");
        urlBuilder.append("://").append(url.getHost()).append(":").append(port).append(DELIMETER).append(url.getService());

        Map<String, String> params = url.getParams();
        Set<String> keySets = params.keySet();

        for(String key:keySets){
            String param = params.get(key);
            if (first){
                urlBuilder.append("?").append(key).append("=").append(param);//第一个为？分隔
            }else{
                urlBuilder.append("&").append(key).append("=").append(param);
            }
        }
        return urlBuilder.toString();
    }
    public static void main(String[] args) {
        ZookeeperRegister register = new ZookeeperRegister();

        URL url = new URL("nagi","172.20.10.2",13330,
                "com.nagi.rpc.service.OrderService", "com.nagi.rpc.service.OrderService",null);
        Map<String,String> params = new HashMap<String, String>();

        register.registerURL(url);

    }
}
