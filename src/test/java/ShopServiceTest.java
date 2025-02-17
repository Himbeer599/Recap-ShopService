import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ShopService shopService;
    private OrderListRepo orderRepo;

    @BeforeEach
    void setUp() {
        orderRepo = new OrderListRepo();
        shopService = new ShopService(new ProductRepo(), orderRepo);
    }

    @Test
    void addOrderTest() {
        //GIVEN

        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void getOrderByStatusTest() {
        //given
        Order order1 = new Order("1", List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        Order order2 = new Order("1", List.of(new Product("2", "Banana")), OrderStatus.COMPLETED);
        Order order3 = new Order("1", List.of(new Product("3", "Kiwi")), OrderStatus.IN_DELIVERY);
        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);
        orderRepo.addOrder(order3);
        //when
        List<Order> result = shopService.getOrderByStatus(OrderStatus.PROCESSING);
        System.out.println(result);
        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(order1));
        assertFalse(result.contains(order2));
        assertFalse(result.contains(order3));

    }

    @Test
    void updateOrder() {
        //given (模拟化）
        Order exisitingOrder;
        final String orderId = "1";
        final OrderStatus newStatus = OrderStatus.IN_DELIVERY;
        exisitingOrder = new Order(orderId, List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        orderRepo.addOrder(exisitingOrder);
        //when (excution)
        Order updatedOrder = shopService.updateOrder(orderId, newStatus);

        //then(verification)
        assertNotNull(updatedOrder);
        assertEquals(newStatus, updatedOrder.orderStatus());
        //verification2
        Order retrievedOrder = orderRepo.getOrderById(orderId);
        assertEquals(newStatus, retrievedOrder.orderStatus());
    }

    @Test
    void testUpdateOrder_OrderNotFound() {
        //Given
        Order exisitingOrder;
        final String orderId = "1";
        final OrderStatus newStatus = OrderStatus.IN_DELIVERY;
        exisitingOrder = new Order(orderId, List.of(new Product("1", "Apfel")), OrderStatus.PROCESSING);
        orderRepo.addOrder(exisitingOrder);

        String notExisitedId = "5";
        //When & Then
        assertThrows(OrderNotFoundException.class, () -> shopService.updateOrder(notExisitedId, newStatus));
    }
}
