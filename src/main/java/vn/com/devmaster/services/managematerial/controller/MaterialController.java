package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller

public class MaterialController implements IMaterialControll {
    @Autowired
    private MaterialService materialService;


    @GetMapping("/login")
    public String showLogin() {
        return "layout/login";
    }

    @GetMapping("/ministore")
    public String showIndex(Model model, HttpSession session) {
        setDesignMenu(session, model);
        model.addAttribute("products", materialService.getProduct());
        return "layout/index";
    }

    private void setDesignMenu(HttpSession session, Model model) {

        if (session.getAttribute("customer") != null) {
            model.addAttribute("check", 1);
            model.addAttribute("design", MENU_CUSTOMER_LOGIN);
        } else {
            model.addAttribute("check", 0);
            model.addAttribute("design", MENU_CUSTOMER);
        }
    }

    @GetMapping(path = {"/shop"})
    public String getProductByCategory(Model model, HttpSession session, @RequestParam(required = false, name = "idcate") String idcate, @RequestParam(required = false, name = "sort") String sort, @RequestParam(required = false, name = "txtSearch") String search) {
        List<Product> products = materialService.getProduct();
        model.addAttribute("categories", materialService.getAllCategory());
        if (idcate != null) {
            products = materialService.getProductByCategory(idcate);
        }
        if (sort != null) {
            Collections.sort(products, (a, b) -> {
                int rs = (int) (a.getPrice() - b.getPrice());
                if (sort.equals("ASC")) {
                    return rs > 0 ? rs : (-rs);
                } else {
                    return rs > 0 ? (-rs) : rs;
                }
            });
        }
        setDesignMenu(session, model);
        model.addAttribute("products", products);
        return "features/shop";
    }

    @GetMapping("/product-detail")
    public String getProductByID(@RequestParam("idpr") Integer idpr, Model model, HttpSession session) {
        setDesignMenu(session, model);
        model.addAttribute("product", materialService.getProductByID(idpr));
        return "features/product-detail";
    }

    @GetMapping("/cart")
    public String save(@RequestParam("idpr") Integer idpr, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Integer id = customer.getId();
        List<Cart> carts = materialService.getCartByCustomer(id);
        if (materialService.checkCart(carts, idpr) || carts.size() == 0) {
            Cart cart = new Cart().builder().idCustomer(id).idProduct(idpr).quantity(1).build();
            materialService.save(cart);
        } else {
            materialService.updateCart(id, idpr);
        }
        return "redirect:/view-cart";
    }

    @GetMapping("add-cart")
    public String addCart(HttpSession session, @RequestParam("idpr") Integer idpr) {
        List<Cart> carts = (List<Cart>) session.getAttribute("carts");
        carts = materialService.addCart(carts, idpr);
        session.setAttribute("carts", carts);
        return "redirect:/view-cart1";
    }


    @GetMapping("/deleteCart")
    public String deleteCart(@RequestParam("delete") Integer delete_idpr, @RequestParam("idcustomer") Integer idcustomer) {
        materialService.deleteProductCarts(delete_idpr, idcustomer);
        return "redirect:/view-cart";
    }

    @GetMapping("/view-cart")
    public String showCart(Model model,
                           HttpSession session,
                           @RequestParam(name = "idtransport", required = false) Integer idtransport) {
        model.addAttribute("customer", session.getAttribute("customer"));
        model.addAttribute("design", MENU_CUSTOMER_LOGIN);
        Customer customer = (Customer) session.getAttribute("customer");
        Integer idcustomer = customer.getId();
        List<IViewProduct> carts = materialService.getCartByIdCustomer(idcustomer);
        if (carts.size() != 0) {

            idtransport = idtransport == null ? 1 : idtransport;
            int ship = materialService.getShipMoney(idtransport, carts.size());
            model.addAttribute("idtransport", idtransport);

            Double totalMoney = materialService.getTotalMoney(carts) + ship;
            model.addAttribute("carts", carts);
            model.addAttribute("ship", ship);
            model.addAttribute("totalCart", materialService.getTotalMoney(carts));
            model.addAttribute("totalmoney", totalMoney);
            model.addAttribute("transports", materialService.getTransport());
            model.addAttribute("total", carts.size());

        } else {
            model.addAttribute("emptycart", "<p>Giỏ hàng trống</p>");
        }
        return "features/cart";
    }

    @GetMapping("/view-cart1")
    public String showCart1(Model model,
                            HttpSession session,
                            @RequestParam(name = "idtransport", required = false) Integer idtransport) {
        setDesignMenu(session, model);
        List<Cart> carts = (List<Cart>) session.getAttribute("carts");
        if (carts == null) {
            model.addAttribute("emptycart", "<p>Giỏ hàng trống</p>");
        } else {
            idtransport = idtransport == null ? 1 : idtransport;
            int ship = materialService.getShipMoney(idtransport, carts.size());
            model.addAttribute("idtransport", idtransport);
            List<ViewCart> viewCarts = materialService.toViewCart(carts);
            Double totalMoney = materialService.getTotal(viewCarts) + ship;
            model.addAttribute("carts", viewCarts);
            model.addAttribute("ship", ship);
            model.addAttribute("totalCart", materialService.getTotal(viewCarts));
            model.addAttribute("totalmoney", totalMoney);
            model.addAttribute("transports", materialService.getTransport());
            model.addAttribute("total", viewCarts.size());
        }
        return "features/cart";

    }

