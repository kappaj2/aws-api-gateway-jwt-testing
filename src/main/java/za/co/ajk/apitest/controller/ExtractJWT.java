package za.co.ajk.apitest.controller;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.stereotype.Component;
import za.co.ajk.apitest.exceptions.JWTTokenException;
import za.co.ajk.apitest.service.CognitoCommunicatorService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ExtractJWT {

    private static final String ROLES_KEY = "cognito:groups";
    private static final String SCOPE_KEY = "scope";
    private static final String USERNAME_KEY = "username";
    private static final String CLIENT_ID_KEY = "client_id";

    private CognitoCommunicatorService cognitoCommunicator;

    public ExtractJWT(CognitoCommunicatorService cognitoCommunicator) {
        this.cognitoCommunicator = cognitoCommunicator;
    }

    public Optional<JWTClaimsSet> extractJWTClaimsSetFromToken(String jwtToken){
        JWTClaimsSet jwtClaimsSet = cognitoCommunicator.extractJWTClaimSet(jwtToken);
        return Optional.of(jwtClaimsSet);
    }

    public List<String> extractActiveRoles(String jwtToken) throws JWTTokenException {

        List<String> activeRoles = new ArrayList<>();

        Optional<JWTClaimsSet> cachedJWTClaimsSetOptional = extractJWTClaimsSetFromToken(jwtToken);

        if (cachedJWTClaimsSetOptional.isPresent()) {
            JWTClaimsSet jwtClaimsSet = cachedJWTClaimsSetOptional.get();
            Map<String, Object> claimsMap = jwtClaimsSet.getClaims();

            if (claimsMap.containsKey(ROLES_KEY)) {
                List<String> roles = (List<String>) claimsMap.get(ROLES_KEY);
                activeRoles.addAll(roles);
            }

            if (claimsMap.containsKey(SCOPE_KEY)) {
                String[] splitArray = ((String) jwtClaimsSet.getClaims().get("scope")).split(" ");
                List<String> roles = Arrays.stream(splitArray)
                        .map(s -> s.substring(s.indexOf("/") + 1))
                        .collect(Collectors.toList());
                activeRoles.addAll(roles);
            }
        }
        return activeRoles;
    }

    public String extractUserName(String jwtToken) throws JWTTokenException {

        Optional<JWTClaimsSet> cachedJWTClaimsSetOptional = extractJWTClaimsSetFromToken(jwtToken);

        if (cachedJWTClaimsSetOptional.isPresent()) {
            JWTClaimsSet jwtClaimsSet = cachedJWTClaimsSetOptional.get();
            if (jwtClaimsSet.getClaims().containsKey(USERNAME_KEY)) {
                return (String) jwtClaimsSet.getClaim(USERNAME_KEY);
            }
            if (jwtClaimsSet.getClaims().containsKey(CLIENT_ID_KEY)) {
                return (String) jwtClaimsSet.getClaim(CLIENT_ID_KEY);
            }
        }
        return null;
    }
}
