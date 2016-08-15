package controllers;

import java.util.Date;
import java.util.Random;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.Logger;
import play.libs.F;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

public class Application extends Controller {

	private static final String KEY_WHEELCHAIR = "wheelchair";
	private static final String KEY_ARRIVE_BY = "arriveBy";
	private static final String KEY_MAX_WALK_DISTANCE = "maxWalkDistance";
	private static final String KEY_DATE = "date";
	private static final String KEY_TIME = "time";
	private static final String KEY_TO_PLACE = "toPlace";
	private static final String KEY_FROM_PLACE = "fromPlace";
	private static final String KEY_PRICE = "price";
	private static final String KEY_TOTAL_PRICE = "totalPrice";
	private static final String KEY_QUOTES = "\"";
	private static final String KEY_COORDINATE_SEPARATOR = ",";
	private static final String BASE_URL = "http://5.189.168.220:4567/PlannerAPI";
	private static final int MAX_PRICE = 5;
	private static final int MIN_PRICE = 3;
	
	private static Random randomGenerator = new Random();

	@Inject
	private WSClient ws;

	public Result index() {
		return ok("Server is up and running. Fire Away!");
	}

	public F.Promise<Result> getRoutes() {

		WSRequest complexRequest = ws.url(BASE_URL)
				.setRequestTimeout(1000)
				.setQueryParameter(KEY_FROM_PLACE, "\"52.52656141115588,13.358001708984375\"")
				.setQueryParameter(KEY_TO_PLACE, "\"52.493233155027156,13.422374725341797\"")
				.setQueryParameter(KEY_TIME, "\"05:13:00pm\"")
				.setQueryParameter(KEY_DATE, "\"10-20-2015\"")
				.setQueryParameter(KEY_MAX_WALK_DISTANCE, "\"800\"")
				.setQueryParameter(KEY_ARRIVE_BY, "\"false\"")
				.setQueryParameter(KEY_WHEELCHAIR, "\"false\"");

		F.Promise<WSResponse> apiResponse = complexRequest.get();

		return apiResponse.map(response -> ok(response.asJson()));

	}

	public F.Promise<Result> getInineraries(String fromX, String fromY, String toX, String toY,
											String journeyTime, String journeyDate, Integer maxWalkDistance, 
											Boolean arriveBy, Boolean wheelchair) {

		WSRequest complexRequest = ws.url(BASE_URL)
				.setRequestTimeout(1000)
				.setQueryParameter(KEY_FROM_PLACE, KEY_QUOTES + fromX + KEY_COORDINATE_SEPARATOR + fromY + KEY_QUOTES)
				.setQueryParameter(KEY_TO_PLACE, KEY_QUOTES + toX + KEY_COORDINATE_SEPARATOR + toY + KEY_QUOTES)
				.setQueryParameter(KEY_TIME, KEY_QUOTES + journeyTime + KEY_QUOTES)
				.setQueryParameter(KEY_DATE, KEY_QUOTES + journeyDate + KEY_QUOTES)
				.setQueryParameter(KEY_MAX_WALK_DISTANCE, KEY_QUOTES + maxWalkDistance + KEY_QUOTES)
				.setQueryParameter(KEY_ARRIVE_BY, KEY_QUOTES + arriveBy + KEY_QUOTES)
				.setQueryParameter(KEY_WHEELCHAIR, KEY_QUOTES + wheelchair + KEY_QUOTES);

		F.Promise<WSResponse> apiResponse = complexRequest.get();

		return apiResponse.map(response -> ok(randomisePriceForItineraries(response.asJson().findPath("itineraries"))));

	}

	private JsonNode randomisePriceForItineraries(JsonNode itineraries) {
		if (itineraries.isArray()) {
			for (JsonNode itinerary : itineraries) {
				generateRandomLegPrices(itinerary);
			}
		}
		return itineraries;
	}

	private void generateRandomLegPrices(JsonNode itinerary) {
		int totalPrice = 0;
		if(itinerary.findPath("legs").isArray()){
				for (JsonNode leg : itinerary.findPath("legs")) {
					if(!leg.findValue("mode").asText().toUpperCase().equals("WALK")) {
						int price = getRandomPrice(MIN_PRICE, MAX_PRICE);
						((ObjectNode)leg).put(KEY_PRICE, price);
						totalPrice += price;
					}
				}
		}
		((ObjectNode)itinerary).put(KEY_TOTAL_PRICE, totalPrice);
	}

	public static int getRandomPrice(int min, int max) {
		int randomNum = randomGenerator.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
