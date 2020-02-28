package com.moneytransfer.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class TestResponse {

  public final String body;
  public final int status;

  public TestResponse(int status, String body) {
      this.status = status;
      this.body = body;
  }

  public Map<String,Object> json() {
      return new Gson().fromJson(body, HashMap.class);
  }
  
  public static <T> T fromJson​(java.lang.String json, java.lang.Class<T> classOfT)
  {
    return new Gson().fromJson(json, classOfT);
  }

}
