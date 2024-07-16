package com.example.duanbanao.controller;

import com.example.duanbanao.entity.ChiTietHoaDon;
import com.example.duanbanao.entity.ChiTietSanPham;
import com.example.duanbanao.entity.HoaDon;
import com.example.duanbanao.entity.KhachHang;
import com.example.duanbanao.repository.ChiTietHoaDonRepository;
import com.example.duanbanao.repository.ChiTietSanPhamRepository;
import com.example.duanbanao.repository.ChucVuRepository;
import com.example.duanbanao.repository.HoaDonRepository;
import com.example.duanbanao.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("bee-store")
public class BanHangController {
    @Autowired
    private ChucVuRepository repoChucVU;
    @Autowired
    private HoaDonRepository repoHoaDon;
    @Autowired
    private ChiTietSanPhamRepository repoChiTietSanPham;
    @Autowired
    private ChiTietHoaDonRepository repoChiTietHoaDon;
    @Autowired
    private KhachHangRepository repoKhachHang;

    //url trang chủ hiển thị thông tin bán hàng tại quầy
    @GetMapping("/trang-chu")
    public String view(
            Model model,
            @RequestParam("pageHd") Optional<Integer> pageHd,
            @RequestParam("pageSp") Optional<Integer> pageSp
    ) {
        //find all và phân trang danh sách hóa đơn
        Pageable pageableHd = PageRequest.of(pageHd.orElse(0), 3);
        Page<HoaDon> pageHoaDon = repoHoaDon.findHoaDonByTrangThai(1, pageableHd);
        model.addAttribute("currentPageHoaDon", pageHd.orElse(0));
        model.addAttribute("totalPageHoaDon", pageHoaDon.getTotalPages());
        model.addAttribute("listHoaDon", pageHoaDon);
        //find all và phân trang danh sách sản phẩm
        Pageable pageableSp = PageRequest.of(pageSp.orElse(0), 3);
        Page<ChiTietSanPham> pageSanPham = repoChiTietSanPham.findAll(pageableSp);
        model.addAttribute("curentPageSanPham", pageSp.orElse(0));
        model.addAttribute("totalPageSanPham", pageSanPham.getTotalPages());
        model.addAttribute("listSanPham", pageSanPham);
        return "view/BanHang";
    }

    // url chọn hóa đơn từ hóa đơn xuống giỏ hàng
    @GetMapping("/chon-hoa-don/{id}")
    public String chonHoaDon(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes,
            @RequestParam Optional<Integer> pageGh) {
        //fill thuộc tính giỏ hàng
        redirectAttributes.addFlashAttribute("listGioHang", repoChiTietHoaDon.findByHoaDonId(id));
        //truyền thuộc tính hóa đơn để lấy thông tin: mã hóa đơn, tên khách hàng, tổng tiền
        redirectAttributes.addFlashAttribute("hoaDon", repoHoaDon.findById(id).get());
        return "redirect:/bee-store/trang-chu";
    }

    @GetMapping("chon-san-pham/{id}")
    public String chonSanPham(
            @PathVariable Integer id,
            ChiTietHoaDon chiTietHoaDon,
            @RequestParam Integer idHd) {
        //add sản phẩm vào giỏ hàng
        Optional<ChiTietSanPham> chiTietSanPhamOpt = repoChiTietSanPham.findById(id);
        Optional<HoaDon> hoaDonOpt = repoHoaDon.findById(idHd);

        if (chiTietSanPhamOpt.isPresent() && hoaDonOpt.isPresent()) {
            ChiTietSanPham sanPhamCt = chiTietSanPhamOpt.get();
            HoaDon hoaDon = hoaDonOpt.get();
            List<ChiTietHoaDon> existtingDetails = repoChiTietHoaDon.findByHoaDonId(hoaDon.getId());
            ChiTietHoaDon existtingDetail = null;
            for (ChiTietHoaDon detail : existtingDetails) {
                if (detail.getChiTietSanPham().getId().equals(sanPhamCt.getId())) {
                    existtingDetail = detail;
                    break;
                }
            }
            if (!Objects.isNull(existtingDetail)) {
                existtingDetail.setSoLuong(existtingDetail.getSoLuong() + 1);
                BigDecimal soLuong = BigDecimal.valueOf(existtingDetail.getSoLuong());
                BigDecimal tongTien = existtingDetail.getGia().multiply(soLuong);
                existtingDetail.setTongTien(tongTien);
                repoChiTietHoaDon.save(existtingDetail);
            } else {
                chiTietHoaDon.setHoaDon(hoaDon);
                chiTietHoaDon.setChiTietSanPham(sanPhamCt);
                chiTietHoaDon.setGia(sanPhamCt.getGiaBan());
                chiTietHoaDon.setSoLuong(1);
                chiTietHoaDon.setTongTien(sanPhamCt.getGiaBan());
                repoChiTietHoaDon.save(chiTietHoaDon);
            }
            sanPhamCt.setSoLuongTon(sanPhamCt.getSoLuongTon() - 1);
            repoChiTietSanPham.save(sanPhamCt);
            return "redirect:/bee-store/chon-hoa-don/" + idHd;
        }
        return "redirect:/bee-store/chon-hoa-don/" + idHd;
    }

    @GetMapping("seach-khach-hang")
    public String searchKhachHang(
            @RequestParam String sdt,
            RedirectAttributes redirectAttributes
    ) {
        Optional<KhachHang> khachHangOpt = repoKhachHang.findKhachHangBySdt(sdt);
        if (khachHangOpt.isPresent()) {
            KhachHang khachHang = khachHangOpt.get();
            redirectAttributes.addFlashAttribute("khachHang", khachHang);
        } else {
            throw new NullPointerException("không tìm thấy");
        }

        return "redirect:/bee-store/trang-chu";
    }

    @PostMapping("tao-hoa-don")
    public String taoHoaDon(HoaDon hoaDon, @RequestParam String tenKhachHang) {
        Optional<KhachHang> khachHangOpt = repoKhachHang.findKhachHangByTen(tenKhachHang);
        if (khachHangOpt.isPresent()) {
            KhachHang khachHang = khachHangOpt.get();
            hoaDon.setKhachHang(khachHang);
            hoaDon.setNgayTao(new Date());
            hoaDon.setTrangThai(1);
            hoaDon.setTongTien(BigDecimal.valueOf(0));
            repoHoaDon.save(hoaDon);
            System.out.println("thành công");
        } else {
            throw new ArithmeticException("lỗi");
        }
        return "redirect:/bee-store/trang-chu";
    }

    @GetMapping("delete/{id}")
    public String deleteSanPham(@PathVariable Integer id,
                                @RequestParam Integer idHd){
        Optional<ChiTietHoaDon> hoaDonChiTietOpt = repoChiTietHoaDon.findById(id);
        if(hoaDonChiTietOpt.isPresent()){
            ChiTietHoaDon chiTietHoaDon = hoaDonChiTietOpt.get();
            ChiTietSanPham chiTietSanPham =chiTietHoaDon.getChiTietSanPham();
            chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon()+chiTietHoaDon.getSoLuong());
            repoChiTietSanPham.save(chiTietSanPham);
            repoChiTietHoaDon.deleteById(id);
        }
        return "redirect:/bee-store/chon-hoa-don/" + idHd;
    }

}
