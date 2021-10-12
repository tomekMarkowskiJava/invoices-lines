package com.vm.formularzwprowadzaniafaktur.repository.birepository;

import com.vm.formularzwprowadzaniafaktur.model.InvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvoiceLineRepo extends JpaRepository<InvoiceLine,Long> {
    @Transactional
    int deleteByInvoiceIdAndLineId(int invoiceId, int lineId);

    List<InvoiceLine> findAllByInvoiceId(int invoiceId);
}
