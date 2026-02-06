package com.foodify.backend_foodify.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Wishlist;
import com.foodify.backend_foodify.Entities.Wishlist_item;
import com.foodify.backend_foodify.Entities.Wishlist_item.Item_type;

@Repository
public interface Wishlist_item_Repo extends JpaRepository<Wishlist_item, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(wi) > 0 THEN true ELSE false END
        FROM Wishlist_item wi
        WHERE wi.wishlist = :wishlist
          AND wi.item_id = :itemId
          AND wi.item_type = :itemType
    """)
    boolean existsWishlistItem(
            @Param("wishlist") Wishlist wishlist,
            @Param("itemId") Long itemId,
            @Param("itemType") Item_type itemType
    );

    //boolean existsByWishlistAndItem_idAndItem_type(Wishlist wishlist, Long itemId, Item_type itemType);
}

