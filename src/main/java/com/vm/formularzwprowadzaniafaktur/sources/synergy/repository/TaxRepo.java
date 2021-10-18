package com.vm.formularzwprowadzaniafaktur.sources.synergy.repository;

import com.vm.formularzwprowadzaniafaktur.sources.synergy.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRepo extends JpaRepository<Tax,Long> {
    List<Tax> findAllByCodeIvNotAndCompanyCodeAndCrednotaNotOrderById(char codeIv, String companyCode, char crednota);
}
