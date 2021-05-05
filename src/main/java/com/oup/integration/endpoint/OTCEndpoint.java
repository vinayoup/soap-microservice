package com.oup.integration.endpoint;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sap.document.sap.rfc.functions.ZTESTSCHEMARequest;
import com.sap.document.sap.rfc.functions.ZTESTSCHEMAResponse;

@Endpoint
public class OTCEndpoint {
	@Autowired
	private Marshaller marshaller;
	private static final String NAMESPACE_URI = "urn:sap-com:document:sap:rfc:functions";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ZTEST_SCHEMARequest")
	@ResponsePayload
	public ZTESTSCHEMAResponse submitOTCSchema(@RequestPayload ZTESTSCHEMARequest request) throws JAXBException {
		marshaller.marshal(request, System.out);
		File file = new File("IN/output");
		marshaller.marshal(request, file);
		ZTESTSCHEMAResponse response = new ZTESTSCHEMAResponse();
		response.setHttpStatus("OK");
		response.setMessage("Succesfully submitted.");
		return response;
	}
}
