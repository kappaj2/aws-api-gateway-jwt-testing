package za.co.ajk.apitest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@Service
public class CognitoCommunicatorService {

    private static final int PAYLOAD = 1;
    private static final int JWT_PARTS = 3;

    public static final String ISS = "iss";
    public static final String JWK_URl_SUFFIX = "/.well-known/jwks.json";

    private ObjectMapper objectMapper;

    public CognitoCommunicatorService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JWTClaimsSet extractJWTClaimSet(String jwtToken) {

        try {
            String jsonWebKeyFileURL = getJsonWebKeyURL(jwtToken);
            ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
            JWKSource jwkSource = new RemoteJWKSet(new URL(jsonWebKeyFileURL));
            JWSAlgorithm jwsAlgorithm = JWSAlgorithm.RS256;
            JWSKeySelector keySelector = new JWSVerificationKeySelector(jwsAlgorithm, jwkSource);
            jwtProcessor.setJWSKeySelector(keySelector);

            JWTClaimsSet claimsSet = jwtProcessor.process(jwtToken, null);

            log.info("JWTClaimset is : {}", objectMapper.writeValueAsString(claimsSet));
            return claimsSet;

        } catch (BadJWTException e) {
            log.error("Bad JWT");
        } catch (java.text.ParseException ex) {
            log.error("Parsing error ");
        } catch (Exception ex) {
            log.error("Error not handled " + ex.getMessage());
        }
        return null;
    }

    protected JsonNode getPayload(String jwt) throws Exception {

        validateJWT(jwt);
        final String payload = jwt.split("\\.")[PAYLOAD];
        final byte[] payloadBytes = Base64.getUrlDecoder().decode(payload);
        final String payloadString = new String(payloadBytes, "UTF-8");
        return objectMapper.readTree(payloadString);

    }

    protected void validateJWT(String jwt) {
        // Basic validation to check if token has three parts.
        final String[] jwtParts = jwt.split("\\.");
        if (jwtParts.length != JWT_PARTS) {
            log.error("Not enough parts");
        }
    }

    protected String getJsonWebKeyURL(String token) throws Exception {

        JsonNode payload = getPayload(token);
        JsonNode issJsonElement = getPayload(token).get(ISS);
        if (Objects.isNull(issJsonElement)) {
            log.error("Invalid JWT token");
        }
        return issJsonElement.asText() + JWK_URl_SUFFIX;
    }
}
