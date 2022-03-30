package com.targa.labs.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.security.UnauthorizedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequestScoped
public class TokenService {
    @ConfigProperty(name="mp.jwt.verify.issuer",defaultValue = "undefined")
    Provider<String> jwtIssuerUrlProvider;

    @ConfigProperty(name = "keycloak.credentials.client-id", defaultValue = "undefined")
    Provider<String> clientIdProvider;

    public String getAccessToken(String userName, String password)
            throws IOException, InterruptedException {
        return getAccessToken(userName,password,null);
    }

    public String getAccessToken(String userName, String password, String clientSecret)
            throws IOException, InterruptedException {
        String keycloakTokenEndpoint=jwtIssuerUrlProvider.get()+
                "/protocol/openid-connect/token";
        String requestBody = "username=" + userName + "&password=" + password +
                "&grant_type=password&client_id=" + clientIdProvider.get();

        if (clientSecret != null) {
            requestBody += "&client_secret=" + clientSecret;
        }
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request=HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .uri(URI.create(keycloakTokenEndpoint))
                .header("Content-type","application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response= client.send(request, HttpResponse.BodyHandlers.ofString());
        String accessToken = "";
        if(response.statusCode() == 200){
            ObjectMapper mapper = new ObjectMapper();
            try{
                accessToken = mapper.readTree(response.body()).get("access_token").textValue();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            throw new UnauthorizedException();
        }
        return accessToken;
    }
}
