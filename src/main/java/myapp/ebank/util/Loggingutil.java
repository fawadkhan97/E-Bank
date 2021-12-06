package myapp.ebank.util;


import org.json.JSONObject;

import java.util.HashMap;

public class Loggingutil {
    public static void logging(Object request, Object response, String serviceName) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        JSONObject jsonObjectRequest = new JSONObject(request);
        JSONObject jsonObjectResponse = new JSONObject(response);

     /*   System.out.println("json object request is " + jsonObjectRequest);
        System.out.println("json object response is " + jsonObjectResponse);
        System.out.println("json object request to string is " + jsonObjectRequest.toString());
        System.out.println("json object response to string  is " + jsonObjectResponse.toString());*/
        dataMap.put("requestobject", jsonObjectRequest.toString());
        dataMap.put("responseobject", jsonObjectResponse.toString());
        if (serviceName != null) {
            dataMap.put("servicename", serviceName.toString());
        }

        dataMap.put("sourceapi", "ApplicantPortalApi");
        dataMap.put("sourceapp", "ApplicantPortal");
        try {
                dataMap.put("sourceappid", jsonObjectRequest.get("usErId".toLowerCase()));
            }
        catch (Exception e){
            dataMap.put("sourceappid", jsonObjectRequest.get("id".toLowerCase()));

            System.out.println("request object is "+request.toString());
        }

//        dataMap.put("sourceappid", jsonObjectResponse.get("userId"));
        dataMap.put("sourceappip", null);

        if (jsonObjectResponse != null && jsonObjectResponse.length() != 0 && "100".equalsIgnoreCase((String) jsonObjectResponse.get("code"))) {
            dataMap.put("status", "Success");
            dataMap.put("errordetail", null);
        } else {
            dataMap.put("status", "Failed");
            dataMap.put("errordetail", jsonObjectResponse.get("message"));
        }

    }
}
