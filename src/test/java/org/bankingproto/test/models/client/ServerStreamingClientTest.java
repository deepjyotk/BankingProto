package org.bankingproto.test.models.client;

import org.bankingproto.generated.Money;
import org.bankingproto.generated.WithdrawRequest;
import org.bankingproto.test.models.common.AbstractTest;
import org.bankingproto.test.models.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bankingproto.common.GrpcServer.create;


public class ServerStreamingClientTest extends AbstractTest {
    public static final Logger log = LoggerFactory.getLogger(ServerStreamingClientTest.class) ;


    @Test
    public void blockingClientWithdrawTest(){
        var request = WithdrawRequest.newBuilder().setAccountNumber(1).setAmount(20).build() ;


        //since we are receiving stream of money!

        var iteratorMoney  = this.blockingStub.withdraw(request) ;

        int count =0 ;
        while(iteratorMoney.hasNext()){
            log.info("{}", iteratorMoney.next());

            count++ ;
        }

        Assertions.assertEquals(2, count);
    }

    @Test
    public void asyncStub(){
        var request = WithdrawRequest.newBuilder().setAccountNumber(1).setAmount(20).build() ;
        var observer = ResponseObserver.<Money>create();
        this.stub.withdraw(request, observer);
        observer.await();
        Assertions.assertEquals(2, observer.getItems().size());
        Assertions.assertEquals(10, observer.getItems().getFirst().getAmount());
        Assertions.assertNull(observer.getThrowable());
    }

}
