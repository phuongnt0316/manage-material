package vn.com.devmaster.services.managematerial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.com.devmaster.services.managematerial.domain.*;
import vn.com.devmaster.services.managematerial.mapper.CartMapper;
import vn.com.devmaster.services.managematerial.mapper.ProductMapper;
import vn.com.devmaster.services.managematerial.projection.IOrderDetailDTO;
import vn.com.devmaster.services.managematerial.projection.IOrderInFor;
import vn.com.devmaster.services.managematerial.projection.IViewProduct;
import vn.com.devmaster.services.managematerial.repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    private TransportMethodRepository transportMethodRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrdersDetailRepository ordersDetailRepository;
    @Autowired
    private OrdersPaymentRepository ordersPaymentRepository;
    @Autowired
    private OrdersTransportRepository ordersTransportRepository;
    private final FileUpload fileUpload;
    @Autowired
    private ProductImageRepository productImageRepository;

    public List<Customer> getAll() {
        List<Customer> customers =customerRepository.findAll();
        return customers;
    }

    public List<Product> getProduct() {
        List<Product> products = productRepository.getProduct();
        return products;
    }

    public List<Product> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Product getProductByID(Integer idpr) {
        Product product = productRepository.getProductByID(idpr);
        return product;
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public List<IViewProduct> getCartByIdCustomer(Integer idcustomer) {
        List<IViewProduct> carts = cartRepository.getCartByIdCustomer(idcustomer);
        return carts;
    }

    public Customer getCustomerByID(String userName, String password) {
        Customer customer = customerRepository.getCustomerByID(userName, password);
        return customer;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.getAllCategory1();
        return categories;
    }

    public List<Product> getProductByCategory(String idcate) {
        List<Product> products = productRepository.getProductByCategory(idcate);
        return products;
    }

    public Page<Product> getAll(Integer pageNo) {


        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        return this.productRepository.findAll(pageable);
    }

    public Page<Product> searchProduct(String keyword, Integer pageNo) {
        List<Product> products = productRepository.searchProduct(keyword);
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = (pageable.getOffset() + pageable.getPageSize()) > products.size() ? products.size() : (int) pageable.getOffset() + pageable.getPageSize();
        products = products.subList(start, end);
        return new PageImpl<Product>(products, pageable, productRepository.searchProduct(keyword).size());
    }

    public void deleteProductCarts(Integer delete_idpr, Integer idcustomer) {
        cartRepository.deleteProductCarts(delete_idpr, idcustomer);
    }

    public void BuyCarts(Integer idproduct, Integer idcustomer) {
        cartRepository.BuyCarts(idproduct, idcustomer);
    }


    public List<TransportMethod> getTransport() {
        return transportMethodRepository.getTransport();
    }

    public List<PaymentMethod> getPayment() {
        return paymentMethodRepository.getPayment();
    }


    public List<IOrderDetailDTO> getCartById(Integer idcustomer) {
        return cartRepository.getCartByID(idcustomer);
    }

    public void saveAll(Integer idcustomer, Order order) {
        List<OrdersDetail> ordersDetails = new ArrayList<>();

        List<IOrderDetailDTO> detailDtoList = getCartById(idcustomer);

        for (IOrderDetailDTO iorder : detailDtoList) {
            OrdersDetail ordersDetail = OrdersDetail
                    .builder()
                    .idord(order.getId())
                    .idproduct(iorder.getIdproduct())
                    .price(iorder.getPrice())
                    .qty(iorder.getQuantity())
                    .build();
            ordersDetails.add(ordersDetail);
        }
        for (IOrderDetailDTO detail : detailDtoList) {
            updateQuantityProduct(detail.getIdproduct(), (-detail.getQuantity())); //update quantity after buy product
        }

        ordersDetailRepository.saveAll(ordersDetails);
        BuyCarts(ordersDetails, idcustomer);
    }

    public void save(OrdersPayment ordersPayment) {
        ordersPaymentRepository.save(ordersPayment);
    }

    public void save(OrdersTransport ordersTransport) {
        ordersTransportRepository.save(ordersTransport);
    }

    public void BuyCarts(List<OrdersDetail> ordersDetails, Integer id) {
        for (OrdersDetail ordersDetail : ordersDetails) {
            BuyCarts(ordersDetail.getIdproduct(), id);
        }
    }

    public List<Order> getOrderByCustomer(Integer id) {
        return orderRepository.getOrderByCustomer(id);
    }

    public String getOrderId(Integer idcustomer) {
        LocalDate localDate = LocalDate.now();

        String date = localDate.getYear() + "" + localDate.getMonth().getValue() + "" + localDate.getDayOfMonth();
        int n = orderRepository.getCountByCustomer(idcustomer);
        //String.format("%03d", n+1) format dạng 001,010
        String orderid = date + String.format("%03d", idcustomer) + (n + 1) + "";
        return orderid;
    }

    public IOrderInFor getOrderInfor(Integer id) {
        return orderRepository.getorderInfor(id);
    }

    public List<IViewProduct> getOrderDetailByID(Integer id) {
        return ordersDetailRepository.getOrderDetailByID(id);
    }

    public void updateQuantityProduct(Integer idproduct, Integer qty) { // update quantity after order
        productRepository.updateQuantityProduct(idproduct, qty);
    }

    public List<Cart> getCartByCustomer(Integer id) {
        return cartRepository.getCartByCustomer(id);
    }

    public void updateCart(Integer id, Integer idpr) {
        cartRepository.updateCart(id, idpr);
    }

    public Page<Product> getProductByCategory(Integer category, Integer pageNo) {
        List<Product> products = productRepository.getProductByCategory(String.valueOf(category));
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = (pageable.getOffset() + pageable.getPageSize()) > products.size() ? products.size() : (int) pageable.getOffset() + pageable.getPageSize();
        products = products.subList(start, end);
        return new PageImpl<Product>(products, pageable, products.size());
    }

    public Page<Product> sortProduct(Integer sort, Integer pageNo) {
        List<Product> products = productRepository.sortProductByPriceASC();
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = (pageable.getOffset() + pageable.getPageSize()) > products.size() ? products.size() : (int) pageable.getOffset() + pageable.getPageSize();
        products = products.subList(start, end);
        return new PageImpl<Product>(products, pageable, productRepository.findAll().size());
    }

    public boolean checkCart(List<Cart> carts, Integer id) {
        boolean check = true;
        for (Cart cart : carts) {
            if (cart.getIdProduct() == id) {
                check = false;
                break;
            }
        }
        return check;
    }

    public Double getTotalMoney(List<IViewProduct> carts) {
        Double sum = 0.0;
        for (IViewProduct cart : carts) {
            sum += cart.getPrice()*cart.getQuantity();
        }
        return sum;
    }

    public Double getTotal(List<ViewCart> carts) {
        Double sum = 0.0;
        for (ViewCart cart : carts) {
            sum += cart.getPrice()*cart.getQuantity();
        }
        return sum;
    }

    public int getToTal(List<IViewProduct> iViewProducts) {
        int total = 0;
        for (IViewProduct iViewProduct : iViewProducts) {
            total += iViewProduct.getQuantity() * iViewProduct.getPrice();
        }
        return total;
    }

    public List<ViewCart> toViewCart(List<Cart> carts) {
        List<ViewCart> viewCarts = new ArrayList<>();
        for (Cart cart : carts) {
            Product product = getProductByID(cart.getIdProduct());
            ViewCart viewCart = ViewCart
                    .builder()
                    .idProduct(cart.getIdProduct())
                    .name(product.getName())
                    .image(product.getImage())
                    .price(product.getPrice())
                    .quantity(cart.getQuantity())
                    .build();
            viewCarts.add(viewCart);
        }
        return viewCarts;
    }

    public void updateCart(List<Cart> carts, Integer id) {

        for (Cart cart : carts) {
            if (cart.getIdProduct() == id) {
                cart.setQuantity(cart.getQuantity() + 1);
                break;
            }
        }

    }


    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Order getOneOrder(Integer idod) {
        return orderRepository.getOne(idod);
    }

    public List<Cart> addCart(List<Cart> carts, Integer idpr) {
        if (carts == null) {
            carts = new ArrayList<>();
            Cart cart = new Cart().builder().idCustomer(0).idProduct(idpr).quantity(1).build();
            carts.add(cart);
        } else {
            if (checkCart(carts, idpr)) {
                Cart cart = new Cart().builder().idCustomer(0).idProduct(idpr).quantity(1).build();
                carts.add(cart);
            } else {
                updateCart(carts, idpr);
            }
        }
        return carts;
    }

    public void updateProduct(Integer id, String name, Integer idcategory, Double price, String description, String notes, Byte isactive, MultipartFile multipartFile) throws IOException {
        Product product = getProductByID(id);
        name = name.trim().isEmpty() ? product.getName() : name;
        price = (price == null) ? product.getPrice() : price;
        description = description.trim().isEmpty() ? product.getDescription() : description;
        notes = notes.trim().isEmpty() ? product.getNotes() : notes;
        String urlImage = multipartFile.isEmpty() ? product.getImage() : fileUpload.uploadFile(multipartFile);
        Date date = new Date();
        productRepository.updateProduct(id, name, idcategory, price, description, notes, isactive, urlImage, date.toInstant());
    }

    public List<Status> getStatus() {
        List<Status> statusList = new ArrayList<>();
        Status status = new Status(1, "Đã đặt đơn");
        statusList.add(status);
        status = new Status(2, "Đang chuẩn bị");
        statusList.add(status);
        status = new Status(3, "Đang giao hàng");
        statusList.add(status);
        status = new Status(4, "Đã giao hàng");
        statusList.add(status);
        return statusList;
    }

    public void updateOrderStatus(Integer idod, Integer status) {
        orderRepository.updateOrderStatus(idod, status);
    }

    public Customer getCustomer(Integer idcustomer) {
        return customerRepository.getOne(idcustomer);
    }

    public int getShipMoney(Integer idtransport, Integer size) {
        int ship = idtransport == 4 ? 0 : 15000 * size;
        return ship;
    }

    public TransportMethod getTransportByID(Integer idtransport) {
        return transportMethodRepository.getOne(idtransport);
    }


    public Order save(Integer idcustomer, String fname, String detailadd, String address1, String note, String phone, Double totalMoney) {
        String idorder = getOrderId(idcustomer);
        Customer customer = Customer.builder().id(idcustomer).build();
        Order order = Order
                .builder()
                .idorders(idorder)
                .ordersDate(new Date().toInstant())
                .idcustomer(customer)
                .nameReciver(fname)
                .status(1)
                .address(detailadd.concat(", ").concat(address1))
                .notes(note)
                .phone(phone)
                .totalMoney(totalMoney)
                .build();
        orderRepository.save(order);
        return order;
    }

    public void saveOrderDetail(List<Cart> carts, Order order) {
        List<OrdersDetail> ordersDetails = new ArrayList<>();
        List<ViewCart> viewCarts = toViewCart(carts);
        for (ViewCart viewCart : viewCarts) {
            OrdersDetail ordersDetail = OrdersDetail
                    .builder()
                    .idord(order.getId())
                    .idproduct(viewCart.getIdProduct())
                    .price(viewCart.getPrice())
                    .qty(viewCart.getQuantity())
                    .build();
            ordersDetails.add(ordersDetail);
        }
        ordersDetailRepository.saveAll(ordersDetails);
        for (Cart cart : carts) {
            updateQuantityProduct(cart.getIdProduct(), (-cart.getQuantity())); //update quantity after buy product
        }
    }

    public void saveOrderPayment(Integer idpayment, Order order, String totalPrice) {
        OrdersPayment ordersPayment = OrdersPayment
                .builder()
                .idord(order.getId())
                .idpayment(idpayment)
                .total(Integer.valueOf(totalPrice))
                .build();
        ordersPaymentRepository.save(ordersPayment);
    }

    public void saveTransport(Integer idtransport, int ship, Order order) {
        OrdersTransport ordersTransport = OrdersTransport
                .builder()
                .idord(order.getId())
                .idtransport(idtransport)
                .total(ship)
                .build();
        ordersTransportRepository.save(ordersTransport);
    }

    public void saveProduct(String name, String description, String notes, MultipartFile multipartFile, Integer category, Double price) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);
        Date date = new Date();
        Category cate = Category.builder().id(category).build();
        Product product = Product
                .builder()
                .name(name)
                .description(description)
                .notes(notes)
                .image(imageURL)
                .idcategory(cate)
                .price(price)
                .quantity(0)
                .createdDate(date.toInstant())
                .isactive((byte) 1)
                .build();
        productRepository.save(product);
    }

    public List<ProductImage> getImageProduct(Integer idproduct) {
        return productImageRepository.getImageProduct(idproduct);
    }
    public ProductImage setProductImage(Product product,MultipartFile multipartFile) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);
        return ProductImage
                .builder()
                .url(imageURL)
                .idProduct(product)
                .name(multipartFile.getName())
                .build();
    }
    public void saveImageProduct(Integer idproduct, MultipartFile multipartFile, MultipartFile multipartFile1, MultipartFile multipartFile2, MultipartFile multipartFile3, MultipartFile multipartFile4) throws IOException {
        List<ProductImage> productImages = new ArrayList<>();
        Product product = Product.builder().id(idproduct).build();
        ProductImage productImage;
        if (!multipartFile.isEmpty()) {
            productImage=setProductImage(product,multipartFile);
            productImages.add(setProductImage(product,multipartFile));
        }
        if (!multipartFile1.isEmpty()) {
            productImages.add(setProductImage(product,multipartFile1));
        }
        if (!multipartFile2.isEmpty()) {
            productImages.add(setProductImage(product,multipartFile2));
        }
        if (!multipartFile3.isEmpty()) {
            productImages.add(setProductImage(product,multipartFile3));
        }
        if (!multipartFile4.isEmpty()) {
            productImages.add(setProductImage(product,multipartFile4));
        }
        productImageRepository.saveAll(productImages);
    }

    public void updateQuantityCart(Integer id, Integer idproduct, Integer quantity) {
        cartRepository.updateQuantityCart(id,idproduct,quantity);
    }

    public void updateQuantityCart(List<Cart> carts, Integer idproduct, Integer quantity) {
        for(Cart cart:carts){
            if(cart.getIdProduct()==idproduct){
                cart.setQuantity(quantity);
                break;
            }
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public boolean checkUserName(String userName) {
        if(customerRepository.getCustomerByUsername(userName)==null){
            return true;
        }
        return false;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public int getTotalProduct(List<IViewProduct> carts) {
        int rs=0;
        for(IViewProduct cart:carts){
            rs+=cart.getQuantity();
        }
        return  rs;
    }

    public Integer getTotalProduct1(List<Cart> carts) {
        int rs=0;
        for(Cart cart:carts){
            rs+=cart.getQuantity();
        }
        return  rs;
    }

    public Page<Product> getProduct(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        return this.productRepository.getProduct(pageable);
    }

    public Page<Product> searchProductbyUser(String keyword, Integer pageNo) {
        List<Product> products = productRepository.searchProductbyUser(keyword);
        Pageable pageable = PageRequest.of(pageNo - 1, 9);
        Integer start = (int) pageable.getOffset();
        Integer end = (pageable.getOffset() + pageable.getPageSize()) > products.size() ? products.size() : (int) pageable.getOffset() + pageable.getPageSize();
        products = products.subList(start, end);
        return new PageImpl<Product>(products, pageable, productRepository.searchProductbyUser(keyword).size());
    }

    public void saveOrderPayment(OrdersPayment ordersPayment) {
        ordersPaymentRepository.save(ordersPayment);
    }
}
