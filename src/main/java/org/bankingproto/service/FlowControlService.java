package org.bankingproto.service;

import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.FlowControlServiceGrpc;
import org.bankingproto.generated.Output;
import org.bankingproto.generated.RequestSize;
import org.bankingproto.requesthandlers.FlowControlServiceRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class FlowControlService extends FlowControlServiceGrpc.FlowControlServiceImplBase {
    public static final Logger logger = LoggerFactory.getLogger(FlowControlService.class) ;

    @Override
    public StreamObserver<RequestSize> getMessages(StreamObserver<Output> responseObserver) {
        return new FlowControlServiceRequestHandler(responseObserver);
    }
}
