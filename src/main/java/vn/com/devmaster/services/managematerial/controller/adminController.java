package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.CustomUser;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import java.util.Date;

@Controller
public class adminController {
    @Autowired
    MaterialService materialService;

    @GetMapping("/admin")
    public String showAdmin() {
        return "layout/admin";
    }

    @GetMapping("/user-manage")
    public String showUserMana(Model model) {
//        CustomUser currentUser=(CustomUser) SecurityContextHolder
//                .getContext()
//                        .getAuthentication()
//                                .getPrincipal();
//        currentUser.getUsername();
        model.addAttribute("users", materialService.getAll());

        return "features/user-manage";
    }



    @GetMapping("/product-manage")
    public String showProductMana(Model model,@Param("keyword") String keyword,@Param("category")String category, @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo) {
        Page<Product> products= this.materialService.getAll(pageNo);
        if(keyword!=null){
            products=materialService.searchProduct(keyword,pageNo);
            model.addAttribute("keyword",keyword);
        }
//        if(category!=null){
//            products=materialService
//        }
        model.addAttribute("pro",new ProductDto());
        model.addAttribute("products", products);
        model.addAttribute("totalPage",products.getTotalPages());
        model.addAttribute("curentpage",pageNo);
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
