package com.automation.route.routemaster.route.controller;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.automation.route.routemaster.common.CommonData;
import com.automation.route.routemaster.common.CommonUrl;

@Controller
@RequestMapping("/route")
public class RouteDataController {
  
  @ResponseBody
  @GetMapping("/naverPlaceInfo")
  public JSONObject getNaverPlaceInfo (@RequestParam("query") String query) {
    JSONParser jsonParser = new JSONParser();

    ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
    String encode = StandardCharsets.UTF_8.decode(buffer).toString();
    URI uri = UriComponentsBuilder.fromUriString(CommonUrl.NAVER.getUrl())
                                  .path("/map-geocode/v2/geocode")
                                  .queryParam("query", encode)
                                  .encode()
                                  .build()
                                  .toUri();

    RestTemplate restTemplate = new RestTemplate();

    RequestEntity<Void> req = RequestEntity.get(uri)
                                          .header("X-NCP-APIGW-API-KEY-ID", CommonData.NAVERID)
                                          .header("X-NCP-APIGW-API-KEY", CommonData.NAVERPW)
                                          .build();

    ResponseEntity<String> result = restTemplate.exchange(req, String.class);

    JSONObject obj = new JSONObject();

    try {
      obj = (JSONObject)jsonParser.parse( result.getBody() );
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return obj;
  }

  @ResponseBody
  @GetMapping("/naverRouteInfo")
  public JSONObject getNaverRouteInfo (@RequestParam("start") String start, @RequestParam("goal") String goal, @RequestParam("waypoints") String wayPoints) {
    JSONParser jsonParser = new JSONParser();

    ByteBuffer buffer = StandardCharsets.UTF_8.encode(start);
    String encodeStart = StandardCharsets.UTF_8.decode(buffer).toString();
    buffer = StandardCharsets.UTF_8.encode(goal);
    String encodeGoal = StandardCharsets.UTF_8.decode(buffer).toString();

    URI uri;

    if (wayPoints == null || wayPoints.isEmpty()) {
      uri = UriComponentsBuilder.fromUriString(CommonUrl.NAVER.getUrl())
                                    .path("/map-direction/v1/driving")
                                    .queryParam("start", encodeStart)
                                    .queryParam("goal", encodeGoal)
                                    .encode()
                                    .build()
                                    .toUri();
    } else {
      buffer = StandardCharsets.UTF_8.encode(wayPoints);
      String encodeWaypoints = StandardCharsets.UTF_8.decode(buffer).toString();
      uri = UriComponentsBuilder.fromUriString(CommonUrl.NAVER.getUrl())
                                    .path("/map-direction/v1/driving")
                                    .queryParam("start", encodeStart)
                                    .queryParam("goal", encodeGoal)
                                    .queryParam("waypoints", encodeWaypoints)
                                    .encode()
                                    .build()
                                    .toUri();
    }

    RestTemplate restTemplate = new RestTemplate();

    RequestEntity<Void> req = RequestEntity.get(uri)
                                          .header("X-NCP-APIGW-API-KEY-ID", CommonData.NAVERID)
                                          .header("X-NCP-APIGW-API-KEY", CommonData.NAVERPW)
                                          .build();

    ResponseEntity<String> result = restTemplate.exchange(req, String.class);

    JSONObject obj = new JSONObject();

    try {
      obj = (JSONObject)jsonParser.parse( result.getBody() );
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return obj;
  }
}
