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
}
