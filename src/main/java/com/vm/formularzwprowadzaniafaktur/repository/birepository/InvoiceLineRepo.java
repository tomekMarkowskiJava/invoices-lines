package com.vm.formularzwprowadzaniafaktur.repository.birepository;

import com.vm.formularzwprowadzaniafaktur.model.InvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface InvoiceLineRepo extends JpaRepository<InvoiceLine,Long> {
    @Transactional
    int deleteByInvoiceIdAndLineId(int invoiceId, int lineId);
}
