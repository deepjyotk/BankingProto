package org.bankingproto.common;


import org.bankingproto.service.BankService;

public class Demo {

    public static void main(String[] args) {

        GrpcServer.create(6565, builder -> {
                    builder.addService(new BankService()) ;

                })
                .start()
                .await();

    }


    /*  Created for load balancing demo
    private static class BankInstance1 {
        public static void main(String[] args) {
            GrpcServer.create(6565, new BankService())
                      .start()
                      .await();
        }
    }

    private static class BankInstance2 {
        public static void main(String[] args) {
            GrpcServer.create(7575, new BankService())
                      .start()
                      .await();
        }
    }
    */
}