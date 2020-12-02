package com.pierrot.oc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:com/pierrot/oc/**/*-config.xml"})
public class WatchlistAppConfiguration {

}
