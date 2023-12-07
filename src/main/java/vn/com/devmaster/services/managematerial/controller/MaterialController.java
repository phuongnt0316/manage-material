package vn.com.devmaster.services.managematerial.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.devmaster.services.managematerial.builders.MailBuilder;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;
import vn.com.devmaster.services.managematerial.service.EmailService;
import vn.com.devmaster.services.managematerial.service.MaterialService;
import vn.com.devmaster.services.managematerial.vnpay.VNPayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller

public class MaterialController implements IMaterialControll {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private VNPayService vnPayService;
    private final EmailService emailService;

    public MaterialController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("customer", new Customer());
        return "layout/login";
    }

    @GetMapping("/ministore")
    public String showIndex(Model model, HttpSession session) {
        setDesignMenu(session, model);
        model.addAttribute("saleProducts", materialService.saleProduct()); //sale>0
        model.addAttribute("products", materialService.getProductByQuantityDsc()); //statistical isactive=1
        model.addAttribute("blogs", materialService.getBlog());
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
                                       @RequestParam(required = false, name = "category") List<Integer> idCategoryList,
                                       @RequestParam(required = false, name = "price", defaultValue = "0") Integer price,
                                       @RequestParam(required = false, name = "key", defaultValue = "") String keyword,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
//        List<Product> products = materialService.getProduct();
//        model.addAttribute("categories", materialService.getAllCategory());
//        if (idcate != null) {
//            products = materialService.getProductByCategory(idcate);
//        }
        //Category
        List<Integer> idCates = new ArrayList<>();
        List<Category> categories = materialService.getAllCategory1();
        List<Integer> idCategories = new ArrayList<>();
        for (Category category : categories) {
            idCategories.add(category.getId());
        }

        if (idCategoryList == null) {
            idCates = idCategories;
        } else {
            idCates = idCategoryList.size() == categories.size() + 1 ? idCategories : idCategoryList;
        }


        //price
        double priceProduct1 = 0.0, priceProduct2 = 1000000000.0;
        switch (price) {
            case 0:
                priceProduct1 = 0.0;
                priceProduct2 = 1000000000.0;
                break;
            case 1:
                priceProduct2 = 10000000.0;
                break;
            case 2:
                priceProduct1 = 10000000.0;
                priceProduct2 = 15000000.0;
                break;
            case 3:
                priceProduct1 = 15000000.0;
                priceProduct2 = 20000000;
                break;
            case 4:
                priceProduct1 = 20000000.0;
                priceProduct2 = 25000000.0;
                break;
            case 5:
                priceProduct1 = 25000000.0;
                priceProduct2 = 30000000.0;
                break;
            case 6:
                priceProduct1 = 30000000.0;
                priceProduct2 = 100000000.0;
                break;

        }
        //Page<Product> products = materialService.getProduct(pageNo);
        List<Product> products = materialService.getProduct(idCates, priceProduct1, priceProduct2, keyword);
        if (keyword != null) {
            //products = materialService.searchProductbyUser(keyword.toUpperCase(), pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("size", products.size());
        model.addAttribute("categories", materialService.getAllCategory1());
        model.addAttribute("products", products);
//        model.addAttribute("totalPage", products.getTotalPages());
//        model.addAttribute("curentpage", pageNo);
        setDesignMenu(session, model);

        return "features/shop";
    }

    @GetMapping("/product-detail")
    public String getProductByID(@RequestParam("idpr") Integer idpr,
                                 Model model,
                                 HttpSession session,
                                 @ModelAttribute("rs") String rs) {
        setDesignMenu(session, model);
        model.addAttribute("rs",rs);
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
                           @ModelAttribute("rs") String rs,
                           @RequestParam(name = "idtransport", required = false) Integer idtransport) {
        setDesignMenu(session, model);
        model.addAttribute("customer", session.getAttribute("customer"));
//        model.addAttribute("design", MENU_CUSTOMER_LOGIN);
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
            model.addAttribute("rs", rs);

        } else {
            model.addAttribute("emptycart", "<p>Giỏ hàng trống</p>");
        }
        return "features/cart";
    }

    @GetMapping("/view-cart1")
    public String showCart1(Model model,
                            HttpSession session,
                            @ModelAttribute("rs") String rs,
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
            int totalMoney = materialService.getTotal(viewCarts) + ship;
            model.addAttribute("carts", viewCarts);
            model.addAttribute("ship", ship);
            model.addAttribute("totalCart", materialService.getTotal(viewCarts));
            model.addAttribute("totalmoney", totalMoney);
            model.addAttribute("transports", materialService.getTransport());
            model.addAttribute("total", materialService.getTotalProduct1(carts));
            model.addAttribute("rs", rs);
        }
        return "features/cart";
    }

    @GetMapping("/updateCart")
    public String updateCart(@RequestParam("idproduct") Integer idproduct,
                             @RequestParam("quantity") Integer quantity,
                             HttpSession session, Model model,
                             RedirectAttributes attributes) {
        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            int rs = materialService.updateQuantityCart(customer.getId(), idproduct, quantity);
            if (rs == 0) {
                attributes.addFlashAttribute("rs", "<script>\n" +
                        "    alert('Số lượng tồn kho không đủ');\n" +
                        "  </script>");
            } else {
                attributes.addFlashAttribute("rs", "<script>\n" +
                        "    alert('Cập nhật thành công!');\n" +
                        "  </script>");
            }
            return "redirect:/view-cart";
        } else {
            List<Cart> carts = (List<Cart>) session.getAttribute("carts");
            int rs = materialService.updateQuantityCart(carts, idproduct, quantity);
            if (rs == 0) {
                attributes.addFlashAttribute("rs", "<script>\n" +
                        "    alert('Số lượng tồn kho không đủ');\n" +
                        "  </script>");
            } else {
                attributes.addFlashAttribute("rs", "<script>\n" +
                        "    alert('Cập nhật thành công!');\n" +
                        "  </script>");
            }
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
    public String checkOut(Model model, HttpSession session, @RequestParam("idTransport") Integer idTransport) {
        setDesignMenu(session,model);
        if (session.getAttribute("customer") != null) {
            Customer customer = (Customer) session.getAttribute("customer");
            Integer idcustomer = customer.getId();

            List<IViewProduct> carts = materialService.getCartByIdCustomer(idcustomer);
            int ship = materialService.getShipMoney(idTransport, materialService.getTotalProduct(carts));
            model.addAttribute("carts", carts);
            model.addAttribute("totalCart", materialService.getTotalMoney(carts));
            model.addAttribute("totalmoney", materialService.getTotalMoney(carts) + ship);
            model.addAttribute("total", materialService.getTotalProduct(carts));
            model.addAttribute("ship", ship);
        } else {
            List<Cart> carts = (List<Cart>) session.getAttribute("carts");
            List<ViewCart> viewCarts = materialService.toViewCart(carts);
            int ship = materialService.getShipMoney(idTransport, materialService.getTotalProduct1(carts));
            model.addAttribute("carts", viewCarts);
            model.addAttribute("totalCart", materialService.getTotal(viewCarts));
            model.addAttribute("totalmoney", materialService.getTotal(viewCarts) + ship);
            model.addAttribute("total", materialService.getTotalProduct1(carts));
            model.addAttribute("ship", materialService.getShipMoney(idTransport, materialService.getTotalProduct1(carts)));
        }
        model.addAttribute("transport", materialService.getTransportByID(idTransport));
        model.addAttribute("payments", materialService.getPayment());
        session.setAttribute("idtransport", idTransport);
        return ("features/checkout");
    }

    @GetMapping("/buy-one-product") //check-out
    public String buyOneProduct(Model model,
                                HttpSession session,
                                @RequestParam("idTransport") Integer idTransport,
                                @RequestParam("idProduct") Integer idProduct,
                                @RequestParam("quantity") Integer quantity) {
        setDesignMenu(session, model);
//        if (session.getAttribute("customer") != null) {
//            Customer customer = (Customer) session.getAttribute("customer");
//        }

        Cart oneProduct= (Cart) session.getAttribute("oneProduct");
        Product product = materialService.getOneProduct(oneProduct.getIdProduct());
        int ship = materialService.getShipMoney(idTransport, oneProduct.getQuantity());
        int totalCart = (int) (product.getPrice() * (1.0 - product.getSale()) * oneProduct.getQuantity());
        model.addAttribute("product", product);
        model.addAttribute("totalCart", totalCart);
        model.addAttribute("totalmoney", totalCart + ship);
        model.addAttribute("total", oneProduct.getQuantity());
        model.addAttribute("ship", ship);
        model.addAttribute("quantity", oneProduct.getQuantity());

        model.addAttribute("transport", materialService.getTransportByID(idTransport));
        model.addAttribute("payments", materialService.getPayment());
        session.setAttribute("idTransport", idTransport);
        return ("features/buy-one-product");
    }

    @PostMapping("/order-one-product")
    public String orderOneProduct(
            HttpSession session,
            @RequestParam("fname") String fname,
            @RequestParam("phone") String phone,
            @RequestParam("address1") String address1,
            @RequestParam("detail-address") String detailadd,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam("listGroupRadios") Integer idpayment,
            HttpServletRequest request
            ) {

        Integer idTransport = (Integer) session.getAttribute("idTransport");
        Integer idCustomer = 0;
        Customer customer;
        if (session.getAttribute("customer") != null) {
            customer = (Customer) session.getAttribute("customer");
            idCustomer = customer.getId();
        }
        Cart oneProduct= (Cart) session.getAttribute("oneProduct");
        Product product = materialService.getOneProduct(oneProduct.getIdProduct());
        int ship = materialService.getShipMoney(idTransport, oneProduct.getQuantity());
        int total = (int) (oneProduct.getQuantity() * product.getPrice() * (1.0 - product.getSale()));
        int totalMoney = ship + total;
        Order order = materialService.setOrder(idCustomer, fname, detailadd, address1, note, phone, totalMoney);//insert orders
        session.setAttribute("order", order);
        String orderInfo = "Thanh toan: " + order.getIdorders();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = "";
        if (idpayment == 2) {
            vnpayUrl = vnPayService.createOrder(totalMoney, orderInfo, baseUrl);
        }
        session.setAttribute("idPayment", idpayment);
        session.setAttribute("rs",2);
        return "redirect:" + vnpayUrl;
    }

    @PostMapping("/order")
    public String order(
            HttpSession session,
            @RequestParam("fname") String fname,
            @RequestParam("phone") String phone,
            @RequestParam("address1") String address1,
            @RequestParam("detail-address") String detailadd,
            @RequestParam(name = "note", required = false) String note,
            @RequestParam("listGroupRadios") Integer idPayment,
            HttpServletRequest request) {

        Integer idtransport = (Integer) session.getAttribute("idtransport");
        Integer idcustomer = 0;
        Customer customer;
        if (session.getAttribute("customer") != null) {
            customer = (Customer) session.getAttribute("customer");
            idcustomer = customer.getId();
        }
        List<Cart> carts=new ArrayList<>();
            carts = session.getAttribute("customer") == null ? (List<Cart>) session.getAttribute("carts") : materialService.getCartByCustomer(idcustomer);

        int ship = materialService.getShipMoney(idtransport, carts.size());
        int total = materialService.getTotal(materialService.toViewCart(carts));
        int totalMoney = (int) (ship + total);
        Order order = materialService.setOrder(idcustomer, fname, detailadd, address1, note, phone, totalMoney);//insert orders
        session.setAttribute("order", order);
        String orderInfo = "Thanh toan: " + order.getIdorders();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = "";
        if (idPayment == 2) {
            vnpayUrl = vnPayService.createOrder(totalMoney, orderInfo, baseUrl);
        }
        session.setAttribute("idpayment", idPayment);
        session.setAttribute("rs",1);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request,
                            HttpSession session) {
        int rs= (int) session.getAttribute("rs");
        session.removeAttribute("rs");
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
            if(rs==1) {
                return "redirect:/order-action";
            }else {
                return "redirect:/order-action-one";
            }

        } else return "orderfail";
    }
    @GetMapping("order-action-one")
    public String orderActionOne(HttpSession session){
        Integer idtransport = (Integer) session.getAttribute("idTransport");
        Integer idpayment = (Integer) session.getAttribute("idPayment");
        Order order = (Order) session.getAttribute("order");
        order = materialService.saveOrder(order);
        Integer idcustomer = 0;
        List<Cart> carts=new ArrayList<>();
        if(session.getAttribute("customer")!=null){
            Customer customer= (Customer) session.getAttribute("customer");
            idcustomer=customer.getId();
        }
        Cart cart= (Cart) session.getAttribute("oneProduct");
        cart.setIdCustomer(idcustomer);
        carts.add(cart);
        int ship = materialService.getShipMoney(idtransport, cart.getQuantity());
        materialService.saveTransport(idtransport, ship, order);//insert orders_transport
        materialService.saveOrderPayment(idpayment, order); //insert orders_payment
        materialService.saveOrderDetail(carts, order); //insert orders detail
        session.removeAttribute("carts");
        session.removeAttribute("order");
        session.removeAttribute("idPayment");
        session.removeAttribute("idTransport");
        return "redirect:/ministore";

    }

    @GetMapping("order-action")
    String orderAction(HttpSession session) {
        Integer idtransport = (Integer) session.getAttribute("idtransport");
        Integer idpayment = (Integer) session.getAttribute("idpayment");
        Order order = (Order) session.getAttribute("order");
        order = materialService.saveOrder(order);
        Integer idcustomer = 0;
        List<Cart> carts=new ArrayList<>();
        if(session.getAttribute("customer")!=null){
            Customer customer= (Customer) session.getAttribute("customer");
            idcustomer=customer.getId();
            carts=materialService.getCartByCustomer(idcustomer);
            materialService.saveAllOrderDetail(idcustomer, order);
        }
        else {
            carts=(List<Cart>) session.getAttribute("carts");
            materialService.saveOrderDetail(carts, order); //insert orders detail
            session.removeAttribute("carts");
        }
        int ship = materialService.getShipMoney(idtransport, materialService.getTotalProduct1(carts));
        materialService.saveTransport(idtransport, ship, order);//insert orders_transport
        materialService.saveOrderPayment(idpayment, order); //insert orders_payment
        session.removeAttribute("order");
        session.removeAttribute("idpayment");
        session.removeAttribute("idtransport");
        return "redirect:/ministore";
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
        model.addAttribute("total", materialService.getTotalDetail(materialService.getOrderDetailByID(id)));
        return "features/order-detail";

    }

    @SneakyThrows
    @PostMapping("singin")
    public String singin(@ModelAttribute("customer") Customer customer, Model model) {
            if(!materialService.checkEmail(customer.getEmail())){
                model.addAttribute("singin", "<script>\n" +
                        "    alert('Email đã được đăng ký! ');\n" +
                        "  </script>");
            }
            if(!materialService.checkUserName(customer.getUsername())){
                model.addAttribute("singin", "<script>\n" +
                        "    alert('Tên người dùng đã tồn tại!');\n" +
                        "  </script>");
            }
        if (materialService.checkUserName(customer.getUsername())&&materialService.checkEmail(customer.getEmail())) {
            materialService.saveCustomer(customer);

                final Mail mail = new MailBuilder()
                        .From(EMAIL) // For gmail, this field is ignored.
                        .To(customer.getEmail())
                        .Template("mail-welcome.html")
                        .AddContext("subject", "Welcome you")
                        .AddContext("content", "")
                        .Subject("Welcome to Ministore!")
                        .createMail();
                emailService.sendHTMLEmail(mail);
                model.addAttribute("singin", "<script>\n" +
                    "    alert('Đăng ký thành công!');\n" +
                    "  </script>");
        }
        return "layout/login";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("blogs", materialService.getBlog());
        model.addAttribute("blogRelated",materialService.getBlogRelated());
        return "features/blog";
    }

    @GetMapping("/blog-detail")
    public String blogDetail(Model model, @RequestParam(name = "idBlog") Integer idBlog) {
        model.addAttribute("blog", materialService.getOneBlog(idBlog));
        model.addAttribute("blogs",materialService.getBlogRelated());
        return "features/blog-detail";
    }



    @GetMapping("forgot-password")
    public String forgotPassword() {
        return "features/forgot-password";
    }

    @PostMapping("forgot-password-action")
    public String sendMail(@RequestParam(name = "txtEmail") String email,
                           Model model,
                           HttpSession session) {
        final Mail mail = new MailBuilder()
                .From(EMAIL) // For gmail, this field is ignored.
                .To(email)
                .Template("mail-template.html")
                .AddContext("subject", "Forgot password")
                .AddContext("content", "Hi,")
                .Subject("Ministore, Bạn quên mật khẩu?")
                .createMail();
        try {
            this.emailService.sendHTMLEmail(mail);
        } catch (Exception e) {
            return "layout/error";
        }
        session.setAttribute("email", email);
        model.addAttribute("messages", "<script>\n" +
                "    alert('Hãy kiểm tra email của bạn');\n" +
                "  </script>");
        return "features/forgot-password";
    }

    @GetMapping("/showError")
    public String showError() {
        return "layout/error";
    }

    @GetMapping("/change-password")
    public String viewChangePassword() {
        return "features/change-password";
    }

    @PostMapping("/change-password-action")
    public String viewChangePassword(HttpSession session,
                                     @RequestParam("txtPassword") String password) {
        String email = (String) session.getAttribute("email");
        materialService.changePassword(email, password);
        session.removeAttribute("email");
        return "redirect:/login";
    }

    @GetMapping("/buyNow") //cart
    public String viewBuyNow(Model model,
                             HttpSession session,
                             @ModelAttribute("rs") String rs,
                             @RequestParam(name = "idtransport", required = false,defaultValue = "1") Integer idTransport,
                             @RequestParam(name = "idProduct",required = false) Integer idProduct,
                             @RequestParam(name = "quantity",required = false) Integer quantity,
                             RedirectAttributes attributes) {
        setDesignMenu(session, model);
        Cart oneProduct=new Cart();
        Product product=new Product();
        if(quantity==null||idProduct==null){
            oneProduct= (Cart) session.getAttribute("oneProduct");
            product=materialService.getOneProduct(oneProduct.getIdProduct());
        }
        else {
             product = materialService.getOneProduct(idProduct);

            if (quantity > product.getQuantity()) {
                oneProduct = session.getAttribute("oneProduct") != null ? (Cart) session.getAttribute("oneProduct") : Cart.builder().idProduct(idProduct).quantity(1).build();
                attributes.addFlashAttribute("rs", "<script>\n" +
                        "    alert('Số lượng tồn kho không đủ');\n" +
                        "  </script>");
                return "redirect:/buyNow";
            } else {
                oneProduct = Cart.builder().idProduct(idProduct).quantity(quantity).build();
                session.setAttribute("oneProduct", oneProduct);
            }
        }
            int ship = materialService.getShipMoney(idTransport, oneProduct.getQuantity());
            model.addAttribute("idtransport", idTransport);
            Double totalMoney = product.getPrice() * (1.0 - product.getSale()) * oneProduct.getQuantity() + ship;
            // model.addAttribute("carts", carts);
            model.addAttribute("product", product);
            model.addAttribute("ship", ship);
            model.addAttribute("quantity", oneProduct.getQuantity());
            model.addAttribute("totalCart", product.getPrice() * (1.0 - product.getSale()) * oneProduct.getQuantity());
            model.addAttribute("totalmoney", totalMoney);
            model.addAttribute("transports", materialService.getTransport());
            model.addAttribute("total", oneProduct.getQuantity());
            model.addAttribute("rs", rs);
            return "features/buy-now";
        }
    }



