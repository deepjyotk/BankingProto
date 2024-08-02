package org.bankingproto.test.models.client;

import org.bankingproto.generated.AccountBalance;
import org.bankingproto.generated.DepositRequest;
import org.bankingproto.generated.Money;
import org.bankingproto.test.models.common.AbstractTest;
import org.bankingproto.test.models.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;


public class ClientStreamingTest extends AbstractTest {

    @Test
    public void depositTest(){
        var responseObserver = ResponseObserver.< AccountBalance >create();
        var requestObserver = this.bankServiceStub.deposit(responseObserver);

        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());

        IntStream.rangeClosed(1,10)
                .mapToObj(i -> Money.newBuilder().setAmount(10).build())
                .map(m -> DepositRequest.newBuilder().setMoney(m).build())
                .forEach(requestObserver::onNext);

        requestObserver.onCompleted();

        responseObserver.await();

        Assertions.assertEquals(1, responseObserver.getItems().size());
        Assertions.assertEquals(200, responseObserver.getItems().getFirst().getBalance());
        Assertions.assertNull(responseObserver.getThrowable());
    }
}
