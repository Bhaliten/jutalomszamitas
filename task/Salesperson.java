package task;

import java.util.ArrayList;
import java.util.List;

public class Salesperson {
    private String name;
    private List<Sale> saleList;

    public Salesperson(String name) {
        this.name = name;
        this.saleList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }

    public void addSale(Sale sale) {
        this.saleList.add(sale);
    }

}
