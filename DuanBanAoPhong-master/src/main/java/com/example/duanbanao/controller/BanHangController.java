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
import com.example.duanbanao.request.SoLuongRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;
import java.util.UUID;
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
            @RequestParam("pageSp") Optional<Integer> pageSp,
            @RequestParam("pageKh") Optional<Integer> pageKh
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
        //find all và phân trang danh sách kh
    int pageSize = 3;
    int currentPage = pageKh.orElse(0);
    Pageable pageableKh = PageRequest.of(currentPage, pageSize);
    Page<KhachHang> khachHangPage = repoKhachHang.findAll(pageableKh);

    model.addAttribute("currentPageKh", currentPage);
    model.addAttribute("totalPagesKh", khachHangPage.getTotalPages());
    model.addAttribute("kh", khachHangPage.getContent());
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
            @RequestParam Integer idHd)
    {
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
    public String taoHoaDon(Model model,HoaDon hoaDon, @RequestParam String tenKhachHang) {
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
    @GetMapping("delete-san-pham/{id}")
    public String deleteSanPham(@PathVariable Integer id){
        repoChiTietHoaDon.deleteById(id);
        return "redirect:/bee-store/chon-hoa-don/";
    }



    public static String randomNumericString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    @PostMapping("/addKH")
    public String addkh(@RequestParam("tenKH") String ten,
                        @RequestParam("emailKH") String email,
                        @RequestParam("sdtKH") String sdt ,
                        @RequestParam("diaChi") String diaChi
                        ,RedirectAttributes redirectAttributes){
            System.out.printf("Dữ liệu lấy được là: ten=" + ten + " email={}" + email + " sdt={} " + sdt);
            System.out.printf("số " + randomNumericString());
            KhachHang khachHang = new KhachHang();
            khachHang.setMa("KH" + randomNumericString());
            khachHang.setTen(ten);
            khachHang.setSdt(sdt);
            khachHang.setDiaChi(diaChi);
            khachHang.setTrangThai(1);
            repoKhachHang.save(khachHang);
          Optional<KhachHang> khachHangOpt = repoKhachHang.findKhachHangBySdt(sdt);
        if (khachHangOpt.isPresent()) {
            khachHang = khachHangOpt.get();
            redirectAttributes.addFlashAttribute("khachHang", khachHang);
        } else {
            throw new NullPointerException("không tìm thấy");
        }


        return "redirect:/bee-store/trang-chu";

    }



    @GetMapping("/chonKhachHang/{id}")
    public String chonkh(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        System.out.printf("idKH lấy đc là"+id);
        Optional<KhachHang> khachHangOpt = repoKhachHang.findKhachHangByid(id);
        if (khachHangOpt.isPresent()) {
          KhachHang  khachHang = khachHangOpt.get();
            redirectAttributes.addFlashAttribute("khachHang", khachHang);
        } else {
            throw new NullPointerException("không tìm thấy");
        }
        return "redirect:/bee-store/trang-chu";
    }

    @GetMapping("/clonePopUp/")
    public String clonePopUp(@RequestParam("soDienThoai") String sdt,RedirectAttributes redirectAttributes){
//        System.out.printf("sdt :"+sdt);
//        Optional<KhachHang> khachHangOpt = repoKhachHang.findKhachHangBySdt(sdt);
//        if (khachHangOpt.isPresent()) {
//            KhachHang  khachHang = khachHangOpt.get();
//            redirectAttributes.addFlashAttribute("khachHang", khachHang);
//        } else {
//            throw new NullPointerException("không tìm thấy");
//        }
        return "redirect:/bee-store/trang-chu";

    }



//     cộng trừ số lượng sản phầm trong giỏ hàng
@GetMapping("cong-san-pham/{id}")
public String congSanPham(
        @PathVariable Integer id,
        ChiTietHoaDon chiTietHoaDon,
        @RequestParam Integer idHd)
{
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
                if (sanPhamCt.getSoLuongTon() > 0){
                    existtingDetail.setSoLuong(existtingDetail.getSoLuong()+ 1);
                    BigDecimal soLuong = BigDecimal.valueOf(existtingDetail.getSoLuong());
                    BigDecimal tongTien = existtingDetail.getGia().multiply(soLuong);
                    existtingDetail.setTongTien(tongTien);
                    repoChiTietHoaDon.save(existtingDetail);
                    sanPhamCt.setSoLuongTon(sanPhamCt.getSoLuongTon() - 1);
                    repoChiTietSanPham.save(sanPhamCt);
                }else {
                    existtingDetail.setSoLuong(existtingDetail.getSoLuong() -0);
                    BigDecimal soLuong = BigDecimal.valueOf(existtingDetail.getSoLuong());
                    BigDecimal tongTien = existtingDetail.getGia().multiply(soLuong);
                    existtingDetail.setTongTien(tongTien);
                    repoChiTietHoaDon.save(existtingDetail);
                    sanPhamCt.setSoLuongTon(sanPhamCt.getSoLuongTon() - 0);
                    repoChiTietSanPham.save(sanPhamCt);
                }

            } else {
                chiTietHoaDon.setHoaDon(hoaDon);
                chiTietHoaDon.setChiTietSanPham(sanPhamCt);
                chiTietHoaDon.setGia(sanPhamCt.getGiaBan());
                chiTietHoaDon.setSoLuong(1);
                chiTietHoaDon.setTongTien(sanPhamCt.getGiaBan());
                repoChiTietHoaDon.save(chiTietHoaDon);
            }




        return "redirect:/bee-store/chon-hoa-don/" + idHd;
    }
    return "redirect:/bee-store/chon-hoa-don/" + idHd;
}


    @GetMapping("tru-san-pham/{id}")
    public String truSanPham(
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
            ChiTietHoaDon cthd = null;
            for (ChiTietHoaDon detail : existtingDetails) {
                if (detail.getChiTietSanPham().getId().equals(sanPhamCt.getId())) {
                    cthd = detail;
                    break;
                }
            }

            if (!Objects.isNull(cthd)) {
                if (cthd.getSoLuong() > 1 ) {
                    cthd.setSoLuong(cthd.getSoLuong() - 1);
                    BigDecimal soLuong = BigDecimal.valueOf(cthd.getSoLuong());
                    BigDecimal tongTien = cthd.getGia().multiply(soLuong);
                    cthd.setTongTien(tongTien);
                    repoChiTietHoaDon.save(cthd);
                    sanPhamCt.setSoLuongTon(sanPhamCt.getSoLuongTon() + 1);
                    repoChiTietSanPham.save(sanPhamCt);
                } else {
                    repoChiTietHoaDon.deleteById(cthd.getId());
                    repoChiTietSanPham.save(sanPhamCt);
                }

            } else {
                chiTietHoaDon.setHoaDon(hoaDon);
                chiTietHoaDon.setChiTietSanPham(sanPhamCt);
                chiTietHoaDon.setGia(sanPhamCt.getGiaBan());
                chiTietHoaDon.setSoLuong(1);
                chiTietHoaDon.setTongTien(sanPhamCt.getGiaBan());
                repoChiTietHoaDon.save(chiTietHoaDon);
            }


            return "redirect:/bee-store/chon-hoa-don/" + idHd;
        }
        return "redirect:/bee-store/chon-hoa-don/" + idHd;
    }


    @GetMapping("xoa-san-pham/{id}")
    public String xoaSanPham(
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
            ChiTietHoaDon cthd = null;
            for (ChiTietHoaDon detail : existtingDetails) {
                if (detail.getChiTietSanPham().getId().equals(sanPhamCt.getId())) {
                    cthd = detail;
                    break;
                }
            }

            if (!Objects.isNull(cthd)) {

                // lấy số lượng đang có trong hdct
                int soluong = cthd.getSoLuong();
                System.out.println(" số lượng đang có trong hoá đơn là + "+ soluong);

                // cập nhật lại số lượng của spct vào lại db
                sanPhamCt.setSoLuongTon(sanPhamCt.getSoLuongTon()+soluong);
                repoChiTietSanPham.save(sanPhamCt);
                // xoá cthd
                repoChiTietHoaDon.deleteById(cthd.getId());

            } else {
                chiTietHoaDon.setHoaDon(hoaDon);
                chiTietHoaDon.setChiTietSanPham(sanPhamCt);
                chiTietHoaDon.setGia(sanPhamCt.getGiaBan());
                chiTietHoaDon.setSoLuong(1);
                chiTietHoaDon.setTongTien(sanPhamCt.getGiaBan());
                repoChiTietHoaDon.save(chiTietHoaDon);
            }


            return "redirect:/bee-store/chon-hoa-don/" + idHd;
        }
        return "redirect:/bee-store/chon-hoa-don/" + idHd;
    }


    @GetMapping("/cap-nhat-so-luong")
    public ResponseEntity<String> capNhatSoLuong(
            @RequestParam Integer productId,
            @RequestParam Integer hoaDonId,
            @RequestParam Integer soLuong) {

        Optional<ChiTietSanPham> chiTietSanPhamOpt = repoChiTietSanPham.findById(productId);
        Optional<HoaDon> hoaDonOpt = repoHoaDon.findById(hoaDonId);

        if (chiTietSanPhamOpt.isPresent() && hoaDonOpt.isPresent()) {
            ChiTietSanPham sanPhamCt = chiTietSanPhamOpt.get();
            HoaDon hoaDon = hoaDonOpt.get();
            List<ChiTietHoaDon> existingDetails = repoChiTietHoaDon.findByHoaDonId(hoaDon.getId());
            ChiTietHoaDon existingDetail = null;
            for (ChiTietHoaDon detail : existingDetails) {
                if (detail.getChiTietSanPham().getId().equals(sanPhamCt.getId())) {
                    existingDetail = detail;
                    break;
                }
            }

            if (existingDetail != null) {
                if (sanPhamCt.getSoLuongTon() >= soLuong) {
                    int previousQuantity = existingDetail.getSoLuong();
                    existingDetail.setSoLuong(soLuong);
                    BigDecimal soLuongBigDecimal = BigDecimal.valueOf(existingDetail.getSoLuong());
                    BigDecimal tongTien = existingDetail.getGia().multiply(soLuongBigDecimal);
                    existingDetail.setTongTien(tongTien);
                    repoChiTietHoaDon.save(existingDetail);
                    sanPhamCt.setSoLuongTon(sanPhamCt.getSoLuongTon() + (previousQuantity - soLuong));
                    repoChiTietSanPham.save(sanPhamCt);
                    return ResponseEntity.ok("Số lượng cập nhật thành công");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số lượng tồn kho không đủ");
                }
            } else {
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                chiTietHoaDon.setHoaDon(hoaDon);
                chiTietHoaDon.setChiTietSanPham(sanPhamCt);
                chiTietHoaDon.setGia(sanPhamCt.getGiaBan());
                chiTietHoaDon.setSoLuong(soLuong);
                chiTietHoaDon.setTongTien(sanPhamCt.getGiaBan().multiply(BigDecimal.valueOf(soLuong)));
                repoChiTietHoaDon.save(chiTietHoaDon);
            }
            return ResponseEntity.ok("Sản phẩm được thêm thành công");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sản phẩm hoặc hóa đơn không tồn tại");
    }

}



