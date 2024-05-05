package task;

public class Result {
    private final String salesperson;
    private final String product;
    private final Long commission;
    private final Long bonus;

    public Result(String salesperson, String product, Long commission, Long bonus) {
        this.salesperson = salesperson;
        this.product = product;
        this.commission = commission;
        this.bonus = bonus;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public String getProduct() {
        return product;
    }

    public Long getCommission() {
        return commission;
    }

    public Long getBonus() {
        return bonus;
    }
}
