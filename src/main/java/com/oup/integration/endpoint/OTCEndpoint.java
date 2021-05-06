package com.oup.integration.endpoint;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.sap.document.sap.rfc.functions.ZTESTSCHEMA;
import com.sap.document.sap.rfc.functions.ZTESTSCHEMARequest;
import com.sap.document.sap.rfc.functions.ZTESTSCHEMAResponse;
import com.sap.document.sap.rfc.functions.ZTESTSCHEMAT;

@Endpoint
public class OTCEndpoint {
	@Autowired
	private Marshaller marshaller;
	
	private static final String NAMESPACE_URI = "urn:sap-com:document:sap:rfc:functions";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ZTEST_SCHEMARequest")
	@ResponsePayload
	public ZTESTSCHEMAResponse submitOTCSchema(@RequestPayload ZTESTSCHEMARequest request) throws JAXBException {
		Map<String, ZTESTSCHEMARequest> requestMap = new HashMap<>();
		List<ZTESTSCHEMA> items = request.getEXIN().getItem();
		List<ZTESTSCHEMA> distinctItems = items.stream().filter(distinctByKey(item -> item.getCUSTOMER()))
				.collect(Collectors.toList());
			
			distinctItems.forEach(item2->{
				ZTESTSCHEMARequest zRequest =new ZTESTSCHEMARequest();
				zRequest.setEXIN(new ZTESTSCHEMAT());
				zRequest.getEXIN().getItem().addAll(items.stream().filter(item->item.getCUSTOMER().equals(item2.getCUSTOMER()))
				.collect(Collectors.toList()));
				requestMap.put(item2.getCUSTOMER(), zRequest);
			});
			
		 requestMap.forEach((key, requestObject) -> {
			 File file = new File(key); 
			 try {
				marshaller.marshal(requestObject, file);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 });
		// File file = new File("IN/output"); marshaller.marshal(request, file);
		 ZTESTSCHEMAResponse response = new ZTESTSCHEMAResponse();
		 response.setHttpStatus("OK");
		 response.setMessage("Successfully submitted.");
		 
		return response;
	}

	// predicate to filter the duplicates by the given key extractor.
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> uniqueMap = new ConcurrentHashMap<>();
		return t -> uniqueMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}
