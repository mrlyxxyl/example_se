package com.yx.test.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * User: LiWenC
 * Date: 16-8-31
 */
public class AtomicTest {
    public static void main(String[] args) {
        Account account = new Account();
        account.setBalance(1000);

        Thread companyThread = new Thread(new Company(account));
        Thread bankThread = new Thread(new Bank(account));
        companyThread.start();
        bankThread.start();
        try {
            companyThread.join();
            bankThread.join();
            System.out.printf("Account : Final Balance: %d\n", account.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Account {
    private AtomicLong balance;

    Account() {
        this.balance = new AtomicLong();
    }

    public long getBalance() {
        return balance.get();
    }

    public void setBalance(long balance) {
        this.balance.set(balance);
    }

    public void addAmount(long amount) {
        this.balance.getAndAdd(amount);
    }

    public void subtractAmount(long amount) {
        this.balance.getAndAdd(-amount);
    }
}

class Company implements Runnable {

    private Account account;

    public Company(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            account.addAmount(1000);
            System.out.println("----------------------");
        }
    }
}

class Bank implements Runnable {
    private Account account;

    public Bank(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            account.subtractAmount(1000);
            System.out.println("=====================");
        }
    }
}