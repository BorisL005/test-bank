package com.test.bank.exceptions;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 14.03.2021
 */
public class CustomerInactiveException extends RuntimeException {
    public CustomerInactiveException(String customerId) {
        super("Customer " + customerId + " is not active");
    }
}
