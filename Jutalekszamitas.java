import org.w3c.dom.Document;
import org.w3c.dom.Element;
import task.Product;
import task.Result;
import task.Sale;
import task.Salesperson;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jutalekszamitas {

    public static void main(String[] args) {
        Map<String, task.Salesperson> salespeople = Map.of(
                "Ü1", new task.Salesperson("Ü1"),
                "Ü2", new task.Salesperson("Ü2"),
                "Ü3", new task.Salesperson("Ü3")
        );

        readFile("eladasok.txt").stream().map(Jutalekszamitas::mapToSale)
                .forEach(sale -> salespeople.get(sale.getSalesperson()).addSale(sale));


        try {
            writeToXml("jutalekok.xml", mapSalespeopleToRows(List.copyOf(salespeople.values())));
        } catch (TransformerException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        System.out.println("DONE");

    }

    public static List<Result> mapSalespeopleToRows(List<task.Salesperson> salespeople) {
        List<Result> result = new ArrayList<>();

        salespeople.forEach(salesperson -> calculateProductsBySalesperson(salesperson)
                .forEach((product, price) -> result.add(
                                new Result(
                                        salesperson.getName(),
                                        product,
                                        calculatePercentage(price),
                                        (new Product(product, price)).getBonus()
                                )
                        )
                ));

        return result;
    }

    public static Map<String, Long> calculateProductsBySalesperson(Salesperson salesperson) {
        Map<String, Long> products = new HashMap<>(Map.of(
                "A", 0L,
                "B", 0L,
                "C", 0L
        ));

        salesperson.getSaleList().forEach(sale -> {
            Long current = products.get(sale.getProduct());
            products.put(sale.getProduct(), current + sale.getPrice());
        });

        return products;
    }

    public static Long calculatePercentage(Long total) {
        return total / 100;
    }

    public static List<String> readFile(String fileName) {
        List<String> rows = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
            String line = br.readLine();

            while (line != null) {
                rows.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

    public static void writeToXml(String fileName, List<Result> results) throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.newDocument();

        Element root = document.createElement("sales");
        document.appendChild(root);

        results.forEach(result -> {

            Element sale = document.createElement("sale");
            root.appendChild(sale);

            Element salesperson = document.createElement("salesperson");
            salesperson.appendChild(document.createTextNode(result.getSalesperson()));
            Element product = document.createElement("product");
            product.appendChild(document.createTextNode(result.getProduct()));
            Element commission = document.createElement("commission");
            commission.appendChild(document.createTextNode(String.valueOf(result.getCommission())));
            Element bonus = document.createElement("bonus");
            bonus.appendChild(document.createTextNode(String.valueOf(result.getBonus())));
            sale.appendChild(salesperson);
            sale.appendChild(product);
            sale.appendChild(commission);
            sale.appendChild(bonus);
        });


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);

        StreamResult result = new StreamResult(fileName);
        transformer.transform(source, result);
    }

    public static Sale mapToSale(String row) {
        String[] data = row.split("\\|");
        return new Sale(data[0], data[1], Long.valueOf(data[2]));
    }
}


