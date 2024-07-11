package com.example.duanbanao.repository;

import com.example.duanbanao.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SanPhamRepository extends JpaRepository<SanPham,Integer> {
    @Query("select sp from SanPham sp")
    Page<SanPham> findSanPhamBy(Integer id, Pageable pageable);
}
