package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.service.BankAccountService;

@Controller
@RequestMapping("/bankaccount")
public class BankAccountController {
    private BankAccountService bankAccountService;
    private double oldBalance;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getBankAccountPage(Model model){
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "bankaccount";
    }

    @PostMapping
    public String openAccount(@ModelAttribute BankAccount bankAccount, Model model) {
        bankAccountService.openBankAccount(bankAccount);
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:bankaccount";
    }

    @GetMapping("/edit/{id}")
    public String getEditBankAccountPage(@PathVariable int id, Model model) {
        BankAccount bankAccount = bankAccountService.getBankAccount(id);
        model.addAttribute("bankAccount", bankAccount);
        return "bankaccount-edit";
    }

    @PostMapping("/edit/{id}")
    public String editAccount(@PathVariable int id,
                              @ModelAttribute BankAccount bankAccount, Model model){
        bankAccountService.editBankAccount(bankAccount);
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }

    @GetMapping("/deposit/{id}")
    public String getDepositBankAccountPage(@PathVariable int id, Model model){
        BankAccount bankAccount = bankAccountService.getBankAccount(id);
        oldBalance = bankAccount.getBalance();
        model.addAttribute("bankAccount", bankAccount);
        return "bankaccount-deposit";
    }

    @PostMapping("/deposit/{id}")
    public String depositAccount(@PathVariable int id,
                                 @ModelAttribute BankAccount bankAccount, Model model){
        bankAccount.setBalance( (bankAccount.getBalance() > 0) ? oldBalance+bankAccount.getBalance() : oldBalance);
        bankAccountService.editBankAccount(bankAccount);
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }

    @GetMapping("/withdraw/{id}")
    public String getWithdrawBankAccountPage(@PathVariable int id, Model model){
        BankAccount bankAccount = bankAccountService.getBankAccount(id);
        oldBalance = bankAccount.getBalance();
        model.addAttribute("bankAccount", bankAccount);
        return "bankaccount-withdraw";
    }

    @PostMapping("/withdraw/{id}")
    public String withdrawAccount(@PathVariable int id,
                                 @ModelAttribute BankAccount bankAccount, Model model){
        bankAccount.setBalance((bankAccount.getBalance() <= oldBalance) ? oldBalance-bankAccount.getBalance() : oldBalance);
        bankAccountService.editBankAccount(bankAccount);
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }
    @GetMapping("/delete/{id}")
    public String getDeleteBankAccountPage(@PathVariable int id, Model model){
        BankAccount bankAccount = bankAccountService.getBankAccount(id);

        model.addAttribute("bankAccount", bankAccount);
        return "bankaccount-delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable int id,
                                @ModelAttribute BankAccount bankAccount, Model model){
        bankAccountService.deleteBankAccount(bankAccount);
        model.addAttribute("bankaccounts", bankAccountService.getBankAccounts());
        return "redirect:/bankaccount";
    }


}