    @PostMapping("/login-action")
    public String Login(Model model, HttpSession session, @RequestParam(name = "txtEmail") String email, @RequestParam(name = "txtPassword") String password) {
        Customer customer = materialService.getCustomerByID(email, password);
        if (customer != null) {
            CustomUser userDetail = new CustomUser(customer.getUsername(), customer.getPassword(), "USER");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            session.setAttribute("customer", customer);
            return "redirect:/ministore";
        } else {
            model.addAttribute("login", "<script>\n" +
                    "    alert('Sai tên đăng nhập hoặc mật khẩu!');\n" +
                    "  </script>");
            return "layout/login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return null;
    }

    @GetMapping("/checkout")
    public String checkOut(Model model, HttpSession session, @RequestParam("transport") Integer idtransport) {
        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            Integer idcustomer = customer.getId();

            List<IViewProduct> carts = materialService.getCartByIdCustomer(idcustomer);
            int ship = materialService.getShipMoney(idtransport, carts.size());
            model.addAttribute("carts", carts);
            model.addAttribute("totalCart", materialService.getTotalMoney(carts));
            model.addAttribute("totalmoney", materialService.getTotalMoney(carts) + ship);
            model.addAttribute("total", carts.size());
            model.addAttribute("ship", ship);
        } else {
            List<Cart> carts = (List<Cart>) session.getAttribute("carts");
            List<ViewCart> viewCarts = materialService.toViewCart(carts);
            int ship = materialService.getShipMoney(idtransport, carts.size());
            model.addAttribute("carts", viewCarts);
            model.addAttribute("totalCart", materialService.getTotal(viewCarts));
            model.addAttribute("totalmoney", materialService.getTotal(viewCarts) + ship);
            model.addAttribute("total", viewCarts.size());
            model.addAttribute("ship", materialService.getShipMoney(idtransport, carts.size()));
        }
        model.addAttribute("transport", materialService.getTransportByID(idtransport));
        model.addAttribute("payments", materialService.getPayment());
        session.setAttribute("idtransport", idtransport);
        return ("features/checkout");
    }

    @PostMapping("/order")
    public String Order(
            HttpSession session,
            @RequestParam("fname") String fname,
            @RequestParam("phone") String phone,
            @RequestParam("address1") String address1,
            @RequestParam("detail-address") String detailadd,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam("listGroupRadios") Integer idpayment) {
        Integer idtransport = (Integer) session.getAttribute("idtransport");
        session.removeAttribute("idtransport");
        Integer idcustomer = 0;
        Customer customer;
        if (session.getAttribute("customer") != null) {
            customer = (Customer) session.getAttribute("customer");
            idcustomer = customer.getId();
        }
        List<Cart> carts = session.getAttribute("customer") == null ? (List<Cart>) session.getAttribute("carts"):materialService.getCartByCustomer(idcustomer);
        int ship = materialService.getShipMoney(idtransport, carts.size());
        Double total = materialService.getTotal(materialService.toViewCart(carts));
        Double totalMoney = ship + total;
        Order order = materialService.save(idcustomer, fname, detailadd, address1, note, phone, totalMoney);//insert orders

        if (session.getAttribute("customer") != null) {

//            for (IOrderDetailDTO iorder : detailDtoList) {
//                OrdersDetail ordersDetail = OrdersDetail
//                        .builder()
//                        .idord(order.getId())
//                        .idproduct(iorder.getIdproduct())
//                        .price(iorder.getPrice())
//                        .qty(iorder.getQuantity())
//                        .build();
//                ordersDetails.add(ordersDetail);
//            }

            materialService.saveAll(idcustomer, order); //insert orders detail

//            for (IOrderDetailDTO detail : detailDtoList) {
//                materialService.updateQuantityProduct(detail.getIdproduct(), (-detail.getQuantity())); //update quantity after buy product
//            }
        } else {
//            List<ViewCart> viewCarts = materialService.toViewCart(carts);
//            for (ViewCart viewCart : viewCarts) {
//                OrdersDetail ordersDetail = OrdersDetail
//                        .builder()
//                        .idord(order.getId())
//                        .idproduct(viewCart.getIdProduct())
//                        .price(viewCart.getPrice())
//                        .qty(viewCart.getQuantity())
//                        .build();
//                ordersDetails.add(ordersDetail);
//            }
            materialService.saveOrderDetail(carts, order); //insert orders detail
            session.removeAttribute("carts");
//            for (Cart cart : carts) {
//                materialService.updateQuantityProduct(cart.getIdProduct(), (-cart.getQuantity())); //update quantity after buy product
//            }
        }

//        OrdersPayment ordersPayment = OrdersPayment
//                .builder()
//                .idord(order.getId())
//                .idpayment(idpayment)
//                .build();
        materialService.saveOrderPayment(idpayment, order); //insert orders_payment
//        OrdersTransport ordersTransport = OrdersTransport
//                .builder()
//                .idord(order.getId())
//                .idtransport(idtransport)
//                .total(ship)
//                .build();
        materialService.saveTransport(idtransport, ship, order);//insert orders_transport
        if (session.getAttribute("customer") != null) {
//            materialService.BuyCarts(ordersDetails, idcustomer);//set cart done
//        } else {
//            session.removeAttribute("carts");//delete carts
            return "redirect:/view-cart1";
        }
        return "redirect:/view-cart";
    }

    @GetMapping("/order-history")
    public String OrderHistory(HttpSession session, Model model) {
        setDesignMenu(session, model);
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("orders", materialService.getOrderByCustomer(customer.getId()));
        return "features/order-history";

    }

    @GetMapping("/order-detail")
    public String OrderDetail(HttpSession session, Model model, @RequestParam("id") Integer id) {
        setDesignMenu(session, model);
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("orderInfor", materialService.getOrderInfor(id));
        model.addAttribute("orders", materialService.getOrderDetailByID(id));
        model.addAttribute("total", materialService.getToTal(materialService.getOrderDetailByID(id)));
        return "features/order-detail";

    }


}
