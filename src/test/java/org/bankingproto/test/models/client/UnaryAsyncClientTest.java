package org.bankingproto.test.models.client;


import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.AccountBalance;
import org.bankingproto.generated.BalanceCheckRequest;
import org.bankingproto.test.models.common.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class UnaryAsyncClientTest extends AbstractTest {
    public static final Logger logger = LoggerFactory.getLogger(UnaryAsyncClientTest.class) ;


    public void getBalanceTest() throws InterruptedException{
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build() ;
        var latch =  new CountDownLatch(1) ;

        this.stub.getAccountBalance(request, new StreamObserver<AccountBalance>() {
            @Override
            public void onNext(AccountBalance accountBalance) {
                logger.info("async balanced received {} ", accountBalance);

                try{
                    Assertions.assertEquals(100, accountBalance.getBalance());
                }
                finally {
                    latch.countDown(); //bro even if assertion fails, you have to end the prohgram, else it will be inf loop
                }


            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.toString());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.info("Completed");
                latch.countDown();
            }
        });

        latch.await();
    }
}
