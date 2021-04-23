package com.example.twistermandatoryassignment.webapi;

public class ApiUtils {
    private ApiUtils(){
    }

    private static final String BASE_URL = "https://anbo-restmessages.azurewebsites.net/api/";

    public static TwisterService getTwisterService() {
        return RetrofitClient.getClient(BASE_URL).create(TwisterService.class);
    }
}
