import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    private ShopService shopService;
    private OrderListRepo orderRepo;
    @BeforeEach
    void setUp(){
        orderRepo = new OrderListRepo();
        shopService = new ShopService(new ProductRepo(),orderRepo);
    }

    @Test
    void addOrderTest() {
        //GIVEN

        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),OrderStatus.PROCESSING);
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
        Order order1 = new Order("1",List.of(new Product("1", "Apfel")),OrderStatus.PROCESSING );
        Order order2 = new Order("1",List.of(new Product("2", "Banana")),OrderStatus.COMPLETED );
        Order order3 = new Order("1",List.of(new Product("3", "Kiwi")),OrderStatus.IN_DELIVERY );
        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);
        orderRepo.addOrder(order3);
        List<Order> result = shopService.getOrderByStatus(OrderStatus.PROCESSING);
        System.out.println(result);
        assertEquals(1,result.size());
        assertTrue(result.contains(order1));
        assertFalse(result.contains(order2));
        assertFalse(result.contains(order3));

    }

    @Test
    void updateOrder() {
    }
}
