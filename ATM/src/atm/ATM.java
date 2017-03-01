/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm;

import java.util.Scanner;

/**
 *
 * @author Loh
 */
public class ATM {

    private static final Scanner scanner = new Scanner(System.in);
    private static int total_account = 0;
    private static Account accounts[];
    private static Client clients[];

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                                 APU ATM SIMULATOR");
        System.out.println("-----------------------------------------------------------------------------------\n");

        create_account();

        for (int member_no = 0; member_no < total_account; member_no++) {
            initial_balance(member_no);
        }
        System.out.println("\n");
        for (int member_no = 0; member_no < total_account; member_no++) {
            menu(member_no);
        }
        
        try{

        for(Client client :clients){
            client.start();
        }
        
        for(Client client :clients){
            client.join();
        }
        
        System.out.println("\n=> BALANCE");
        
        for(int i=0;i<total_account;i++)
        {
            System.out.println("** Account "+i+" : "+accounts[i].getBalance());
        }
        
        
        }catch(Exception e){}
    }

    public static void menu(int member_no) {
        int activity;
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                                 ACCOUNT " + member_no );
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                                 ACTIVITY SCENARIO");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                                 0.TRANSFER");
        System.out.println("                                 1.WITHDRAW");
        System.out.println("                                 2.DEPOSIT");
        System.out.println("-----------------------------------------------------------------------------------\n");

        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Select your activity : ");
            if (scanner.hasNextInt()) {
                activity = scanner.nextInt();
                if (activity >= 0 && activity <= 2) {
                    switch (activity) {
                        case 0:
                            transaction(member_no);
                            break;
                        case 1:
                            withdraw(member_no);
                            break;
                        case 2:
                            deposit(member_no);
                            break;

                    }
                    invalidInput = false;
                } else {
                    System.out.println("** You have not entered a number between 0 and 2, try again.");
                }

            } else {

                System.out.println("** You have entered an invalid input, try again.");
                scanner.next();
            }
        }

    }
    
    public static void create_account() {

        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> How many account you want to create : ");

            if (scanner.hasNextInt()) {
                total_account = scanner.nextInt();
                if (total_account > 0) {
                    accounts = new Account[total_account];
                    clients = new Client[total_account];
                    System.out.println("** " + total_account + " account created\n");
                    invalidInput = false;
                } else {
                    System.out.println("** You have not entered a possitive number, try again.");
                }

            } else {
                System.out.println("** Only accept integer number, try again");
                scanner.next(); // clear the invalid input from scanner
            }
        }

    }

    public static void initial_balance(int member_no) {

        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Enter initial balance for account " + member_no + " :  ");
            if (scanner.hasNextDouble()) {
                double balance = scanner.nextDouble();
                if (balance > 0) {
                    accounts[member_no] = new Account(member_no, balance);
                    invalidInput = false;
                } else {
                    System.out.println("** You have not entered a possitive number, try again.");
                }
            } else {
                System.out.println("** Invalid balance input, try again");
                scanner.next();
            }
        }
    }

    public static void transaction(int member_no) {

        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Enter destination account id : " );
            if (scanner.hasNextInt()) {
                int transfer_destination_acc_id = scanner.nextInt();

                if (transfer_destination_acc_id < total_account) {
                    boolean invalidInput2 = true;
                    while (invalidInput2) {
                        System.out.print("=> Enter amount to transfer : ");
                        if (scanner.hasNextDouble()) {
                            double amount = scanner.nextDouble();
                            clients[member_no] = new Client(accounts[member_no], accounts[transfer_destination_acc_id], amount, pin(member_no), hardware(member_no), cancel(member_no),0);
                            invalidInput2 = false;
                        } else {
                            System.out.println("** You have entered an invalid input, try again.");
                            scanner.next();
                        }
                    }
                    invalidInput = false;
                } else {
                    System.out.println("** No such account, try again");
                }

            } else {
                System.out.println("** You have entered an invalid input, try again.");
                scanner.next();
            }
        }

    }
    
    public static void withdraw(int member_no) {
        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Enter amount to withdraw : ");
            if (scanner.hasNextDouble()) {
                double amount = scanner.nextDouble();
                clients[member_no] = new Client(accounts[member_no],amount, pin(member_no), hardware(member_no), cancel(member_no), 1);
                invalidInput = false;
            } else {
                System.out.println("** You have entered an invalid input, try again.");
                scanner.next();
            }

        }
    }
    
    public static void deposit(int member_no) {
        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Enter amount to deposit : ");
            if (scanner.hasNextDouble()) {
                double amount = scanner.nextDouble();
                clients[member_no] = new Client(accounts[member_no], amount, pin(member_no), hardware(member_no), cancel(member_no), 2);
                invalidInput = false;
            } else {
                System.out.println("** You have entered an invalid input, try again.");
                scanner.next();
            }

        }
    }
    
    public static int pin(int member_no) {
        System.out.print("\n");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("                                  SCENARIO");
        System.out.println("-----------------------------------------------------------------------------------\n");

        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Set pin scenario (1. Correct Pin 2.Wrong Pin) :  ");
            if (scanner.hasNextDouble()) {
                int pin_answer = scanner.nextInt();
                if (pin_answer >= 1 && pin_answer <= 2) {
                    invalidInput = false;
                    return pin_answer;
                } else {
                    System.out.println("** You have not entered a number between 1 and 2, try again.");
                }
            } else {
                System.out.println("** You have entered an invalid input, try again.");
                scanner.next();
            }

        }
        return 0;
    }

    public static int hardware(int member_no) {

        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Set hardware situation scenario (1. Normal 2. Failure) :  ");
            if (scanner.hasNextDouble()) {
                int hardware_answer = scanner.nextInt();
                if (hardware_answer >= 1 && hardware_answer <= 2) {
                    invalidInput = false;
                    return hardware_answer;
                } else {
                    System.out.println("** You have not entered a number between 1 and 2, try again.");
                }
            } else {
                System.out.println("** You have entered an invalid input, try again.");
                scanner.next();
            }
        }

        return 0;

    }

    public static int cancel(int member_no) {

        boolean invalidInput = true;
        while (invalidInput) {
            System.out.print("=> Set cancel button scenario (1. No 2. Yes):  ");
            if (scanner.hasNextDouble()) {
                int cancel_answer = scanner.nextInt();
                if (cancel_answer >= 1 && cancel_answer <= 2) {
                    invalidInput = false;
                    System.out.println("** ACCOUNT " + member_no + " FINISH");
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
                    return cancel_answer;
                } else {
                    System.out.println("** You have not entered a number between 1 and 2, try again.");
                }
            } else {
                System.out.println("** You have entered an invalid input, try again.");
                scanner.next();
            }

        }
        return 0;
    }
    
  

}
