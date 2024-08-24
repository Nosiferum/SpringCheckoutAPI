package com.dogukan.springcheckoutapi.repository;

import com.dogukan.springcheckoutapi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByCategoryId(int categoryId);
    @Query("select case when count(i) > 0 then true else false end from Item i where i.categoryId != :categoryId")
    boolean isExistsByCategoryIdOtherThan(@Param("categoryId") int categoryId);
}

