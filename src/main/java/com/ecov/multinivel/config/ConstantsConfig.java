package com.ecov.multinivel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantsConfig {
    private final int workGroupIdAdmin = 1;
    private final int workGroupIdAffiliate = 2;
    @Value("${url.front}")
    private String urlFront;
    public int getWorkGroupIdAffiliate() {
        return  this.workGroupIdAffiliate;
    }
    public int getWorkGroupIdAdmin() { return this.workGroupIdAdmin;}
    public String getUrlFront(){ return this.urlFront;}
}
