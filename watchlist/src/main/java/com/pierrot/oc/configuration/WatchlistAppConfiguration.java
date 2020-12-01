package com.pierrot.oc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
// DOESN'T WORK!!!
//@ImportResource(locations = {"classpath*:/*-config.xml"})
//DOESN'T WORK TOO!!!
//@ImportResource(locations = {"classpath:com/pierrot/oc/*-config.xml"})

@ImportResource(locations = {"classpath:com/pierrot/oc/**/*-config.xml"})
public class WatchlistAppConfiguration {

}
