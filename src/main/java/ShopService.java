import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    //    private OrderRepo orderRepo = new OrderMapRepo();
//    private OrderRepo orders = new OrderListRepo();
    private final OrderRepo orders;

    public Order addOrder(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            products.add(productToOrder.get());
        }

//        Order newOrder = new Order(UUID.randomUUID().toString(), products);
//        return orderRepo.addOrder(newOrder);

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);
        return orders.addOrder(newOrder);

    }

    // New method to filter orders by order status using streams
    public List<Order> getOrderByStatus(OrderStatus orderStatus){
        return orders.getOrders().stream()
                .filter(order->order.orderStatus().equals(orderStatus))
                .collect(Collectors.toList());
    }
    // New method to update an order's status
    public Order updateOrder(String orderId, OrderStatus newStatus) throws OrderNotFoundException{
        Order existingOrder = orders.getOrderById(orderId);
        if(existingOrder == null){
            throw new OrderNotFoundException("Order mit der Id: " + orderId + " wurde nicht gefunden!");
        }
        Order updateOrder = existingOrder.withOrderStatus(newStatus);
        orders.removeOrder(orderId);
        orders.addOrder(updateOrder);
        return updateOrder;
    }
}
