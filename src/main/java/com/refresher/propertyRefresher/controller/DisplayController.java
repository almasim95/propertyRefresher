package com.refresher.propertyRefresher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.refresher.propertyRefresher.properties.AppProperties;
@RestController
public class DisplayController {
	@Autowired
	private AppProperties props;
	
	@GetMapping("/prop1")
	public String getFirstPropety() {
		return "first.dynamic.prop=" + props.getFirstDynamicPropery();
	}
	
	@GetMapping("/prop2")
	public String getSecondPropety() {
		return "second.dynamic.prop=" + props.getSecondDyanimicProperty();
	}
	
	@GetMapping("/")
	public String getBothProperties() {
		return this.getFirstPropety() + " <----------------------------> " + this.getSecondPropety();
	}
	
}
