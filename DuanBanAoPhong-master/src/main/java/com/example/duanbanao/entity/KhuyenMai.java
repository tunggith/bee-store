package com.example.duanbanao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "khuyen_mai")
public class KhuyenMai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_khuyen_mai")
    private Integer id;
    @Column(name = "ma_khuyen_mai")
    private String ma;
    @Column(name = "ten_khuyen_mai")
    private String tenKhuyenMai;
    @Column(name = "gia_tri")
    private Integer giaTri;
    @Column(name = "ngay_tao")
    @Temporal(TemporalType.DATE)
    private Date ngayTao;
    @Column(name = "ngay_bat_dau")
    @Temporal(TemporalType.DATE)
    private Date ngayBatDau;
    @Column(name = "ngay_ket_thuc")
    @Temporal(TemporalType.DATE)
    private Date ngayKetThuc;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "trang_thai")
    private Integer trangThai;
}