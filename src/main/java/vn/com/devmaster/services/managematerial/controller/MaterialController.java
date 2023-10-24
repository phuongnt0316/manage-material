package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.projection.IViewCart;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
//@RequestMapping("/ministore")

public class MaterialController implements IMaterialControll{
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
    @GetMapping(path = {"/shop"})
  public String getProductByCategory(Model model,@RequestParam(required=false,name="idcate") String idcate,@RequestParam(required = false,name = "sort") String sort,@RequestParam(required = false,name = "txtSearch") String search){
        List<Product> products=materialService.getProduct();
        model.addAttribute("categories",materialService.getAllCategory());
        if(idcate!=null) {
            products = materialService.getProductByCategory(idcate);
        }
        if(sort!=null){
            Collections.sort(products,(a,b)->{
                int rs= (int) (a.getPrice()-b.getPrice());
                if(sort.equals("ASC")){
                   return rs>0?rs:(-rs);
                }
                else {
                    return rs>0?(-rs):rs;
                }
            });
        }
        model.addAttribute("products",products);
        return "features/shop";

  }
      @GetMapping("/product-detail")
    public String getProductByID(@RequestParam("idpr") String idpr, Model model) {
        model.addAttribute("product", materialService.getProductByID(idpr));
        return "features/product-detail";
    }

    @GetMapping("/cart")
    public String save(@RequestParam("idpr") Integer idpr, HttpSession session) {
        Customer customer=(Customer) session.getAttribute("customer");
        Integer id=customer.getId();
        Cart cart = new Cart().builder().idCustomer(id).idProduct(idpr).quantity(1).build();
        materialService.save(cart);
        return "redirect:/view-cart";
    }
@GetMapping("/deleteCart")
public String deleteCart(@RequestParam("delete") String delete_idpr,@RequestParam("idcustomer") String idcustomer){
    materialService.deleteProductCarts(delete_idpr,idcustomer);
    return "redirect:/view-cart";
}
    @GetMapping("/view-cart")
    public String showCart(Model model, HttpSession session)  {
        if(session.getAttribute("customer")!=null) {
            Customer customer = (Customer) session.getAttribute("customer");
            String idcustomer = String.valueOf(customer.getId());
            List<IViewCart> carts = materialService.getCartByIdCustomer(idcustomer);
            if (carts.size() == 0) {
                model.addAttribute("emptycart", "<p>Giỏ hàng trống</p>");
            } else {
                model.addAttribute("customer", session.getAttribute("customer"));
                model.addAttribute("carts", carts);
                model.addAttribute("totalmoney", getTotalMoney(carts));
                model.addAttribute("total", carts.size());
                model.addAttribute("design", session.getAttribute("design"));
                model.addAttribute("transports", materialService.getTransport());

            }
            return "features/cart";
        }


        else {
            return "redirect:/login";
        }


    }

    private Integer getTotalMoney(List <IViewCart> carts) {
        int sum=0;
        for(IViewCart cart:carts){
            sum+=cart.getPrice();
        }
        return sum;
    }

    @PostMapping("/login-action")
    public String Login(Model model, HttpSession session, @RequestParam(name = "txtEmail") String email, @RequestParam(name = "txtPassword") String password) {
        List<Customer> customers = materialService.getCustomerByID(email, password);
        if (customers.size() > 0) {
            Customer customer = customers.get(0);
            CustomUser userDetail = new CustomUser(customer.getUsername(), customer.getPassword(), "USER");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail,null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //model.addAttribute("customer", customers);
            session.setAttribute("customer",customer);
            model.addAttribute("customer",session.getAttribute("customer"));
           // session.setAttribute("idcustomer",customer.getId());
            session.setAttribute("design",MENU_CUSTOMER);
            model.addAttribute("design", MENU_CUSTOMER);
            // return "redirect:/admin";
            return "layout/index";
        } else {
            model.addAttribute("login", "<script>\n" +
                    "    alert('Sai tên đăng nhập hoặc mật khẩu!');\n" +
                    "  </script>");
            return "layout/login";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        return null;
    }
    @GetMapping("/checkout")
    public String checkOut(Model model) {
        model.addAttribute("payments",materialService.getPayment());
        model.addAttribute("order", new Order());
        return ("features/checkout");
    }


}
