package com.targa.labs.web;

import com.targa.labs.security.TokenService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Collectors;

@Authenticated
@Path("/user")
public class UserResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    TokenService tokenService;

    @GET
    @Path("/current/info")
    public JsonWebToken getCurrentUserInfo(){
        return jwt;
    }

    @GET
    @Path("/current/info/claims")
    public Map<String,Object> getCurrentUserInfoClaims(){
        return jwt.getClaimNames().stream()
                .map(name -> Map.entry(name,jwt.getClaim(name)))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    //alternative
    @GET
    @Path("/current/info-alternative")
    public Principal getCurrentUserInfoAlternative(@Context SecurityContext ctx) {
        return ctx.getUserPrincipal();
    }

    @PermitAll
    @POST
    @Path("/access-token")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccessToken(@QueryParam("username") String username,
                                 @QueryParam("password") String password)
            throws IOException, InterruptedException {
        return tokenService.getAccessToken(username,password);
    }

}
