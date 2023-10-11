package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.devmaster.services.managematerial.domain.Cart;
import vn.com.devmaster.services.managematerial.repository.MaterialRepository;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import java.util.List;

@Controller
public class MaterialController {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialService materialService;

    @GetMapping("/admin")
    public String showAdmin(){
        return "layout/admin";
    }
    @GetMapping("/login")
    public String showLogin(){
        return "layout/login";
    }
    @GetMapping("/ministore")
    public String showIndex(){
        return "layout/index";
    }
    @GetMapping("/user-manage")
    public String showUserMana(Model model) {
        model.addAttribute("users",materialService.getAll());
        return "features/user-manage";
    }
    @GetMapping("product-manage")
    public String showProductMana(Model model) {
        model.addAttribute("products",materialService.getAllProduct());
        return "features/product-manage";
    }
    @GetMapping("/orders-manage")
    public String showOrders() {
        return "features/orders-manage";
    }

    @GetMapping("/shop")
    public String showShop(Model model) {
    model.addAttribute("products",materialService.getProduct());
        return "features/shop";
    }
    @GetMapping("/product-detail")
    public String getProductByID(@RequestParam("idpr") String idpr, Model model) {
        model.addAttribute("product",materialService.getProductByID(idpr));
        return "features/product-detail";
    }
    @GetMapping("/cart")
        public String save(@RequestParam("idpr") Integer idpr)
        {
            Cart cart1=new Cart().builder().idCustomer(1).idProduct(idpr).quantity(1).build();
            materialService.save(cart1);
            return "redirect:/view-cart";
        }
    @GetMapping("/view-cart")
    public String showCart(){
        return "features/cart";
    }

}
