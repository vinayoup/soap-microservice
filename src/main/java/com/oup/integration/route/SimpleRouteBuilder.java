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

		//To check msg in topic run below command
		//C:\Development_Avecto\kafka\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic otc-interface --from-beginning
		
	}
}
