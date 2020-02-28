package com.moneytransfer.utils;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class JsonUtil {
  
  public static String toJson(Object object) {
    return new Gson().toJson(object);
  }
 
  public static ResponseTransformer json() {
    return JsonUtil::toJson;
  }
  
  public static <T> T fromJson​(java.lang.String json, java.lang.Class<T> classOfT)
  {
    return new Gson().fromJson(json, classOfT);
  }
}
