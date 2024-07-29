package org.bankingproto.test.models.client;

import com.google.protobuf.Empty;
import org.bankingproto.generated.BalanceCheckRequest;
import org.bankingproto.test.models.common.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UnaryBlockingClientTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(UnaryBlockingClientTest.class);


    @Test
    public void getBalanceTest(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();


        var balance = this.blockingStub.getAccountBalance(request);

        log.info("unary balance received : {} ",balance);

        Assertions.assertEquals(100, balance.getBalance());
    }


    @Test
    public void allAccountsStub(){
        var allAccounts = this.blockingStub.getAllAccounts(Empty.getDefaultInstance()) ;
        log.info("all accounts size: {}" , allAccounts.getAccountsCount());
        Assertions.assertEquals(10, allAccounts.getAccountsCount());
    }






}
