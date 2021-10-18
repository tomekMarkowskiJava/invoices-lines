package com.vm.formularzwprowadzaniafaktur.sources.bi.repository;

import com.vm.formularzwprowadzaniafaktur.sources.bi.model.ItemMU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMuRepo extends JpaRepository<ItemMU, Long> {

    @Query(value = "EXEC [Towary].[Lista towarów dla zamówienia zakupu] :projectNr, :mpk",
    nativeQuery = true)
    List<ItemMU> findItemMuByProjectNumerAndMPK(String projectNr, String mpk);
}
