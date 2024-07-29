package org.bankingproto.test.models.common;

import org.bankingproto.common.GrpcServer;
import org.bankingproto.generated.BankServiceGrpc;
import org.bankingproto.service.BankService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest extends  AbstractChannelTest{
    public static final GrpcServer grpcServer = GrpcServer.create(new BankService());
    protected BankServiceGrpc.BankServiceBlockingStub blockingStub ;
    protected BankServiceGrpc.BankServiceStub stub ;


    @BeforeAll
    public void setup(){
        this.grpcServer.start();
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.stub = BankServiceGrpc.newStub(channel) ;
    }

    @AfterAll
    public void stop(){
        this.grpcServer.stop() ;
    }
}
