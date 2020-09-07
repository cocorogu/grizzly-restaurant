package webapp.grizzlyrestaurant.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.grizzlyrestaurant.Entity.OrderProduct;
import webapp.grizzlyrestaurant.Entity.Orders;
import webapp.grizzlyrestaurant.Entity.Product;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>  {
	
}