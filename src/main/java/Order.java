import lombok.With;

import java.time.Instant;
import java.util.List;

public record Order(
        String id,
        List<Product> products,
        @With OrderStatus orderStatus,
        @With Instant orderTimestamp
) {
    public Order(String id, List<Product> products, OrderStatus orderStatus) {
        this(id, products, orderStatus, Instant.now());
    }
}
