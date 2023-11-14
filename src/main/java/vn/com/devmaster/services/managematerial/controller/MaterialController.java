package vn.com.devmaster.services.managematerial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;
import vn.com.devmaster.services.managematerial.service.MaterialService;
import vn.com.devmaster.services.managematerial.vnpay.VNPayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller

public class MaterialController implements IMaterialControll {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("customer", new Customer());
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
    public String getProductByCategory(Model model,
                                       HttpSession session,
                                       @RequestParam(required = false, name = "idcate") String idcate,
                                       @RequestParam(required = false, name = "sort") String sort,
                                       @RequestParam(required = false, name = "key") String keyword,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
//        List<Product> products = materialService.getProduct();
//        model.addAttribute("categories", materialService.getAllCategory());
//        if (idcate != null) {
//            products = materialService.getProductByCategory(idcate);
//        }
        Page<Product> products = materialService.getProduct(pageNo);
        if (keyword != null) {
            products = materialService.searchProductbyUser(keyword.toUpperCase(), pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("size", products.getSize());
        model.addAttribute("categories", materialService.getAllCategory());
        model.addAttribute("products", products);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("curentpage", pageNo);
//        if (sort != null) {
//            Collections.sort(products, (a, b) -> {
//                int rs = (int) (a.getPrice() - b.getPrice());
//                if (sort.equals("ASC")) {
//                    return rs > 0 ? rs : (-rs);
//                } else {
//                    return rs > 0 ? (-rs) : rs;
//                }
//            });
//        }
        setDesignMenu(session, model);
        model.addAttribute("products", products);
        return "features/shop";
    }

    @GetMapping("/product-detail")
    public String getProductByID(@RequestParam("idpr") Integer idpr, Model model, HttpSession session) {
        setDesignMenu(session, model);
        model.addAttribute("product", materialService.getProductByID(idpr));
        model.addAttribute("images", materialService.getImageProduct(idpr));
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
            int ship = materialService.getShipMoney(idtransport, materialService.getTotalProduct(carts));
            model.addAttribute("idtransport", idtransport);

            Double totalMoney = materialService.getTotalMoney(carts) + ship;
            model.addAttribute("carts", carts);
            model.addAttribute("ship", ship);
            model.addAttribute("totalCart", materialService.getTotalMoney(carts));
            model.addAttribute("totalmoney", totalMoney);
            model.addAttribute("transports", materialService.getTransport());
            model.addAttribute("total", materialService.getTotalProduct(carts));

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
            int ship = materialService.getShipMoney(idtransport, materialService.getTotalProduct1(carts));
            model.addAttribute("idtransport", idtransport);
            List<ViewCart> viewCarts = materialService.toViewCart(carts);
            Double totalMoney = materialService.getTotal(viewCarts) + ship;
            model.addAttribute("carts", viewCarts);
            model.addAttribute("ship", ship);
            model.addAttribute("totalCart", materialService.getTotal(viewCarts));
            model.addAttribute("totalmoney", totalMoney);
            model.addAttribute("transports", materialService.getTransport());
            model.addAttribute("total", materialService.getTotalProduct1(carts));
        }
        return "features/cart";
    }

    @GetMapping("/updateCart")
    public String updateCart(@RequestParam("idproduct") Integer idproduct,
                             @RequestParam("quantity") Integer quantity,
                             HttpSession session) {
        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            materialService.updateQuantityCart(customer.getId(), idproduct, quantity);
            return "redirect:/view-cart";
        } else {
            List<Cart> carts = (List<Cart>) session.getAttribute("carts");
            materialService.updateQuantityCart(carts, idproduct, quantity);
            return "redirect:/view-cart1";
        }
    }

