package vn.com.devmaster.services.managematerial.projection;

public interface IProduct {
    Integer getId();
    String getName();
    String getImage();
    Double getPrice();
    Integer getQuantity();
    String getDescription();
    Double getSale();
}
