package com.example.duanbanao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "chi_tiet_san_pham")
public class ChiTietSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_san_pham_ct")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private Size size;
    @Column(name = "chat_lieu")
    private String chatLieu;
    @Column(name = "thuong_hieu")
    private String thuongHieu;
    @Column(name = "xuat_xu")
    private String xuatXu;
    @Column(name = "gia_ban")
    private BigDecimal giaBan;
    @Column(name = "gia_nhap")
    private BigDecimal giaNhap;
    @Column(name = "so_luong_ton")
    private Integer soLuongTon;
    @Column(name = "ngay_nhap")
    @Temporal(TemporalType.DATE)
    private Date ngayNhap;
    @Column(name = "trang_thai")
    private Integer trangThai;


}