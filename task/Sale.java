package task;

public class Sale {
    private String product;
    private String salesperson;
    private Long price;

    public Sale(String product, String sales, Long price) {
        this.product = product;
        this.salesperson = sales;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
