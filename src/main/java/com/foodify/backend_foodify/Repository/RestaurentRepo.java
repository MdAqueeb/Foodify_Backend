package com.foodify.backend_foodify.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foodify.backend_foodify.Entities.Restaurent;

@Repository
public interface RestaurentRepo extends JpaRepository<Restaurent, Long> {

    @Query(
        value = "SELECT * FROM restaurant WHERE active_status = :status ",
        countQuery = "SELECT COUNT(*) FROM restaurant WHERE active_status = :status ",
        nativeQuery = true
    )
    Page<Restaurent> findByRestaurentStatus(String status, PageRequest pageable);

    @Query(
        value = "SELECT * FROM restaurant WHERE active_status = :status ORDER BY popular DESC",
        countQuery = "SELECT COUNT(*) FROM restaurant WHERE active_status = :status ORDER BY popular DESC",
        nativeQuery = true
    )
    Page<Restaurent> findByRestaurentStatusPopular(String status, PageRequest pageable);

    @Query(
        value = "SELECT * FROM restaurant WHERE owner_id = :userid",
        countQuery = "SELECT COUNT(*) FROM restaurant WHERE owner_id = :userid",
        nativeQuery = true
    )
    Page<Restaurent> findByUserRest(@Param("userid") Long userid, PageRequest pageable);

    @Query(
        value = "SELECT * FROM restaurant WHERE owner_id = :userid AND active_status = :status",
        countQuery = "SELECT COUNT(*) FROM restaurant WHERE owner_id = :userid AND active_status = :status",
        nativeQuery = true
    )
    Page<Restaurent> findByUserRstStatus(@Param("userid") Long userid,@Param("status") String status, PageRequest pageable);
    
    
}
