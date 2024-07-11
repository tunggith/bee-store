package com.example.duanbanao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hoa_don")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khuyen_mai")
    private KhuyenMai khuyenMai;
    @Column(name = "ma_hoa_don")
    private String ma;
    @Column(name = "ngay_tao")
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @ManyToOne
    @JoinColumn(name = "id_thanh_toan")
    private ThanhToan thanhToan;
    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @ManyToOne
    @JoinColumn(name = "id_giao_hang")
    private GiaoHang giaoHang;
    @Column(name = "trang_thai")
    private Integer trangThai;
    @PrePersist
    public void prePersist(){
        this.ma = generateInvoiceCode();
    }

    private String generateInvoiceCode() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomNum = new Random().nextInt(10000);
        return "HD" + timeStamp + randomNum;
    }
}