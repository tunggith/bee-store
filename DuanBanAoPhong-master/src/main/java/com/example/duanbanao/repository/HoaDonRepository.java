package com.example.duanbanao.repository;

import com.example.duanbanao.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon,Integer> {
    @Query("SELECT hd FROM HoaDon hd WHERE hd.trangThai = ?1 GROUP BY hd.id ORDER BY hd.id DESC")
    Page<HoaDon> findHoaDonByTrangThai(Integer trangThai, Pageable pageable);
}
