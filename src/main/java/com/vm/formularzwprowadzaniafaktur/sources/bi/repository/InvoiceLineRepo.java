package com.vm.formularzwprowadzaniafaktur.sources.bi.repository;

import com.vm.formularzwprowadzaniafaktur.sources.bi.model.InvoiceLine;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvoiceLineRepo extends JpaRepository<InvoiceLine,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM InvoiceLine i where i.id = :id")
    int deleteByInvoiceIdAndLineId(@Param("id") int id);

    List<InvoiceLine> findAllByInvoiceId(int invoiceId);

}
