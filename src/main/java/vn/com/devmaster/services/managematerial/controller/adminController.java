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
import vn.com.devmaster.services.managematerial.domain.Category;
import vn.com.devmaster.services.managematerial.domain.Product;
import vn.com.devmaster.services.managematerial.service.FileUpload;
import vn.com.devmaster.services.managematerial.service.MaterialService;

import java.io.IOException;
import java.util.Date;


@Controller
@RequiredArgsConstructor
public class adminController {


    @Autowired
    MaterialService materialService;
    private final FileUpload fileUpload;

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
                                  @RequestParam(name = "key",required = false) String keyword,
                                  @RequestParam(name = "sort",required = false) Integer sort,
                                  @RequestParam(name = "category",required = false) Integer category,
                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Product> products = materialService.getAll(pageNo);
        if (keyword!=null) {
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
    public String showOrders() {
        return "features/orders-manage";
    }

    @PostMapping("/new-product")
    public String save(@RequestParam("name-product") String name,
                       @RequestParam("category") Integer category,
                    //   @RequestParam("quantity") Integer quantity,
                       @RequestParam("price") Double price,
                       @RequestParam("description") String description,
                       @RequestParam(name = "notes",required = false) String notes,
                    //   @RequestParam("product-active") Byte isactive,
                       @RequestParam(name = "image", required = false) MultipartFile multipartFile) throws IOException {

        String imageURL = fileUpload.uploadFile(multipartFile);
        Date date=new Date();
        Category cate= Category.builder().id(category).build();
        Product product = Product
                .builder()
                .name(name)
                .description(description)
                .notes(notes)
                .image(imageURL)
                .idcategory(cate)
                .price(price)
               // .quantity(quantity)
                .createdDate(date.toInstant())
                .build();
        materialService.saveProduct(product);
        return "redirect:/product-manage";
    }
    @GetMapping("/update-product")
    public String updateProduct(@RequestParam("id") Integer id,Model model){

        model.addAttribute("product",materialService.getProductByID(id));
        return"features/update-product";
    }

}
