/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.util.concurrent.locks.*;
/**
 *
 * @author Loh
 */
public class Account {
    
    private double balance; 

    public double getBalance() {
        return balance;
    }

    
    int id;
 
    Lock lock= new ReentrantLock();
    
    public Account(int id, double balance){
        this.balance = balance;
        this.id = id;
       
    }
    
    //IF withdraw amount is larger than zero 
        //IF withdraw amount is less than or equal to the balance
            //IF lock is available, perform withdraw transaction, unlock the lock. Return 1 
            //ELSE lock is not available, exit the code. Return 0
        //ELSE prompt out error message, Return 2
    //ELSE prompt out error message, Return 2
    public int withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                if (lock.tryLock()) {
                    balance -= amount;
                    System.out.println("** Account " + id + " withdrew " + amount);
                    lock.unlock();
                    return (1);
                } else {
                    return (0);
                }

            } else {
                System.out.println("** Account " + id + " has not enough blance for " + amount + " ,withdraw fail!");
                return (2);

            }

        } else {
            System.out.println("** You have not entered a possitive number, withdraw fail!");
            return (2);
        }
    }

    //IF withdraw is success (1)
        //IF deposit to destination account is success (1), Return 1
    //ELSE IF withdraw has invalid amount input (2), prompt out error message, Return 1
    //ELSE client is busy, reimbursing the account, Return 0
    public int transfer(double amount, Account toAccount) {
        int withdraw = withdraw(amount);

        if (withdraw == 1) {
            if (toAccount.deposit(amount) == 1) {
                System.out.println("** Account " + id + " transfered " + amount + " to account " + toAccount.id);
                return (1);
            }
        } else if (withdraw == 2) {
            System.out.println("** Account " + id + " transaction fail! ");
            return (1);
        } else {
            System.out.println("** Account " + id + " reimbursing, client busy ");
            while (deposit(amount) == 0);
        }

        return (0);
    }
    
    //IF deposit amount is larger than zero 
        //IF lock is available, perform deposit transaction, unlock the lock. Return 1
        //ELSE lock is not available, exit the code. Return 0
    //ELSE prompt out error message, Return 2
    public int deposit(double amount) {
        if (amount > 0) {
            if (lock.tryLock()) {
                balance += amount;
                System.out.println("** Account " + id + " deposit " + amount);
                lock.unlock();
                return (1);
            } else {
                return (0);
            }
        } else {
            System.out.println("** You have not entered a possitive number, deposit fail!");
            return (2);
        }

    }
}
