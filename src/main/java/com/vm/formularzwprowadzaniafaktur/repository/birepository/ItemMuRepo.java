package com.vm.formularzwprowadzaniafaktur.repository.birepository;

import com.vm.formularzwprowadzaniafaktur.model.ItemMU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemMuRepo extends JpaRepository<ItemMU, Long> {

    @Query(value = "EXEC [Towary].[Lista towarów dla zamówienia zakupu] :projectNr, :mpk",
    nativeQuery = true)
    List<ItemMU> findItemMuByProjectNumerAndMPK(String projectNr, String mpk);
}
