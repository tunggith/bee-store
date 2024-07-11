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
@Table(name = "chi_tiet_hoa_don")
public class ChiTietHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hoa_don_ct")
    private Integer id;
    @Column(name = "ma_hoa_don_ct")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "id_san_pham_ct")
    private ChiTietSanPham chiTietSanPham;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "gia")
    private BigDecimal gia;
    @Column(name = "tong_tien")
    private BigDecimal tongTien;
    @PrePersist
    public void prePersist(){
        this.ma = generateInvoiceCode();
    }

    private String generateInvoiceCode() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomNum = new Random().nextInt(10000);
        return "HDCT" + timeStamp + randomNum;
    }

}