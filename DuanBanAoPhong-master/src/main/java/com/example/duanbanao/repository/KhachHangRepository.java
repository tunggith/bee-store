package com.example.duanbanao.repository;

import com.example.duanbanao.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang,Integer> {
    @Query("select kh from KhachHang kh where  kh.sdt like ?1")
    Optional<KhachHang> findKhachHangBySdt(String sdt);
    @Query("select kh from KhachHang kh where  kh.ten like ?1")
    Optional<KhachHang> findKhachHangByTen(String ten);

    @Query("select kh from KhachHang kh where  kh.id = ?1")
    Optional<KhachHang> findKhachHangByid(Integer id);




}
