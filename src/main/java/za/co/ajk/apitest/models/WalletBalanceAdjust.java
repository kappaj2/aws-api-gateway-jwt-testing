package za.co.ajk.apitest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletBalanceAdjust implements Serializable {

    private int walletId;
    private BigDecimal newBalance;

}
