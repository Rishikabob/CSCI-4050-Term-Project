package c3.theater.springweb.repositories;

import c3.theater.springweb.domain.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
