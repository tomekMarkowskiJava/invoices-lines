package com.vm.formularzwprowadzaniafaktur.repository.birepository;

import com.vm.formularzwprowadzaniafaktur.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
    Invoice findByRequestId(String requestId);
}
