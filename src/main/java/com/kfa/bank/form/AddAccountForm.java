package com.kfa.bank.form;

public class AddAccountForm {

    private String accountName;
    private Double amount;

    public AddAccountForm () {

    }

    public AddAccountForm(String accountName, Double amount) {

        this.accountName = accountName;
        this.amount = amount;

    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
