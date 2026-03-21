package movie.service.bookmyshow.controllers;


import movie.service.bookmyshow.dtos.BookTicketRequestDto;
import movie.service.bookmyshow.dtos.BookTicketResponseDto;
import movie.service.bookmyshow.dtos.PaymentDto;
import movie.service.bookmyshow.exceptions.BookTicketRequestValidationException;
import movie.service.bookmyshow.exceptions.InvalidUser;
import movie.service.bookmyshow.exceptions.SeatsAlreadyBookedException;
import movie.service.bookmyshow.models.Ticket;
import movie.service.bookmyshow.paymentgateway.PaymentRequest;
import movie.service.bookmyshow.paymentgateway.PaymentResult;
import movie.service.bookmyshow.services.PaymentService;
import movie.service.bookmyshow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final PaymentService paymentService;

    @Autowired
    public TicketController(TicketService ticketService, PaymentService paymentService) {
        this.ticketService = ticketService;
        this.paymentService = paymentService;
    }

    @PostMapping("/bookTicket")
    public BookTicketResponseDto bookTicket(@RequestBody BookTicketRequestDto dto) {
        try {
            validateRequest(dto);
            Ticket ticket = ticketService.bookTicket(dto.getShowSeatIds(), dto.getUserId());
            return BookTicketResponseDto.getSuccessResponse(ticket);
        } catch (BookTicketRequestValidationException | SeatsAlreadyBookedException | InvalidUser e) {
            return BookTicketResponseDto.getFailureResponse(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unhandled exception:" + e.getMessage());
            return BookTicketResponseDto.getFailureResponse(e.getMessage());
        }

    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Ticket> confirmBooking(@PathVariable Integer id, @RequestBody PaymentDto dto) {
        PaymentResult result = paymentService.processPayment(
                PaymentRequest.builder()
                        .bookingReference("")
                        .amount(dto.getAmount())
                        .currency("INR")
                        .customerEmail(dto.getEmail())
                        .customerPhone(dto.getPhone())
                        .metadata(Map.of("bookingId", id))
                        .build()
        );

        if (result.isSuccess()) {
            Ticket booking = ticketService.confirmBooking(id, result.getPaymentId(), "stripe");
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Ticket> cancelBooking(@PathVariable Integer id) {
        Ticket booking = ticketService.cancelBooking(id);
        return ResponseEntity.ok(booking);
    }

    private void validateRequest(BookTicketRequestDto dto) throws BookTicketRequestValidationException {
        if (dto.getUserId() < 0) {
            throw new BookTicketRequestValidationException("Invalid user id");
        }
        if (dto.getShowSeatIds() == null) {
            throw new BookTicketRequestValidationException("Seats not selected");
        }

        if (dto.getShowSeatIds().isEmpty()) {
            throw new BookTicketRequestValidationException("Atleast 1 seat should be selected");
        }

        if (dto.getShowSeatIds().size() > 10) {
            throw new BookTicketRequestValidationException("More than 10 seats can not be selected");
        }

    }

}
