import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Step1: initialization of order and products
        OrderRepo orderRepo = new OrderListRepo(); //order realize
        ProductRepo productRepo = new ProductRepo(); //product to save all products
        //Step2: Initilization of ShopService(业务逻辑核心)
        ShopService shopService = new ShopService(productRepo,orderRepo);

        //Step3: add products to productRepo
        Product product1 = new Product("1","Apfel");
        Product product2 = new Product("2","Banane");
        Product product3 = new Product("3","Kiwi");
        Product product4 = new Product("4","Peach");
        Product product5 = new Product("5","Pinapple");
        Product product6 = new Product("6","Cherry");
        Product product7 = new Product("7","Himbeer");
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);
        productRepo.addProduct(product4);
        productRepo.addProduct(product5);
        productRepo.addProduct(product6);
        productRepo.addProduct(product7);

        //Step4: establish new orders （dispatch addOrder using productIds)
        List<String> productIds = List.of(product1.id(),product3.id(),product2.id(),product4.id(),product5.id(),product6.id(),product7.id());
        Order orders = shopService.addOrder(productIds);
        System.out.println(orders);

        //Step4(for test): manually add new orders (user OrderRepo.addOrder), firstly we need order (input of order should follow record order)
        //---this is suitable for test, not for main method (compared to the aforementioned shopService.addOrder)
            //--setup List<Product>
//        List<Product> productList = new ArrayList<>();
//        List<Product> productList2 = new ArrayList<>();
//        List<Product> productList3 = new ArrayList<>();
//        productList.add(product1);
//        productList.add(product2);
//        productList.add(product3);
//        productList2.add(product4);
//        productList2.add(product1);
//        productList3.add(product1);
//        productList3.add(product5);
//        productList3.add(product6);
//        productList3.add(product7);
             //--setup new order
//        Order order1 = new Order("1", productList, OrderStatus.PROCESSING, Instant.now());
//        Order order2 = new Order("2", productList2, OrderStatus.IN_DELIVERY, Instant.now());
//        Order order3 = new Order("3", productList3, OrderStatus.PROCESSING, Instant.now());
            //--manually add new orders
//        orderRepo.addOrder(order1);
//        orderRepo.addOrder(order2);
//        orderRepo.addOrder(order3);
        //getorders
//        OrderRepo processingOrders = shopService.getOrders();
//        System.out.println("all orders: " +processingOrders);
//        List<Order> getOrderByStatus = shopService.getOrderByStatus(OrderStatus.PROCESSING);
//        System.out.println("By status:" +getOrderByStatus);


        //Step 5: get order by status
        List<Order> orders1 = shopService.getOrderByStatus(OrderStatus.IN_DELIVERY);
        System.out.println("order1" +orders1);
        List<Order> orders2 = shopService.getOrderByStatus(OrderStatus.PROCESSING);
        System.out.println("order2" +orders2);

        //Step 6: update order status
        if(!orders2.isEmpty()){
            Order firstOrder = orders2.get(0);
            shopService.updateOrder(firstOrder.id(), OrderStatus.IN_DELIVERY);
            System.out.println("Bestellung aktualisiert: " + firstOrder);
        }

    }
}
