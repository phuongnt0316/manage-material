package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.repository.MaterialRepository;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import java.util.List;


@Controller
public class MaterialController {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialService materialService;


    @GetMapping("/login")
    public String showLogin() {
        return "layout/login";
    }

    @GetMapping("/ministore")
    public String showIndex() {
        return "layout/index";
    }

    @GetMapping("/shop")
    public String showShop(Model model) {
        List<Category> categories=materialService.getAllCategory();
        model.addAttribute("products", materialService.getProduct());
        model.addAttribute("categories",materialService.getAllCategory());
        return "features/shop";
    }

    private void setcategory(String id) {

    }

//    @PostMapping("/login-check")
//    public String login() {
//        CustomUser userDetail = new CustomUser("admin", "admin", "ADMIN");
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, userDetail.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        return "redirect:/ministore";
//    }

    @GetMapping("/product-detail")
    public String getProductByID(@RequestParam("idpr") String idpr, Model model) {
        model.addAttribute("product", materialService.getProductByID(idpr));
        return "features/product-detail";
    }

    @GetMapping("/cart")
    public String save(@RequestParam("idpr") Integer idpr) {
        Cart cart = new Cart().builder().idCustomer(2).idProduct(idpr).quantity(1).build();
        materialService.save(cart);
        return "redirect:/view-cart";
    }

    @GetMapping("/view-cart")
    public String showCart(Model model, Integer idcustomer) {

        model.addAttribute("carts", materialService.getCartByIdCustomer(idcustomer));
        return "features/cart";
    }

    @PostMapping("/login-action")
    public String Login(Model model, @RequestParam(name = "txtEmail") String email, @RequestParam(name = "txtPassword") String password) {
        List<Customer> customers = materialService.getCustomerByID(email, password);
        Customer customer = customers.get(0);
        if (customers.size() > 0) {
            CustomUser userDetail = new CustomUser(customer.getUsername(), null, "ADMIN");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            model.addAttribute("customer", customers);
            model.addAttribute("design", "<a href=\"#\">Thông tin cá nhân</a>\n" +
                    "                                            <a href=\"#\">Đơn hàng</a>\n" +
                    "                                            <a href=\"#\">Giỏ hàng</a>\n" +
                    "                                            <a href=\"#\">Đăng xuất</a>");
          //   return "redirect:/admin";
            return "layout/index";
        } else {
            model.addAttribute("login", "<script>\n" +
                    "    alert('Sai tên đăng nhập hoặc mật khẩu!');\n" +
                    "  </script>");
            return "layout/login";
        }
    }

    @GetMapping("/checkout")
    public String checkOut(Model model, @RequestParam("idpr") String idpr) {
        model.addAttribute("product", materialService.getProductByID(idpr));
        model.addAttribute("order", new Order());
        return ("features/checkout");
    }


}
