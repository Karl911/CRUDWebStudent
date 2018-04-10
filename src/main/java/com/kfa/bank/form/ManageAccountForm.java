package com.kfa.bank.form;

public class ManageAccountForm {
	 
    private String fullname;
    private Double balance;
 
    public ManageAccountForm() {
 
    }
 
    public ManageAccountForm(String fullname, Double balance) {
        this.fullname = fullname;
        this.balance = balance;
    }
 
    public String getFullname() {
        return fullname;
    }
 
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
 
 
    public Double getBalance() {
        return balance;
    }
 
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}