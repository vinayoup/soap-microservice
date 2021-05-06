package com.oup.integration.db;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerConfiguration {
	private final static List<Customer> customers=new ArrayList<>();
	static {
		customers.add(new Customer("Amazon",true));
		customers.add(new Customer("Akademica",true));
	}
	public static List<Customer> getCustomers()
	{
		return customers;
	}
}

