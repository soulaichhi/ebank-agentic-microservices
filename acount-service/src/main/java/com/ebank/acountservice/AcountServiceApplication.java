package com.ebank.acountservice;

import com.ebank.acountservice.domain.AccountType;
import com.ebank.acountservice.services.EbankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AcountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(EbankService ebankService) {
        return args -> {
            ebankService.saveNewAccount(65000, AccountType.CURRENT_ACCOUNT, "MAD", 1L);
            ebankService.saveNewAccount(12000, AccountType.CURRENT_ACCOUNT, "MAD", 1L);
            ebankService.saveNewAccount(3000, AccountType.CURRENT_ACCOUNT, "MAD", 2L);

            ebankService.getAllAccounts().forEach(acc ->{
                for (int i=0; i<5; i++) {
                    ebankService.credit(acc.getId(),500+Math.random()*230000);
                    ebankService.debit(acc.getId(),500+Math.random()*10000);
                }
            });

        };
    }

}
