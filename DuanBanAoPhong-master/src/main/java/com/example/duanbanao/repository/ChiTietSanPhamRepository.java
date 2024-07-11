package com.example.duanbanao.repository;

import com.example.duanbanao.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,Integer> {
    @Query("select ctsp from ChiTietSanPham ctsp")
    Page<ChiTietSanPham> findAll(Pageable pageable);
    @Query("SELECT c FROM ChiTietSanPham c " +
            "WHERE " +
            "(LOWER(c.sanPham.ten) LIKE LOWER(CONCAT('%', COALESCE(?1, ''), '%'))) AND " +
            "(LOWER(c.mauSac.ten) LIKE LOWER(CONCAT('%', COALESCE(?2, ''), '%'))) AND " +
            "(LOWER(c.size.ten) LIKE LOWER(CONCAT('%', COALESCE(?3, ''), '%'))) AND " +
            "(LOWER(c.chatLieu) LIKE LOWER(CONCAT('%', COALESCE(?4, ''), '%'))) AND " +
            "(c.soLuongTon = COALESCE(?5, c.soLuongTon))")
    Page<ChiTietSanPham> findAllByCriteria(String sanPhamName, String mauSacName, String sizeName, String chatLieu, Integer soLuongTon, Pageable pageable);
}
