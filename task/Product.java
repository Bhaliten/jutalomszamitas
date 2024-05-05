package task;

public class Product {
    private final String name;
    private final Long bonus;

    public Product(String name, Long price) {
        this.name = name;
        this.bonus = this.calculateBonus(price);
    }

    private Long calculateBonus(Long price) {
        switch (this.name) {
            case "A":
                if (price < 9999999) {
                    return 0L;
                } else if (price < 19999999) {
                    return 25000L;
                } else {
                    return 40000L;
                }
            case "B":
                if (price < 7999999) {
                    return 0L;
                } else if (price < 15999999) {
                    return 30000L;
                } else {
                    return 50000L;
                }
            case "C":
                if (price < 4999999) {
                    return 0L;
                } else if (price < 9999999) {
                    return 20000L;
                } else {
                    return 40000L;
                }
            default:
                return 0L;
        }
    }

    public Long getBonus() {
        return bonus;
    }

    public String getName() {
        return name;
    }
}
