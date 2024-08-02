package org.bankingproto.test.models.common;

import org.bankingproto.common.GrpcServer;
import org.bankingproto.generated.BankServiceGrpc;
import org.bankingproto.generated.TransferServiceGrpc;
import org.bankingproto.service.BankService;
import org.bankingproto.service.TransferService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest extends  AbstractChannelTest{
    public static final GrpcServer grpcServer = GrpcServer.create(new BankService());
    protected BankServiceGrpc.BankServiceBlockingStub blockingStub ;
    protected BankServiceGrpc.BankServiceStub bankServiceStub ;
    protected TransferServiceGrpc.TransferServiceStub transferServiceStub ;


    @BeforeAll
    public void setup(){
        this.grpcServer.start();
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankServiceStub = BankServiceGrpc.newStub(channel) ;
        this.transferServiceStub = TransferServiceGrpc.newStub(channel) ;
    }

    @AfterAll
    public void stop(){
        this.grpcServer.stop() ;
    }
}
