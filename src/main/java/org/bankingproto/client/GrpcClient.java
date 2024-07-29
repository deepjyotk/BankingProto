package org.bankingproto.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.AccountBalance;
import org.bankingproto.generated.BalanceCheckRequest;
import org.bankingproto.generated.BankServiceGrpc;
import org.bankingproto.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class GrpcClient {
    public static final Logger logger =  LoggerFactory.getLogger(GrpcClient.class);
    public static void main(String[] args) {

        var channel = ManagedChannelBuilder.forAddress("localhost",6565)
                    .usePlaintext()//this is there because we are not using secure connection
                    .build();

        var bankServiceStub = BankServiceGrpc.newStub(channel);

        bankServiceStub.getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(2).build(), new StreamObserver<AccountBalance>() {
            @Override
            public void onNext(AccountBalance accountBalance) {
                logger.info("{}",accountBalance);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                logger.info("completed");
            }
        });


        try {
            Thread.sleep(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
