package com.vm.formularzwprowadzaniafaktur.repository.birepository;

import com.vm.formularzwprowadzaniafaktur.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
    Invoice findByRequestId(String requestId);
}
