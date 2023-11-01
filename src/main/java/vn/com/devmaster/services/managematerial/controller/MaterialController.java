package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.projection.IOrderDetailDTO;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;
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
    public String showIndex(Model model,HttpSession session) {
        setDesignMenu(session,model);
        model.addAttribute("products",materialService.getProduct());
        return "layout/index";
    }
    private void setDesignMenu(HttpSession session,Model model){
        if(session.getAttribute("customer")!=null) {
            model.addAttribute("design",MENU_CUSTOMER_LOGIN);
        }
        else {
            model.addAttribute("design",MENU_CUSTOMER);

        }
    }
    @GetMapping(path = {"/shop"})
  public String getProductByCategory(Model model,HttpSession session,@RequestParam(required=false,name="idcate") String idcate,@RequestParam(required = false,name = "sort") String sort,@RequestParam(required = false,name = "txtSearch") String search){
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
        setDesignMenu(session,model);
        model.addAttribute("products",products);
        return "features/shop";

  }
      @GetMapping("/product-detail")
    public String getProductByID(@RequestParam("idpr") Integer idpr, Model model,HttpSession session) {
        setDesignMenu(session,model);
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
public String deleteCart(@RequestParam("delete") Integer delete_idpr, @RequestParam("idcustomer") Integer idcustomer){
    materialService.deleteProductCarts(delete_idpr,idcustomer);
    return "redirect:/view-cart";
}
    @GetMapping("/view-cart")
    public String showCart(Model model, HttpSession session)  {

        if(session.getAttribute("customer")!=null) {
            model.addAttribute("customer",session.getAttribute("customer"));
            model.addAttribute("design",MENU_CUSTOMER_LOGIN);
            Customer customer = (Customer) session.getAttribute("customer");
            Integer idcustomer=customer.getId();
            List<IViewProduct> carts = materialService.getCartByIdCustomer(idcustomer);
            if (carts.size() !=0) {
                model.addAttribute("carts", carts);
                model.addAttribute("totalmoney", getTotalMoney(carts));
                model.addAttribute("transports", materialService.getTransport());


            } else {
                model.addAttribute("emptycart", "<p>Giỏ hàng trống</p>");

            }
            model.addAttribute("total", carts.size());
            return "features/cart";
        }


        else {
            model.addAttribute("design",MENU_CUSTOMER);
            return "features/cart";
        }
    }
    private Integer getTotalMoney(List <IViewProduct> carts) {
        int sum=0;
        for(IViewProduct cart:carts){
            sum+=cart.getPrice();
        }
        return sum;
    }
    @PostMapping("/login-action")
    public String Login(Model model, HttpSession session, @RequestParam(name = "txtEmail") String email, @RequestParam(name = "txtPassword") String password) {
        Customer customer = materialService.getCustomerByID(email, password);
        if (customer!=null) {
            CustomUser userDetail = new CustomUser(customer.getUsername(), customer.getPassword(), "USER");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail,null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            session.setAttribute("customer",customer);
            return "redirect:/ministore";
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
    public String checkOut(Model model,HttpSession session,@RequestParam("transport") Integer idtransport) {
        if(session.getAttribute("customer")!=null) {
            Customer customer = (Customer) session.getAttribute("customer");
            Integer idcustomer =customer.getId();
            List<IViewProduct> carts = materialService.getCartByIdCustomer(idcustomer);
            model.addAttribute("carts",carts);
            model.addAttribute("totalmoney", getTotalMoney(carts));
            model.addAttribute("total", carts.size());
        }
        model.addAttribute("payments",materialService.getPayment());
        //model.addAttribute("transport", idtransport);
        return ("features/checkout");
    }

    @PostMapping("/order")
    public String Order(
            HttpSession session,
            @RequestParam("fname") String fname,
            @RequestParam("phone") String phone,
            @RequestParam("address1") String address1,
            @RequestParam("detail-address") String detailadd,
            @RequestParam("note") String note,
            @RequestParam("listGroupRadios") Integer idpayment){
        Integer idtransport=1;
        Customer customer = (Customer) session.getAttribute("customer");
        String idorder=materialService.getOrderId(customer.getId());
        Order order= Order.builder().idorders(idorder).ordersDate(new Date().toInstant()).idcustomer(customer).nameReciver(fname).address(detailadd.concat(", ").concat(address1)).notes(note).phone(phone).build();
        materialService.save(order);//insert orders
        List<IOrderDetailDTO> detailDtoList=materialService.getCartById(customer.getId());
        List<OrdersDetail>ordersDetails=new ArrayList<>();
        for(IOrderDetailDTO iorder:detailDtoList){
            OrdersDetail ordersDetail=OrdersDetail.builder().idord(order.getId()).idproduct(iorder.getIdproduct()).price(iorder.getPrice()).qty(iorder.getQuantity()).build();
            ordersDetails.add(ordersDetail);
        }
        materialService.saveAll(ordersDetails); //insert orders detail
        OrdersPayment ordersPayment= OrdersPayment.builder().idord(order.getId()).idpayment(idpayment).build();
        materialService.save(ordersPayment); //insert orders_payment
        OrdersTransport ordersTransport=OrdersTransport.builder().idord(order.getId()).idtransport(idtransport).build();
        materialService.save(ordersTransport);//insert orders_transport

        materialService.BuyCarts(ordersDetails,customer.getId());
        return "redirect:/view-cart";
    }
    @GetMapping("/order-history")
    public String OrderHistory(HttpSession session,Model model){
        setDesignMenu(session,model);
        Customer customer= (Customer) session.getAttribute("customer");
        model.addAttribute("orders",materialService.getOrderByCustomer(customer.getId()));
        return "features/order-history";

    }
    @GetMapping("/order-detail")
    public String OrderDetail(HttpSession session,Model model,@RequestParam("id") Integer id){
        setDesignMenu(session,model);
        Customer customer= (Customer) session.getAttribute("customer");
        model.addAttribute("orderInfor",materialService.getOrderInfor(id));
        model.addAttribute("orders",materialService.getOrderDetailByID(id));
        model.addAttribute("total",getToTal(materialService.getOrderDetailByID(id)));
        return "features/order-detail";

    }
    public int getToTal(List<IViewProduct> iViewProducts){
        int total=0;
        for(IViewProduct iViewProduct:iViewProducts){
            total+=iViewProduct.getQuantity()*iViewProduct.getPrice();
        }
        return total;
    }
}
