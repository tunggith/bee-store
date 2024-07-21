package com.example.duanbanao.controller;

import com.example.duanbanao.entity.ChiTietHoaDon;
import com.example.duanbanao.entity.ChiTietSanPham;
import com.example.duanbanao.entity.HoaDon;
import com.example.duanbanao.entity.MauSac;
import com.example.duanbanao.entity.SanPham;
import com.example.duanbanao.entity.Size;
import com.example.duanbanao.repository.ChiTietHoaDonRepository;
import com.example.duanbanao.repository.ChiTietSanPhamRepository;
import com.example.duanbanao.repository.HoaDonRepository;
import com.example.duanbanao.repository.KichThuocRepository;
import com.example.duanbanao.repository.MauSacRepository;
import com.example.duanbanao.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("bee-store")
public class SanPhamController {

    @Autowired
    private ChiTietSanPhamRepository repoChiTietSanPham;
    @Autowired
    private MauSacRepository repoMauSac;
    @Autowired
    private SanPhamRepository repoSanPham;
    @Autowired
    private KichThuocRepository repoKichThuoc;
    private Integer pageSanPham = 0;
    //    @GetMapping("san-pham-chi-tiet")
//    public String getSanPhamChiTiet(  Model model,@RequestParam("pageSp") Optional<Integer> pageSp
//    ) {
//        Pageable pageableSp = PageRequest.of(pageSp.orElse(0),3);
//        Page<ChiTietSanPham> pageSanPham = repoChiTietSanPham.findAll(pageableSp);
//        model.addAttribute("curentPageSanPham",pageSp.orElse(0));
//        model.addAttribute("totalPageSanPham",pageSanPham.getTotalPages());
//        model.addAttribute("listSanPham",pageSanPham);
//        return "view/ChiTietSanPham";
//    }
    @ModelAttribute("idSP")
    public List<SanPham> getSP(){
        return repoSanPham.findAll();
    }
    @ModelAttribute("idMS")
    public List<MauSac> getMS(){
        return repoMauSac.findAll();
    }
    @ModelAttribute("idSize")
    public List<Size> getKT(){
        return repoKichThuoc.findAll();
    }


    @GetMapping("san-pham-chi-tiet/delete")
    public String delete(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<ChiTietSanPham> chiTietSanPhamOptional = repoChiTietSanPham.findById(id);
        if (chiTietSanPhamOptional.isPresent()) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamOptional.get();
            if (chiTietSanPham.getSanPham() != null) {
                repoChiTietSanPham.deleteById(id);
                redirectAttributes.addFlashAttribute("message", "Xóa chi tiết sản phẩm thành công");
                return "redirect:/bee-store/san-pham-chi-tiet/" + chiTietSanPham.getSanPham().getId();
            }
        }
        redirectAttributes.addFlashAttribute("error", "Xóa chi tiết sản phẩm thất bại: sản phẩm không tồn tại");
        return "redirect:/bee-store/sanpham";
    }

    @GetMapping("san-pham-chi-tiet/search")
    public String searchProducts(
            @RequestParam(name = "sanPhamName", required = false) String sanPhamName,
            @RequestParam(name = "mauSacName", required = false) String mauSacName,
            @RequestParam(name = "sizeName", required = false) String sizeName,
            @RequestParam(name = "chatLieuName", required = false) String chatLieuName,
            @RequestParam(name = "soLuongName", required = false) Integer soLuongName,
            Model model,
            Pageable pageable) {

        Page<ChiTietSanPham> results = repoChiTietSanPham.findAllByCriteria(sanPhamName, mauSacName, sizeName, chatLieuName, soLuongName, pageable);
        model.addAttribute("listSanPham", results);
        model.addAttribute("curentPageSanPham", pageable.getPageNumber());
        model.addAttribute("totalPageSanPham", results.getTotalPages());

        return "view/ChiTietSanPham";
    }


    @GetMapping("/san-pham-chi-tiet/{id}")
    public String getSanPhamDetail(@PathVariable("id") Integer id,Model model,@RequestParam("pageSp") Optional<Integer> pageSp){
        Optional<SanPham> sanPhamOptional = repoSanPham.findById(id);

        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();

            model.addAttribute("detail", sanPham);

            Pageable pageableSp = PageRequest.of(pageSp.orElse(0), 3);
            Page<ChiTietSanPham> pageSanPham = repoChiTietSanPham.findAll(pageableSp);


            model.addAttribute("curentPageSanPham", pageSp.orElse(0));
            model.addAttribute("totalPageSanPham", pageSanPham.getTotalPages());
            model.addAttribute("listSanPham", pageSanPham);
            model.addAttribute("idSP", repoSanPham.findAll()); // Để hiển thị danh sách sản phẩm
            model.addAttribute("idMS", repoMauSac.findAll()); // Để hiển thị danh sách màu sắc
            model.addAttribute("idSize", repoKichThuoc.findAll()); // Để hiển thị danh sách kích thước


            return "view/ChiTietSanPham";
        } else {
            return "redirect:/bee-store/san-pham";
        }
    }
    @PostMapping("/san-pham-chi-tiet/add")
    public String addChiTietSanPham(@ModelAttribute ChiTietSanPham chiTietSanPham, RedirectAttributes redirectAttributes) {
        repoChiTietSanPham.save(chiTietSanPham);
        redirectAttributes.addFlashAttribute("message", "Thêm chi tiết sản phẩm thành công");
        return "redirect:/bee-store/san-pham-chi-tiet/" + chiTietSanPham.getSanPham().getId();
    }
    @GetMapping("san-pham")
    public String SanPham( Model model,
                           @RequestParam("pageHd") Optional<Integer> pageHd,
                           @RequestParam("pageSp") Optional<Integer> pageSp
    ) {
        Pageable pageableSp = PageRequest.of(pageSp.orElse(0),3);
        Page<SanPham> pageSanPham = repoSanPham.findAll(pageableSp);
        model.addAttribute("curentPageSanPham",pageSp.orElse(0));
        model.addAttribute("totalPageSanPham",pageSanPham.getTotalPages());
        model.addAttribute("listSanPham",pageSanPham);
        return "view/SanPham";
    }
    @PostMapping("san-pham-them")
    public String themSp(@ModelAttribute("sanPham") SanPham sanPham,
                         @ModelAttribute("ctsp") ChiTietSanPham chiTietSanPham,
                         RedirectAttributes redirectAttributes) {
        repoSanPham.save(sanPham);
        redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm thành công");
        return "redirect:/bee-store/san-pham";
    }


}
