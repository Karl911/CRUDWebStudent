package com.kfa.bank;

import java.util.List;

import com.kfa.bank.form.AddAccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kfa.bank.dao.BankAccountDAO;
import com.kfa.bank.exception.BankTransactionException;
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

    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    public String viewAddAccountPage(Model model) {

        AddAccountForm form = new AddAccountForm();

        model.addAttribute("addAccountForm", form);
        //model.addAttribute("accountInfos", getBankAccounts());

        return "addAccountPage";
    }

    @RequestMapping(value = "/manageAccount", method = RequestMethod.GET)
    public String viewManageAccountPage(Model model) {

        //ManageAccountForm form = new ManageAccountForm(1L, 2L, 700d);
        //model.addAttribute("sendMoneyForm", form);
        model.addAttribute("accountInfos", getBankAccounts());

        return "manageAccountsPage";
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.POST)
    public String processSendMoney(Model model, AddAccountForm addAccountForm) {

        System.out.println("Create new account: " + addAccountForm.getAccountName() + " with : "+addAccountForm.getAmount());

        try {

            bankAccountDAO.createAccount(addAccountForm.getAccountName(),
                    addAccountForm.getAmount());
        } catch (BankTransactionException e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "/addAccountPage";
        }
        return "redirect:/";
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

}