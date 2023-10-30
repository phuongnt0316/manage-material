package vn.com.devmaster.services.managematerial.controller;

public interface IMaterialControll {
    public static final String MENU_CUSTOMER_LOGIN = "<a href='/account' class='d-flex align-items-center gap-2 dropdown-item'>" +
            "                                                    <i class='fa-regular fa-user'></i>" +
            "                                                    <p class='mb-0 fs-3'>Tài khoản</p> </a>" +
            "                                                <a href='/view-cart' class='d-flex align-items-center gap-2 dropdown-item'>" +
            "                                                    <i class='fa-solid fa-cart-shopping'></i>" +
            "                                                    <p class='mb-0 fs-3'>Giỏ hàng</p></a>" +
            "                                                <a href='/order-history' class='d-flex align-items-center gap-2 dropdown-item'>" +
            "                                                    <i class='fa-regular fa-file-lines'></i>" +
            "                                                    <p class='mb-0 fs-3'>Đơn hàng</p></a>" +
            "                                                <a href='/logout' class='btn btn-outline-primary mx-3 mt-2 d-block'>Đăng xuất</a>";
    public static final String MENU_CUSTOMER = "<a href='/view-cart' class='d-flex align-items-center gap-2 dropdown-item'>" +
            "                                                    <i class='fa-solid fa-cart-shopping'></i>" +
            "                                                    <p class='mb-0 fs-3'>Giỏ hàng</p></a>" +
            "                                                <a href='/login' class='btn btn-outline-primary mx-3 mt-2 d-block'>Đăng nhập</a>";
    public  static  final String EMPTY_CART="";
}
