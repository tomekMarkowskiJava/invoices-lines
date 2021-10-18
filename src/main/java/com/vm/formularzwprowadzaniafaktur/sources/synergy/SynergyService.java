package com.vm.formularzwprowadzaniafaktur.sources.synergy;

import com.vm.formularzwprowadzaniafaktur.sources.synergy.model.Tax;
import com.vm.formularzwprowadzaniafaktur.sources.synergy.repository.TaxRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mainService")
public class SynergyService{
    private final TaxRepo taxRepo;

    @Autowired
    public SynergyService(TaxRepo taxRepo) {
        this.taxRepo = taxRepo;
    }

    public List<Tax> findTaxes(){
        return taxRepo.findAllByCodeIvNotAndCompanyCodeAndCrednotaNotOrderById('V',"103",'J');
    }
}