    @PostMapping("/login-action")
    public String Login(Model model, HttpSession session, @RequestParam(name = "txtEmail") String email, @RequestParam(name = "txtPassword") String password) {
        Customer customer = materialService.getCustomerByID(email, password);
        if (customer != null) {
            CustomUser userDetail;
            String url = "redirect:/product-manage";

            if (customer.getRole() == 0) {
                userDetail = new CustomUser(customer.getUsername(), customer.getPassword(), "USER");
                url = "redirect:/ministore";
            } else {
                userDetail = new CustomUser(customer.getUsername(), customer.getPassword(), "ADMIN");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            session.setAttribute("customer", customer);
            return url;

        } else {
            model.addAttribute("login", "<script>\n" +
                    "    alert('Sai tên đăng nhập hoặc mật khẩu!');\n" +
                    "  </script>");
            return "layout/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("customer");
        return "redirect:/login";
    }

    @GetMapping("/checkout")
    public String checkOut(Model model, HttpSession session, @RequestParam("transport") Integer idtransport) {
        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            Integer idcustomer = customer.getId();

            List<IViewProduct> carts = materialService.getCartByIdCustomer(idcustomer);
            int ship = materialService.getShipMoney(idtransport, materialService.getTotalProduct(carts));
            model.addAttribute("carts", carts);
            model.addAttribute("totalCart", materialService.getTotalMoney(carts));
            model.addAttribute("totalmoney", materialService.getTotalMoney(carts) + ship);
            model.addAttribute("total", materialService.getTotalProduct(carts));
            model.addAttribute("ship", ship);
        } else {
            List<Cart> carts = (List<Cart>) session.getAttribute("carts");
            List<ViewCart> viewCarts = materialService.toViewCart(carts);
            int ship = materialService.getShipMoney(idtransport, materialService.getTotalProduct1(carts));
            model.addAttribute("carts", viewCarts);
            model.addAttribute("totalCart", materialService.getTotal(viewCarts));
            model.addAttribute("totalmoney", materialService.getTotal(viewCarts) + ship);
            model.addAttribute("total", materialService.getTotalProduct1(carts));
            model.addAttribute("ship", materialService.getShipMoney(idtransport, materialService.getTotalProduct1(carts)));
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
            @RequestParam("listGroupRadios") Integer idpayment,
            HttpServletRequest request) {

        Integer idtransport = (Integer) session.getAttribute("idtransport");
        // session.removeAttribute("idtransport");
        Integer idcustomer = 0;
        Customer customer;
        if (session.getAttribute("customer") != null) {
            customer = (Customer) session.getAttribute("customer");
            idcustomer = customer.getId();
        }
        List<Cart> carts = session.getAttribute("customer") == null ? (List<Cart>) session.getAttribute("carts") : materialService.getCartByCustomer(idcustomer);
        //  int totalMoney=materialService.TotalMoney(idtransport,carts);
        int ship = materialService.getShipMoney(idtransport, carts.size());
        Double total = materialService.getTotal(materialService.toViewCart(carts));
        int totalMoney = (int) (ship + total);
        Order order = materialService.setOrder(idcustomer, fname, detailadd, address1, note, phone, Double.valueOf(totalMoney));//insert orders
        session.setAttribute("order", order);
        String orderInfo = "Thanh toan: " + order.getIdorders();
//        if (session.getAttribute("customer") != null) {
//           List<OrdersDetail>  ordersDetails=materialService.setListOrderDetail(idcustomer, order); //insert orders detail
//            session.setAttribute("orderDetailList",ordersDetails);
//        } else {
//           // materialService.saveOrderDetail(carts, order); //insert orders detail
//            session.removeAttribute("carts");
//        }


        //       materialService.saveTransport(idtransport, ship, order);//insert orders_transport
//        materialService.saveOrderPayment(idpayment, order); //insert orders_payment
//        if (session.getAttribute("customer") != null) {
//            return "redirect:/view-cart1";
//        }
//        return "redirect:/view-cart";
        //  OrdersPayment ordersPayment=OrdersPayment.builder().idpayment(idpayment).idord(order.getId()).build();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = "";
        if (idpayment == 2) {
            vnpayUrl = vnPayService.createOrder(totalMoney, orderInfo, baseUrl);
        }
        session.setAttribute("idpayment", idpayment);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request,
                             Model model, HttpSession session) {
        int paymentStatus = vnPayService.orderReturn(request);
//        OrdersPayment ordersPayment= (OrdersPayment) session.getAttribute("orderpayment");
//        String orderInfo = request.getParameter("vnp_OrderInfo");
//        String paymentTime = request.getParameter("vnp_PayDate");
//        String transactionId = request.getParameter("vnp_TransactionNo");
//        String totalPrice = request.getParameter("vnp_Amount");
//        model.addAttribute("orderId", orderInfo);
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("paymentTime", paymentTime);
//        model.addAttribute("transactionId", transactionId);
        if (paymentStatus == 1) {
            Integer idtransport = (Integer) session.getAttribute("idtransport");
            Integer idpayment = (Integer) session.getAttribute("idpayment");
            Order order = (Order) session.getAttribute("order");
            order = materialService.saveOrder(order);
            Integer idcustomer = 0;
            List<Cart> carts = session.getAttribute("customer") == null ? (List<Cart>) session.getAttribute("carts") : materialService.getCartByCustomer(idcustomer);
            if (session.getAttribute("customer") != null) {
                idcustomer = (Integer) session.getAttribute("customer");
                materialService.saveAllOrderDetail(idcustomer, order);
            } else {
                materialService.saveOrderDetail(carts, order); //insert orders detail
                session.removeAttribute("carts");
            }
            int ship = materialService.getShipMoney(idtransport, carts.size());
            materialService.saveTransport(idtransport, ship, order);//insert orders_transport
            materialService.saveOrderPayment(idpayment, order); //insert orders_payment
            session.removeAttribute("order");
            session.removeAttribute("idpayment");
            session.removeAttribute("idtransport");
            return "redirect:/ministore";

        } else return "orderfail";
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

    @PostMapping("singin")
    public String singin(@ModelAttribute("customer") Customer customer, Model model) {
        if (materialService.checkUserName(customer.getUsername())) {
            materialService.saveCustomer(customer);
        } else {
            model.addAttribute("singin", "<script>\n" +
                    "    alert('Tên người dùng đã tồn tại!');\n" +
                    "  </script>");
        }
        return "layout/login";
    }


}
