package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import java.util.Date;

@Controller
public class AdminController {
    @Autowired
    MaterialService materialService;

    @GetMapping("/admin")
    public String showAdmin() {
        return "layout/admin";
    }

    @GetMapping("/user-manage")
    public String showUserMana(Model model) {
        model.addAttribute("users", materialService.getAll());

        return "features/user-manage";
    }



    @GetMapping("/product-manage")
    public String showProductMana(Model model) {
        model.addAttribute("pro",new ProductDto());
        model.addAttribute("products", materialService.getAllProduct());
        return "features/product-manage";
    }

    @GetMapping("/orders-manage")
    public String showOrders() {
        return "features/orders-manage";
    }
    @PostMapping("/new-product")
    public String save(@ModelAttribute("pro") ProductDto dto)

    {

      dto.builder().createdDate(new Date().toInstant()).build();
        materialService.saveProduct(dto);
        return "redirect:/product-manage";
    }

}
