/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

/**
 *
 * @author Loh
 */
public class Client extends Thread{
    
      Account fromAccount;
      Account toAccount;
      double amount;
      int pin; 
      int hardware; 
      int cancel;
      int activity_no;

    public Client(Account fromAccount, Account toAccount, double amount, int pin, int hardware, int cancel, int activity_no) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.pin = pin;
        this.hardware = hardware;
        this.cancel = cancel;
        this.activity_no = activity_no;
    }

    public Client(Account fromAccount,double amount, int pin, int hardware, int cancel, int activity_no) {
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.pin = pin;
        this.hardware = hardware;
        this.cancel = cancel;
        this.activity_no = activity_no;
    }
    
    
    @Override
    @SuppressWarnings("empty-statement")
    
    public void run() {
        if (pin == 1) {
            if (hardware == 1) {
                if (cancel == 1) {
                    switch (activity_no) {
                        case 0:
                            while (fromAccount.transfer(amount, toAccount) == 0);
                            break;
                        case 1:
                            while (fromAccount.withdraw(amount) == 0);
                            break;
                        case 2:
                            while (fromAccount.deposit(amount) == 0);
                            break;
                    }

                } else {
                    System.out.println("** Account " + fromAccount.id + " pressened cancel button, transaction canceled");
                }
            } else {
                System.out.println("** Account " + fromAccount.id + " transaction canceled, hardware failure");
            }
        } else {
            System.out.println("** Account " + fromAccount.id + " transaction canceled, invalid pin number");
        }

    }
    
    
    
}
