package com.example.duanbanao.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class PagesRequest {
    private Optional<Integer> pageHoaDon;
    private Optional<Integer> pageGioHang;
    private Optional<Integer> pageSanPham;
}
