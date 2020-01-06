package za.co.ajk.apitest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.ajk.apitest.models.WalletBalanceAdjust;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/wallet")
public class WalletController {

    @GetMapping
    public ResponseEntity<?> getWallets(){
        log.debug("Reached getWallets");
        return ResponseEntity.ok("Reached getWallets");
    }

    @GetMapping("/balance/{wallet_id}")
    public ResponseEntity<?> getWalletBalance(@PathVariable int wallet_id){
        log.debug("Reached getWallet balance for walletId : ", wallet_id);
        return ResponseEntity.ok("Reached getWallet balance for walletId : "+wallet_id);
    }

    @PostMapping("/balance")
    public ResponseEntity<?> updateWalletBalance(@RequestBody WalletBalanceAdjust walletBalanceAdjust ){

        log.debug("Reached updateWalletBalance for : {} ", walletBalanceAdjust);

        walletBalanceAdjust.setNewBalance(new BigDecimal("50000.21"));
        return ResponseEntity.ok(walletBalanceAdjust);
    }
}
