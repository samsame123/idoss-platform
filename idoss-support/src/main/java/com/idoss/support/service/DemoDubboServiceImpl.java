package com.idoss.support.service;

import com.idoss.api.dubbo.DemoDubboService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by xiaoxuwang on 2019/6/14.
 */
@Service(version = "1.0.0")
public class DemoDubboServiceImpl implements DemoDubboService {

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

}
