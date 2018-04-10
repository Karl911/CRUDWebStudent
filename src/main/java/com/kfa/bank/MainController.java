package com.kfa.bank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kfa.bank.dao.BankAccountDAO;
import com.kfa.bank.exception.BankTransactionException;
import com.kfa.bank.form.ManageAccountForm;
import com.kfa.bank.form.SendMoneyForm;
import com.kfa.bank.model.BankAccountInfo;
 
@Controller
//@ComponentScan(basePackages = "com.kfa.bank.controller")
public class MainController {
 
	@Autowired
    private BankAccountDAO bankAccountDAO;
 
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showBankAccounts(Model model) {
        List<BankAccountInfo> list = bankAccountDAO.listBankAccountInfo();
 
        model.addAttribute("accountInfos", list);
 
        return "accountsPage";
    }
    
      private List<BankAccountInfo> getBankAccounts() {
        List<BankAccountInfo> list = bankAccountDAO.listBankAccountInfo();
 
        return list;
    }
    
     
    @RequestMapping(value = "/sendMoney", method = RequestMethod.GET)
    public String viewSendMoneyPage(Model model) {
 
        SendMoneyForm form = new SendMoneyForm(1L, 2L, 700d);
    
        model.addAttribute("sendMoneyForm", form);
        model.addAttribute("accountInfos", getBankAccounts());
 
        return "sendMoneyPage";
    }
    
    @RequestMapping(value = "/manageAccount", method = RequestMethod.GET)
    public String viewManageAccountPage(Model model) {
 
       //ManageAccountForm form = new ManageAccountForm(1L, 2L, 700d);
        //model.addAttribute("sendMoneyForm", form);
        model.addAttribute("accountInfos", getBankAccounts());
 
        return "manageAccountPage";
    }
    
    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    public String viewAddAccountPage(Model model) {
 
        ManageAccountForm form = new ManageAccountForm();
    
        model.addAttribute("ManageAccountForm", form);
        //model.addAttribute("accountInfos", getBankAccounts());
 
        return "addNewAccountPage";
    }
 
  
    @RequestMapping(value = "/sendMoney", method = RequestMethod.POST)
    public String processSendMoney(Model model, SendMoneyForm sendMoneyForm) {
 
        System.out.println("Send Money: " + sendMoneyForm.getAmount());
 
        try {
            bankAccountDAO.sendMoney(sendMoneyForm.getFromAccountId(), //
                    sendMoneyForm.getToAccountId(), //
                    sendMoneyForm.getAmount());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/sendMoneyPage";
        }
        return "redirect:/";
    }
    
    @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
    public String processAddAccount(Model model, ManageAccountForm manageAccountForm) {
 
        System.out.println("Create new account for: " + manageAccountForm.getFullname() + " / " + manageAccountForm.getBalance());
 
        try {
        		bankAccountDAO.createAccount(manageAccountForm.getFullname() , manageAccountForm.getBalance());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/accountsPage";
        }
        return "redirect:/";
    }
 
}