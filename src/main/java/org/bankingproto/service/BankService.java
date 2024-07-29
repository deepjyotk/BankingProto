package org.bankingproto.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.bankingproto.generated.AccountBalance;
import org.bankingproto.generated.AllAccountsResponse;
import org.bankingproto.generated.BalanceCheckRequest;
import org.bankingproto.generated.BankServiceGrpc;
import org.bankingproto.repository.AccountRepository;

public class BankService extends BankServiceGrpc.BankServiceImplBase{

    // to support streaming response,we can support emitting values and go with streaming response.
    //grpc is generic, though here we want to send unary response from the server, grpc follows observer style.

    //ye server ki request client se mila aur 2nd observer server se mila...server is listening
    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber() ;
        var balance = AccountRepository.getBalance(accountNumber) ;

        var accountBalanceResponse = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build() ;


        responseObserver.onNext(accountBalanceResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver){
        var accountsBalanceResponse = AccountRepository.getAllAccounts()
                .entrySet()
                .stream()
                .map(e->AccountBalance.newBuilder().setAccountNumber(e.getKey()).setBalance(e.getValue()).build())
                .toList();

        var response = AllAccountsResponse.newBuilder().addAllAccounts(accountsBalanceResponse).build() ;

        responseObserver.onNext(response);

        responseObserver.onCompleted();
    }

    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        /*
        we should validate the input request,
           assumption: account# :1-10,and withdraw amount is multiple of 10$
        */

        var accountNumber = request.getAccountNumber();
        var requestedAmount = request.getAmount() ;

        var accountBalance = AccountRepository.getBalance(accountNumber) ;

        if(requestedAmount > accountBalance){
            responseObserver.onCompleted();
            return ;
        }

        else{
            for(int i=0;i<(requestedAmount/10) ; i++){
                var money = Money.newBuilder().setAmount(10).build() ;
                responseObserver.onNext(money);

                log.info("money sent is : {} ", money ) ;

                AccountRepository.deductAmount(accountNumber,10 );

                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            }

            responseObserver.onCompleted();
        }

    }

    @Override
    public StreamObserver<DepositRequest> deposit(StreamObserver<AccountBalance> responseObserver) {
        return new DepositRequestHandler(responseObserver) ;
    }
}
