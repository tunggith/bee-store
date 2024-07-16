package com.example.duanbanao.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SoLuongRequest {
    private Integer productId;
    private Integer hoaDonId;
    private int soLuong;
}

