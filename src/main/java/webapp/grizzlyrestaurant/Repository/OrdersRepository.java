package webapp.grizzlyrestaurant.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.grizzlyrestaurant.Entity.Orders;
import webapp.grizzlyrestaurant.Entity.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>  {
	public Optional<Orders> findByUser(User u);
}