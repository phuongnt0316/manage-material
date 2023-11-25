package vn.com.devmaster.services.managematerial.projection;

public interface IViewProduct {
    Integer getIdProduct();
    String getName();
    Integer getQuantityCart();
    String getImage();
    Double getPrice();
    Integer getQuantityProduct();
    Integer getIsactive();

}
