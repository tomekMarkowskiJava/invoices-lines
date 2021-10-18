package com.vm.formularzwprowadzaniafaktur.sources.bi.repository;

import com.vm.formularzwprowadzaniafaktur.sources.bi.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
    boolean existsByRequestId(String requestId);
    Invoice findByRequestId(String requestId);
}
