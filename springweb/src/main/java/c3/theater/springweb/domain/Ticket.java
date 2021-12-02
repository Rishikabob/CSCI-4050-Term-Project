package c3.theater.springweb.domain;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketId;
    private Long screeningId;
    private int seatNum;
    private Long userId;

    public Ticket() {
    }

    public Ticket(Long ticketId, Long screeningId, int seatNum, Long userId) {
        this.ticketId = ticketId;
        this.screeningId = screeningId;
        this.seatNum = seatNum;
        this.userId = userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(Long screeningId) {
        this.screeningId = screeningId;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (seatNum != ticket.seatNum) return false;
        if (ticketId != null ? !ticketId.equals(ticket.ticketId) : ticket.ticketId != null) return false;
        if (screeningId != null ? !screeningId.equals(ticket.screeningId) : ticket.screeningId != null) return false;
        return userId != null ? userId.equals(ticket.userId) : ticket.userId == null;
    }

    @Override
    public int hashCode() {
        int result = ticketId != null ? ticketId.hashCode() : 0;
        result = 31 * result + (screeningId != null ? screeningId.hashCode() : 0);
        result = 31 * result + seatNum;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}