package com.aa.cdc.client;

import com.aa.cdc.server.api.UsrServiceApi;
import com.aa.cdc.server.dto.User;

public class ClientApplication {

    public static void main(String[] args) {
        UsrServiceApi api = new UsrServiceApi();
        User result = api.find("0");
        System.out.println(result);
        User result1 = api.find("1");
        System.out.println(result1);
    }
}
