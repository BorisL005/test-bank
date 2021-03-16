package com.test.bank.repository;

import com.test.bank.domain.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
public interface AccountRepository extends CrudRepository<Account, String> {

    List<Account> findAllByCustomerId(String customerId);

}
