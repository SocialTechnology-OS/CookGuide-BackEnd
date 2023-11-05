package com.cookguide.database.repository;

import com.cookguide.database.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
