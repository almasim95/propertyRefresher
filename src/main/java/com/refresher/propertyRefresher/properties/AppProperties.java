package com.refresher.propertyRefresher.properties;

import org.springframework.stereotype.Component;

@Component
public class AppProperties extends ReloadableProperties {

    public String getFirstDynamicPropery() {
        return environment.getProperty("first.dynamic.prop");
    }
    public String getSecondDyanimicProperty() {
        return environment.getProperty("second.dynamic.prop");    
    }
    @Override
    protected void propertiesReloaded() {
        System.out.println("Properties have been reloaded!");
    }
}