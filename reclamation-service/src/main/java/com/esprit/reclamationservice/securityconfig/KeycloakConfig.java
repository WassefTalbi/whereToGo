package com.esprit.reclamationservice.securityconfig;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;


@Slf4j
public class KeycloakConfig {

    private static Keycloak keycloak = null;


    public KeycloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:9090/")
                    .realm("whereToGo")
                    .grantType(OAuth2Constants.PASSWORD)
                    .username("super-admin")
                    .password("superadmin")
                    .clientId("login-app")
                    .clientSecret("KCkLConBOMbkIsOjEnWBmzokgUHE1hzn")
                    .build();
        }
        return keycloak;
    }

}
