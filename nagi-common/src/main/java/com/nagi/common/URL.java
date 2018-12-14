package com.nagi.common;

import java.io.Serializable;
import java.util.Map;
/**
 * @program: nagirpc
 * @descrition: 存放服务注册信息的类，协议，服务名，参数，ip，端口等
 * @author: Nagi
 * @create: 2018-12-13
 */
public class URL implements Serializable {

    private String protocol;//协议  默认 Nagi:
    private String host;//服务地址
    private int port;//端口
    private String path;//服务名
    private Map<String,String> params;//url参数

    protected URL(){
        this.protocol = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.params = null;
    }

    public URL(String protocol, String host, int port, String path, Map<String, String> params) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.params = params;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
