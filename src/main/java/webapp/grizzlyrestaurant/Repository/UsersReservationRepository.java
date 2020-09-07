package webapp.grizzlyrestaurant.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.grizzlyrestaurant.Entity.User;
import webapp.grizzlyrestaurant.Entity.UsersReservation;

@Repository
public interface UsersReservationRepository extends JpaRepository<UsersReservation, Long>  {
	public Optional<UsersReservation> findByUser(User user);
}