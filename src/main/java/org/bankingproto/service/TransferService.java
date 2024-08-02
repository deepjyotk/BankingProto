package org.bankingproto.service;

import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.TransferRequest;
import org.bankingproto.generated.TransferResponse;
import org.bankingproto.generated.TransferServiceGrpc;
import org.bankingproto.requesthandlers.TransferRequestHandler;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver) ;
    }
}
