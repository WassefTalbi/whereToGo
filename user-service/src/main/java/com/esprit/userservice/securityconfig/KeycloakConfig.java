package com.esprit.userservice.securityconfig;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;



@Slf4j
public class KeycloakConfig {

    static Keycloak keycloak = null;

    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:9090/")
                    .realm("whereToGo")
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)

                    .clientId("login-app")

                    .clientSecret("KCkLConBOMbkIsOjEnWBmzokgUHE1hzn")

                    .build();
            keycloak.tokenManager().getAccessToken();
        }
        return keycloak;
    }

}
