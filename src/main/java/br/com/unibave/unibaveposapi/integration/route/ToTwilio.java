package br.com.unibave.unibaveposapi.integration.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ToTwilio extends RouteBuilder{
	
	@Value("${token-twilio}")
	private String token;
	
	@Value("${phone-envio}")
	private String phoneDeEnvio;
	
	@Value("${endpoint-envio}")
	private String endpointDeEnvio;
	
	@Override
	public void configure() throws Exception {
		
		from("direct:enviarNotificacao")
			.doTry()
				.setHeader(Exchange.HTTP_METHOD, HttpMethods.POST)
				.setHeader("Authorization", simple(token))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/x-www-form-urlencoded"))
				.process(new Processor() {					
					@Override
					public void process(Exchange exchange) throws Exception {
						String bodyJson = exchange.getMessage().getBody(String.class);
						JSONObject bodyMap = new JSONObject(bodyJson);
						String bodyEncoded = "From=" + phoneDeEnvio 
								+ "&Body=" + bodyMap.getString("msg") 
								+ "&To=" + bodyMap.getString("phoneDeDestino");
						exchange.getMessage().setBody(bodyEncoded);
					}
				})
				.toD(endpointDeEnvio)
				.process(new Processor() {					
					@Override
					public void process(Exchange exchange) throws Exception {
						String bodyJson = exchange.getMessage().getBody(String.class);
						JSONObject bodyMap = new JSONObject(bodyJson);
						String id = bodyMap.getString("sid");
						exchange.getMessage().setBody(id);
					}
				})
			.doCatch(Exception.class)
				.setProperty("erro", simple("${exception}"))
				.process(new Processor() {					
					@Override
					public void process(Exchange exchange) throws Exception {
						exchange.getMessage().setBody(null);
						Exception ex = exchange.getProperty("erro", Exception.class);
						ex.printStackTrace();
					}
				})
		.end();		
	}

}
