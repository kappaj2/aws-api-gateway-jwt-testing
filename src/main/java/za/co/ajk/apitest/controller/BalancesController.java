package za.co.ajk.apitest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/balances")
public class BalancesController {

    private static final String AUTH = "authorization";

    @Autowired
    private ExtractJWT extractJWT;

    @GetMapping
    public ResponseEntity<?> getAggregators(@RequestHeader Map<String, String> headers) {
        log.debug("Reached getAggregators");

        if(headers.containsKey(AUTH)){
            String jwt = headers.get(AUTH);
            extractJWT.extractJWTClaimsSetFromToken(jwt);
        }
        return ResponseEntity.ok("Reached getAggregators");
    }

    @GetMapping("/adjustments/{adjustment_id}/status")
    public ResponseEntity<?> getAdjustmentsStatus(@RequestHeader Map<String, String> headers, @PathVariable int adjustment_id) {
        log.debug("Reached get adjustment status for id : {}", adjustment_id);

        if(headers.containsKey(AUTH)){
            String jwt = headers.get(AUTH);
            extractJWT.extractJWTClaimsSetFromToken(jwt);
        }

        return ResponseEntity.ok("Reached getAdjustmentStatus for id :" + adjustment_id);
    }

    @GetMapping("/aggregators/{aggregator_id}")
    public ResponseEntity<?> getAggregator(@RequestHeader Map<String, String> headers, @PathVariable int aggregator_id) {
        log.debug("Reached get aggregator for id : {}", aggregator_id);

        if(headers.containsKey(AUTH)){
            String jwt = headers.get(AUTH);
            extractJWT.extractJWTClaimsSetFromToken(jwt);
        }

        return ResponseEntity.ok("Reached aggregator for id :" + aggregator_id);
    }
}


