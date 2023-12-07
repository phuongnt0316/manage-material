package vn.com.devmaster.services.managematerial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.exportToExcel.CustomerExport;
import vn.com.devmaster.services.managematerial.exportToExcel.OrderExport;
import vn.com.devmaster.services.managematerial.exportToExcel.ProductExport;
import vn.com.devmaster.services.managematerial.projection.IOrderInFor;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class adminController {
    @Autowired
    MaterialService materialService;

    @GetMapping("/admin")
    public String showAdmin(Model model, @RequestParam(name = "month", required = false, defaultValue = "11") int month) {
//        int year=2023;
//        model.addAttribute("chart_month",materialService.getRevenueByMonth());
//        model.addAttribute("chart_day",materialService.getRevenueByDay(month));
//        model.addAttribute("chart_category",materialService.getRevenueByCategory());
//        model.addAttribute("month_year",materialService.getMonthYear(year));
//        model.addAttribute("month",month);
        return "layout/admin";
    }

    @GetMapping("/revenue")
    public String showRevenue(Model model, @RequestParam(name = "month", required = false, defaultValue = "11") int month) {
        int year = 2023;
        model.addAttribute("revenue", materialService.getTotalRevenue(month));
        model.addAttribute("chart_month", materialService.getRevenueByMonth());
        model.addAttribute("chart_day", materialService.getRevenueByDay(month));
        model.addAttribute("chart_category", materialService.getRevenueByCategory());
        model.addAttribute("month_year", materialService.getMonthYear(year));
        model.addAttribute("month", month);
        return "features/revenue";
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
    public String showProductMana(Model model,
                                  @RequestParam(name = "key", required = false) String keyword,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Product> products = materialService.getAll(pageNo);
        if (keyword != null) {
            products = materialService.searchProduct(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("pro", new ProductDto());
        model.addAttribute("categories", materialService.getCategory());
        model.addAttribute("products", products);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("curentpage", pageNo);
        //   model.addAttribute("allproducts",materialService.getAllProduct());
        return "features/product-manage";
    }


    @GetMapping("/orders-manage")
    public String showOrders(Model model,
                             @RequestParam(name = "idcustomer", required = false) Integer idCustomer) {

        List<Order> orders = materialService.getAllOrders();
        if (idCustomer != null) {
            orders = materialService.getOrderByCustomer(idCustomer);
            model.addAttribute("customer", materialService.getCustomer(idCustomer));
        }
        model.addAttribute("orders", orders);
        List<Status> statusList = materialService.getStatus();
        model.addAttribute("status", statusList);
        return "features/orders-manage";
    }

    @GetMapping("/order-detail-manage")
    public String getOrderDetail(Model model, @RequestParam(name = "idod", required = false) Integer idod) {
        model.addAttribute("orderInfor", materialService.getOrderInfor(idod));
        model.addAttribute("orders", materialService.getOrderDetailByID(idod));
        List<Status> statusList = materialService.getStatus();
        model.addAttribute("status", statusList);
        //model.addAttribute("total", materialService.getToTal(materialService.getOrderDetailByID(idod)));
        return "features/order-detail-manage";
    }

    @GetMapping("/update-status-order")
    public String updateStatusOrder(@RequestParam(name = "idod", required = false) Integer idod,
                                    @RequestParam(name = "status", required = false) Integer status) {
        if (status == 5) {
            List<OrdersDetail> ordersDetails = materialService.getOrderDetailByIDOrder(idod);
            for (OrdersDetail ordt : ordersDetails) {
                materialService.updateQuantityProduct(ordt.getIdproduct(), ordt.getQty());
            }
        }
        materialService.updateOrderStatus(idod, status);
        return "redirect:/order-detail-manage?idod=" + idod;
    }

    @PostMapping("/new-product")
    public String save(@RequestParam("name-product") String name,
                       @RequestParam("category") Integer category,
                       @RequestParam("price") Double price,
                       @RequestParam("description") String description,
                       @RequestParam(name = "notes", required = false) String notes,
                       @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {

        materialService.saveProduct(name, description, notes, multipartFile, category, price);
        return "redirect:/product-manage";
    }

    @PostMapping("new-category")
    public String saveCategory(HttpSession session,
                               @RequestParam("name-category") String name,
                               @RequestParam(name = "notes", required = false) String notes,
                               @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {
        Customer customer = (Customer) session.getAttribute("customer");
        materialService.saveCategory(name, notes, multipartFile, customer.getId());
        return "redirect:/category-manage";

    }

    @GetMapping("update-category")
    public String showUpdateCategory(HttpSession session,
                                     Model model,
                                     @RequestParam(name = "idCategory") Integer idCategory) {
        model.addAttribute("category", materialService.getOneCategory(idCategory));
        return "features/update-category";

    }

    @PostMapping("update-category-action")
    public String updateCategory(HttpSession session,
                                 @RequestParam(name = "id-category",required = false) Integer id,
                                 @RequestParam(name = "name-category",required = false) String name,
                                 @RequestParam("category-active") Integer isActive,
                                 @RequestParam(name = "notes", required = false) String notes,
                                 @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {
        Customer customer = (Customer) session.getAttribute("customer");
        materialService.updateCategory(id,name,notes,multipartFile,customer.getId(),isActive);

        return "redirect:/category-manage";
    }

    @GetMapping("/image-product")
    public String getImageProduct(@RequestParam("idproduct") Integer idproduct, Model model) {
        model.addAttribute("images", materialService.getImageProduct(idproduct));
        model.addAttribute("idproduct", idproduct);
        return "features/image-product-manage";
    }

    @PostMapping("new-image-product")
    public String saveImageProduct(@RequestParam(name = "idproduct", required = false) Integer idproduct,
                                   @RequestParam(name = "image", required = false) MultipartFile multipartFile,
                                   @RequestParam(name = "image1", required = false) MultipartFile multipartFile1,
                                   @RequestParam(name = "image2", required = false) MultipartFile multipartFile2,
                                   @RequestParam(name = "image3", required = false) MultipartFile multipartFile3,
                                   @RequestParam(name = "image4", required = false) MultipartFile multipartFile4) throws IOException {
        materialService.saveImageProduct(idproduct, multipartFile, multipartFile1, multipartFile2, multipartFile3, multipartFile4);
        return "redirect:/image-product?idproduct=" + idproduct;

    }
    @GetMapping("/delete-image-product")
    public String deteleImage(@RequestParam("id") Integer id,
                              @RequestParam("idProduct") Integer idProduct){
        materialService.deleteImage(id);
        return "redirect:/image-product?idproduct="+idProduct;
    }

    @GetMapping("/update-product")
    public String updateProduct(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("categories", materialService.getAllCategory());
        model.addAttribute("product", materialService.getProductByID(id));
        return "features/update-product";
    }

    @GetMapping("add-quantity-product")
    public String addQuantity(Model model,
                              @RequestParam(name = "idProduct", required = false, defaultValue = "0") Integer idProduct,
                              @ModelAttribute("message") String message
    ) {
        Product product = new Product();
        if (idProduct != null) {
            product = materialService.getOneProduct(idProduct);
            model.addAttribute("product1", product);
        }
        model.addAttribute("idproduct", idProduct);
        model.addAttribute("products", materialService.getAllProduct());
        model.addAttribute("message", message);
        return ("features/add-quantity-product");

    }

    @PostMapping("/add-quantity-product-action")
    public String addQuantityProduct(@RequestParam(name = "idProduct", required = false) Integer idProduct,
                                     @RequestParam(name = "quantity", required = false, defaultValue = "0") Integer quantity,
                                     RedirectAttributes attributes) {
        materialService.updateQuantityProduct(idProduct, quantity);
        materialService.saveProductRecevied(idProduct, quantity);
        attributes.addFlashAttribute("message", "<script>\n" +
                "    alert('Đã nhập kho: ID sản phẩm:" + idProduct + "-Số lượng:" + quantity + "');" +
                "  </script>");
        return "redirect:/add-quantity-product";
    }

    @PostMapping("/update-product-action")
    public String updateProductAction(@RequestParam("id-product") Integer id,
                                      @RequestParam(name = "name-product", required = false) String name,
                                      @RequestParam(name = "category") Integer idCategory,
                                      @RequestParam(name = "price", required = false) Double price,
                                      @RequestParam(name = "description", required = false) String description,
                                      @RequestParam(name = "notes", required = false) String notes,
                                      @RequestParam("product-active") Byte isactive,
                                      @RequestParam("sale") Double sale,
                                      @RequestParam(name = "image", required = false) MultipartFile multipartFile,
                                      HttpSession session) throws IOException {
        Customer customer= (Customer) session.getAttribute("customer");
        materialService.updateProduct(id, name, idCategory, price, description, notes, isactive, multipartFile,sale,customer.getId());

        return "redirect:/product-manage";

    }

    @GetMapping("/payment")
    public String paymentMethod(Model model) {
        model.addAttribute("payments", materialService.getAllPayment());
        return "features/payment";

    }
    @GetMapping("/transport")
    public String transportMethod(Model model) {
        model.addAttribute("transports", materialService.getAllTransort());
        return "features/transport-manage";

    }

    @GetMapping("/update-payment")
    public String updatePayment(@RequestParam(name = "idpayment") Integer idPayment,
                                Model model) {
        model.addAttribute("payment", materialService.getOnePayment(idPayment));
        return "features/update-payment";
    }

    @PostMapping("/update-payment-action")
    public String updatePaymentMethod(@RequestParam(name = "idPayment", required = false) Integer idPayment,
                                      @RequestParam(name = "namePayment", required = false) String namePayment,
                                      @RequestParam(name = "notes", required = false) String notes,
                                      @RequestParam(name = "paymentActive") Byte paymentActive,
                                      @RequestParam(name = "imagePayment", required = false) MultipartFile multipartFile) throws IOException {
        materialService.updatePaymentMethod(idPayment, namePayment, notes, paymentActive, multipartFile);
        return "features/payment";
    }

    @GetMapping("/category-manage")
    public String getCategory(Model model) {
        model.addAttribute("categories", materialService.getAllCategory());
        return "features/category-manage";
    }

    @GetMapping("/product-inventory-manage")
    public String getProductInventory(Model model) {
        // int time=90;
        model.addAttribute("inventories", materialService.getProductInventory());
        // get product inventory list
        return "features/product-inventory-manage";
    }

    @GetMapping("/users-export")
    public void exportToExcelCustomer(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=user.xlsx";
        response.setHeader(headerKey, headerValue);
        List<Customer> customers = materialService.getAll();
        CustomerExport exportToExcel = new CustomerExport(customers);
        exportToExcel.export(response);

    }

    @GetMapping("/products-export")
    public void exportToExcelProduct(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=product.xlsx";
        response.setHeader(headerKey, headerValue);
        List<Product> products = materialService.getAllProduct();
        ProductExport exportToExcel = new ProductExport(products);
        exportToExcel.export(response);
    }

    @GetMapping("/orders-export")
    public void exportToExcelOrder(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=order.xlsx";
        response.setHeader(headerKey, headerValue);
        List<IOrderInFor> orders = materialService.getViewOrder();
        OrderExport exportToExcel = new OrderExport(orders);
        exportToExcel.export(response);
    }

    @GetMapping("/statistical")
    public String getStatistical(Model model) {
        model.addAttribute("products", materialService.getStatisticalProduct());
        return "features/statistical";
    }
}
