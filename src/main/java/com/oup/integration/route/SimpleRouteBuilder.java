package com.oup.integration.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class SimpleRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("file:IN").split().tokenize("\n")
		.log("${body}")
		.to("kafka:otc-interface?brokers=localhost:9092");
		
		
	}
}
