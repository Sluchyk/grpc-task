package com.example.grpctask.interceptors;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfig {
    @GrpcGlobalServerInterceptor
    public ServerInterceptor logServerInterceptor() {
        return new CustomServerInterceptor();
    }
}
