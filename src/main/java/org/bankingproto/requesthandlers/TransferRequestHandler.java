package org.bankingproto.requesthandlers;

import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.AccountBalance;
import org.bankingproto.generated.TransferRequest;
import org.bankingproto.generated.TransferResponse;
import org.bankingproto.generated.TransferStatus;
import org.bankingproto.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TransferRequestHandler implements StreamObserver<TransferRequest> {
    public static final Logger logger = LoggerFactory.getLogger(TransferRequestHandler.class);

     private final StreamObserver<TransferResponse> responseObserver ;

    public TransferRequestHandler(StreamObserver<TransferResponse> responseObserver) {
        this.responseObserver = responseObserver ;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {
        var status = this.transfer(transferRequest) ;

        if(status == TransferStatus.REJECTED){
            return;
        }

        var response = TransferResponse.newBuilder()
                .setFromAccount(this.toAccountBalance(transferRequest.getFromAccount()))
                .setToAccount(this.toAccountBalance(transferRequest.getToAccount()))
                .setStatus(status)
                .build() ;

        this.responseObserver.onNext(response);
    }

    @Override
    public void onError(Throwable throwable) {
        logger.error(throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        logger.info("transfer request stream completed");
        this.responseObserver.onCompleted();
    }


    private TransferStatus transfer(TransferRequest request){
        var amount = request.getAmount();
        var fromAccount = request.getFromAccount() ;
        var toAccount  = request.getToAccount() ;
        var status = TransferStatus.REJECTED ;

        if(AccountRepository.getBalance(fromAccount) >= amount && fromAccount != toAccount ){
            AccountRepository.deductAmount(fromAccount, amount);
            AccountRepository.addAmount(toAccount, amount);
            status = TransferStatus.COMPLETED ;
        }

        return status ;
    }


    private AccountBalance toAccountBalance(int accountNumber){
        return AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(AccountRepository.getBalance(accountNumber))
                .build();
    }
}
