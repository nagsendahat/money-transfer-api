package com.moneytransfer.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Assert;

import com.google.gson.Gson;

import spark.utils.IOUtils;

public class TestRequest
{
  public static String toJson(Object object) {
    return new Gson().toJson(object);
  }
  
  public static TestResponse request(String method, String path, String json) {
    HttpURLConnection connection = null;
    OutputStream os = null;
    try {
        URL url = new URL("http://localhost:4567" + path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        if(json != null)
        {
          connection.setRequestProperty("Content-Type", "application/json; utf-8");
          connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
          connection.setRequestProperty("Accept","*/*");
          os = connection.getOutputStream();
          byte[] input = json.getBytes("utf-8");
          os.write(input, 0, input.length);           
        }
        connection.connect();
        String body = IOUtils.toString(connection.getInputStream());
        return new TestResponse(connection.getResponseCode(), body);
    } catch (IOException e) {
        e.printStackTrace();
        Assert.fail("Sending request failed: " + e.getMessage());
        return null;
    }
    finally 
    {
      if(connection != null) connection.disconnect();
      if(os != null) try
      {
        os.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
}
}
