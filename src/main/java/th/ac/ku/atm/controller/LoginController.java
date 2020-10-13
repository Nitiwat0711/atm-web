package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.atm.model.BankAccount;
import th.ac.ku.atm.model.Customer;
import th.ac.ku.atm.service.BankAccountService;
import th.ac.ku.atm.service.CustomerService;

@Controller
@RequestMapping("/login")
public class LoginController {

    private CustomerService customerService;
    private BankAccountService bankAccountService;

    public LoginController(CustomerService customerService, BankAccountService bankAccountService) {
        this.customerService = customerService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(@ModelAttribute Customer customer, Model model) {
        Customer matchingCustomer = customerService.checkPin(customer);

        if (matchingCustomer != null) {
            model.addAttribute("customertitle",
                    "สวัสดี, " + matchingCustomer.getName() + "! " + "Bank Accounts");
            model.addAttribute("bankaccounts",
                    bankAccountService.getCustomerBankAccounts(customer.getId()));
            return "customeraccount";
        }else {
            model.addAttribute("greeting",
                    "Can't find customer");
            return "home";
        }


    }
}
