package org.bankingproto.test.models.client;

import org.bankingproto.generated.TransferRequest;
import org.bankingproto.generated.TransferResponse;
import org.bankingproto.service.TransferService;
import org.bankingproto.test.models.common.AbstractTest;
import org.bankingproto.test.models.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BiDirectionalStreamingTest extends AbstractTest {

    @Test
    public void transferTest(){
        var responseObserver = ResponseObserver.< TransferResponse >create();

        var requestObserver =   this.transferServiceStub.transfer(responseObserver) ;

        var requests = List.of(
                TransferRequest.newBuilder().setAmount(10).setFromAccount(6).setToAccount(6).build(),
                TransferRequest.newBuilder().setAmount(110).setFromAccount(6).setToAccount(7).build(),
                TransferRequest.newBuilder().setAmount(10).setFromAccount(6).setToAccount(7).build(),
                TransferRequest.newBuilder().setAmount(10).setFromAccount(7).setToAccount(6).build()
        );

        requests.forEach(requestObserver::onNext);
        requestObserver.onCompleted();

        responseObserver.await();


        Assertions.assertEquals(4, responseObserver.getItems().size());


    }


}
