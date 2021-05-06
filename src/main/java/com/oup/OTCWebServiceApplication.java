package com.oup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.oup.integration.db.Customer;
import com.oup.integration.db.CustomerConfiguration;

@SpringBootApplication
public class OTCWebServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OTCWebServiceApplication.class, args);
		createCustomersInFolder();

	}

	static void createCustomersInFolder() {
		List<Customer> customers = CustomerConfiguration.getCustomers();
		customers.forEach(customer -> {
			if (customer.getEnable()) {
				File file = new File(customer.getCustomerName());
				if (!file.exists()) {
					// Creating the directory
					boolean bool = file.mkdir();
					if (bool) {
						System.out.println("Directory created successfully");
					} else {
						System.out.println("Sorry couldn’t create specified directory");
					}
				}
			}

		});

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
