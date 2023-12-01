package com.example.section9.repository;

import com.example.section9.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCustomerIdOrderByTransactionDateDesc(int customerId);

}
