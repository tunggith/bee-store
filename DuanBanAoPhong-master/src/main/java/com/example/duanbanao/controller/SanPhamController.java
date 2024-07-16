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
    private HoaDonRepository repoHoaDon;
    @Autowired
    private ChiTietSanPhamRepository repoChiTietSanPham;
    @Autowired
    private ChiTietHoaDonRepository repoChiTietHoaDon;

    @Autowired
    private MauSacRepository repoMauSac;
    @Autowired
    private SanPhamRepository repoSanPham;
    @Autowired
    private KichThuocRepository repoKichThuoc;
    private Integer pageSanPham = 0;
    @GetMapping("san-pham-chi-tiet")
    public String getSanPhamChiTiet(  Model model,@RequestParam("pageSp") Optional<Integer> pageSp
    ) {
        Pageable pageableSp = PageRequest.of(pageSp.orElse(0),3);
        Page<ChiTietSanPham> pageSanPham = repoChiTietSanPham.findAll(pageableSp);
        model.addAttribute("curentPageSanPham",pageSp.orElse(0));
        model.addAttribute("totalPageSanPham",pageSanPham.getTotalPages());
        model.addAttribute("listSanPham",pageSanPham);
        return "view/ChiTietSanPham";
    }
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

    @PostMapping("san-pham-chi-tiet/add")
    public String store(ChiTietSanPham spct) {
        repoChiTietSanPham.save(spct);
        return "redirect:/bee-store/san-pham-chi-tiet";
    }
    @GetMapping("san-pham-chi-tiet/delete")
    public String delete(@RequestParam("id") Integer id, Model model){
        repoChiTietSanPham.deleteById(id);
        return "redirect:/bee-store/san-pham-chi-tiet";
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
        return "view/ChiTietSanPham";
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
