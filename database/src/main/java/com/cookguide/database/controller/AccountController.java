package com.cookguide.database.controller;

import com.cookguide.database.exception.ResourceNotFoundException;
import com.cookguide.database.exception.ValidationException;
import com.cookguide.database.model.Account;
import com.cookguide.database.model.Health_info;
import com.cookguide.database.model.Preference;
import com.cookguide.database.repository.AccountRepository;
import com.cookguide.database.repository.HealthInfoRepository;
import com.cookguide.database.repository.PreferenceRepository;
import com.cookguide.database.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login/v1")

public class AccountController{
    @Autowired
    private AccountService accountService;

    private final AccountRepository accountRepository;
    private final HealthInfoRepository healthInfoRepository;
    private final PreferenceRepository preferenceRepository;

    public AccountController(PreferenceRepository preferenceRepository, HealthInfoRepository healthInfoRepository, AccountRepository accountRepository) {
        this.preferenceRepository = preferenceRepository;
        this.healthInfoRepository = healthInfoRepository;
        this.accountRepository = accountRepository;
    }

    //URL: http://localhost:8080/api/login/v1/accounts/1
    //METHOD: POST
    public ResponseEntity<Account> createAccount(@RequestBody Account account)
    {
        // Crear nuevas preferencias y guardar en la base de datos
        Preference newPreference = new Preference(/* Agregar aquí los atributos de la preferencia */);
        Preference savedPreference = preferenceRepository.save(newPreference);

        // Crear nueva información de salud y guardar en la base de datos
        Health_info newHealthInfo = new Health_info(/* Agregar aquí los atributos de la información de salud */);
        Health_info savedHealthInfo = healthInfoRepository.save(newHealthInfo);

        // Asignar las nuevas preferencias y la nueva información de salud a la cuenta
        account.setPreference(savedPreference);
        account.setHealthInfo(savedHealthInfo);

        // Guardar la cuenta con las nuevas preferencias y la nueva información de salud asignadas
        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.ok(savedAccount);
    }


    //Aqui se establecen las validaciones al momento de crear una entidad "Account".
    private void validateAccount(Account account){
        String emailRegex = "^[A-Za-z0-9_-]+@(gmail\\.com|yahoo\\.com|outlook\\.com)$";
        // Validación de Dominios y nombres erroneos->
        if (!account.getUserEmail().matches(emailRegex)) {
            throw new ValidationException("Invalid Email or domain not allowed");
        }
        if (account.getFirstName().length() > 100){
            throw new ValidationException("Maximun limit of characters");
        }
        // Faltan más validaciones
    }




}
