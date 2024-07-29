package org.bankingproto.requesthandlers;

import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.AccountBalance;
import org.bankingproto.generated.DepositRequest;
import org.bankingproto.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class DepositRequestHandler implements StreamObserver<DepositRequest> {

    private static final Logger log = LoggerFactory.getLogger(DepositRequestHandler.class);
    public final StreamObserver<AccountBalance> accountBalanceStreamObserver ;
    private int accountNumber ;

    public DepositRequestHandler(StreamObserver<AccountBalance> accountBalanceStreamObserver) {
        this.accountBalanceStreamObserver = accountBalanceStreamObserver;
    }



    @Override
    public void onNext(DepositRequest depositRequest) {
        log.info("received deposit {}", depositRequest);
        switch (depositRequest.getRequestCase()){
            case ACCOUNT_NUMBER : {
                this.accountNumber = depositRequest.getAccountNumber() ;
                break ;
            }
            case MONEY : { AccountRepository.addAmount(this.accountNumber, depositRequest.getMoney().getAmount());}

        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        var accountbalance = AccountBalance.newBuilder()
                .setAccountNumber(this.accountNumber)
                .setBalance(AccountRepository.getBalance(this.accountNumber))
                .build() ;
        this.accountBalanceStreamObserver.onNext(accountbalance);
        this.accountBalanceStreamObserver.onCompleted();
    }
}
