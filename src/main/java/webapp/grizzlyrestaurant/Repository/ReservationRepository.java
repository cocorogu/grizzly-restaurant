package webapp.grizzlyrestaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.grizzlyrestaurant.Entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>  {

}