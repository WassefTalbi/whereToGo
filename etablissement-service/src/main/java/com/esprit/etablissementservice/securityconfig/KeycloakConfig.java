package com.esprit.etablissementservice.securityconfig;

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
                    .grantType(OAuth2Constants.PASSWORD)
                    .username("super-admin")
                    .password("superadmin")
                    .clientId("login-app")
                    .clientSecret("B8CNAMfaplvu1ng5FFn7MUnmcCetR4Yk")

                    .build();

        }
        return keycloak;
    }

}
