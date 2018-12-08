package com.nagi.rpc.config.spring;

import com.nagi.rpc.config.spring.context.annotation.NagiComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@NagiComponentScan(packages = {"com.nagi.rpc"})
public class NagiBeanConfig {

}
