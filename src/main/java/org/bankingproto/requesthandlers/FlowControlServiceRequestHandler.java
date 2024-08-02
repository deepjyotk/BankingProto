package org.bankingproto.requesthandlers;

import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.Output;
import org.bankingproto.generated.RequestSize;
import org.bankingproto.service.FlowControlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlowControlServiceRequestHandler implements StreamObserver<RequestSize> {

    public static final Logger logger = LoggerFactory.getLogger(FlowControlServiceRequestHandler.class) ;
    public  final StreamObserver<Output> responseObserver ;
    private Integer emittedIndex;


    public FlowControlServiceRequestHandler(StreamObserver<Output> responseObserver){
        this.responseObserver = responseObserver ;
        this.emittedIndex = 0;
    }


    @Override
    public void onNext(RequestSize requestSize) {
        IntStream.rangeClosed(emittedIndex+1,100)
                .limit(requestSize.getSize())
                .forEach(i ->{
                    logger.info("emitting: {}",i);
                    responseObserver.onNext(Output.newBuilder().setValue(i).build());
                });

        emittedIndex += (requestSize.getSize()) ;

        if(emittedIndex>=100){
            responseObserver.onCompleted();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error(throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
    }
}
