package com.automation.route.routemaster.common;

public enum CommonUrl {
  NAVER("https://naveropenapi.apigw.ntruss.com");

  private final String url;

  CommonUrl (String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
