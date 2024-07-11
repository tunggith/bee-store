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

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "giao_hang")
public class GiaoHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_giao_hang")
    private Integer id;
    @Column(name = "ngay_giao")
    @Temporal(TemporalType.DATE)
    private Date ngayGiao;
    @Column(name = "ngay_nhan")
    @Temporal(TemporalType.DATE)
    private Date ngayNhan;
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "phi_giao_hang")
    private BigDecimal phiGiaoHang;
    @Column(name = "trang_thai")
    private Integer trangThai;

    // Getters and setters
}