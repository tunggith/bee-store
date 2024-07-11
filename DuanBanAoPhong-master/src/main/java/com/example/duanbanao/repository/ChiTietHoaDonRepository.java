package com.example.duanbanao.repository;

import com.example.duanbanao.entity.ChiTietHoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChiTietHoaDonRepository extends JpaRepository<com.example.duanbanao.entity.ChiTietHoaDon,Integer> {
    @Query("select hd from ChiTietHoaDon hd where hd.hoaDon.id=?1")
    Page<ChiTietHoaDon> findChiTietHoaDonByHoaDon(Integer id, Pageable pageable);
    List<ChiTietHoaDon> findByHoaDonId(Integer id);
//    @Query("select hd from ChiTietHoaDon hd where hd.hoaDon.id=?1")
//    List<ChiTietHoaDon> findChiTietHoaDonByHoaDon(Integer id);
}
