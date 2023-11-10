package vn.com.devmaster.services.managematerial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.com.devmaster.services.managematerial.DTO.ProductDto;
import vn.com.devmaster.services.managematerial.domain.Order;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.domain.Status;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
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
    public String showProductMana(Model model,
                                  @RequestParam(name = "key", required = false) String keyword,
                                  @RequestParam(name = "sort", required = false) Integer sort,
                                  @RequestParam(name = "category", required = false) Integer category,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Product> products = materialService.getAll(pageNo);
        if (keyword != null) {
            products = materialService.searchProduct(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        if (category != null) {
            products = materialService.getProductByCategory(category, pageNo);
            model.addAttribute("category", category);
        }
        if (sort != null) {
            products = materialService.sortProduct(sort, pageNo);
            model.addAttribute("sort", sort);
        }
        model.addAttribute("pro", new ProductDto());
        model.addAttribute("categories", materialService.getAllCategory());
        model.addAttribute("products", products);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("curentpage", pageNo);
        return "features/product-manage";
    }


    @GetMapping("/orders-manage")
    public String showOrders(Model model,
                             @RequestParam(name = "idcustomer", required = false) Integer idcustomer,
                             @RequestParam(name = "choices-single-defaul",required = false) Integer idsatus) {

        List<Order> orders = materialService.getAllOrders();
        if (idcustomer != null) {
            orders = materialService.getOrderByCustomer(idcustomer);
            model.addAttribute("customer", materialService.getCustomer(idcustomer));
        }
        model.addAttribute("orders", orders);
        List<Status> statusList = materialService.getStatus();
        model.addAttribute("status", statusList);
        return "features/orders-manage";
    }

    @GetMapping("/order-detail-manage")
    public String getOrderDetail(@RequestParam(name = "idod", required = false) Integer idod,
                                 @RequestParam(name = "status", required = false) Integer status,
                                 Model model) {
        model.addAttribute("orderInfor", materialService.getOrderInfor(idod));
        model.addAttribute("orders", materialService.getOrderDetailByID(idod));
        List<Status> statusList = materialService.getStatus();
        model.addAttribute("status", statusList);
        //model.addAttribute("total", materialService.getToTal(materialService.getOrderDetailByID(idod)));
        materialService.updateOrderStatus(idod, status);
        return "features/order-detail-manage";
    }

    @PostMapping("/new-product")
    public String save(@RequestParam("name-product") String name,
                       @RequestParam("category") Integer category,
                       //   @RequestParam("quantity") Integer quantity,
                       @RequestParam("price") Double price,
                       @RequestParam("description") String description,
                       @RequestParam(name = "notes", required = false) String notes,
                       //   @RequestParam("product-active") Byte isactive,
                       @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {

        materialService.saveProduct(name, description, notes, multipartFile, category, price);
        return "redirect:/product-manage";
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
        return "redirect:/image-product?idproduct="+idproduct;

    }

    @GetMapping("/update-product")
    public String updateProduct(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("categories", materialService.getAllCategory());
        model.addAttribute("product", materialService.getProductByID(id));
        return "features/update-product";
    }

    @PostMapping("/update-product-action")
    public String updateProductAction(@RequestParam("id-product") Integer id,
                                      @RequestParam(name = "name-product", required = false) String name,
                                      @RequestParam(name = "category") Integer idcategory,
                                      @RequestParam(name = "price", required = false) Double price,
                                      @RequestParam(name = "description", required = false) String description,
                                      @RequestParam(name = "notes", required = false) String notes,
                                      @RequestParam("product-active") Byte isactive,
                                      @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {
        materialService.updateProduct(id, name, idcategory, price, description, notes, isactive, multipartFile);
        return "redirect:/product-manage";
    }


}
