package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.com.devmaster.services.managematerial.repository.MaterialRepository;
import vn.com.devmaster.services.managematerial.service.MaterialService;

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
    @GetMapping("/ministore")
    public String showIndex(){
        return "layout/index";
    }
    @GetMapping("/user-manage")
    public String showUser(Model model) {
        model.addAttribute("users",materialService.getAll());
        return "features/user-manage";
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
}
