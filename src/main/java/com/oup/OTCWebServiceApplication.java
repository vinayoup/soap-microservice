package com.oup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class OTCWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OTCWebServiceApplication.class, args);

	}
	@Bean
	public Marshaller jaxbMarshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setPackagesToScan("com.sap.document.sap.rfc.functions");
		Map<String, Object> properties = new HashMap<>();
		properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxb2Marshaller.setMarshallerProperties(properties);
		Marshaller marshaller = jaxb2Marshaller.createMarshaller();
		return marshaller;
	}
}
