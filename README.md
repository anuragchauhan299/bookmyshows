API Contract for Booking Service

Overview

- Purpose: Provide a stable contract for the Booking/Show API to support booking tickets for shows at theaters.
- Scope: Public API for front-end clients and partner apps to query shows and book seats.
- Versioning: v1 as initial contract; major changes require backward compatibility notes.

Table of contents

- API Contract (OpenAPI)
- Endpoints
- Error handling
- Security
- Data Model
- Platform & Deployment
- Design Patterns
- Scenario Implementation
- Non-Functional Requirements mapping
- Extensibility
- Glossary
- Next steps

API Contract (OpenAPI)

```yaml
openapi: 3.0.3
info:
  title: Booking Service API
  version: 1.0.0
  description: API contract for shows, theaters, bookings, and payments.
servers:
  - url: https://api.bookmyshow.local/v1
    description: Local dev
  - url: https://api.bookmyshow.prod/v1
    description: Production
tags:
  - name: shows
    description: Show-related endpoints
  - name: bookings
    description: Booking operations
  - name: payments
    description: Payment operations
  - name: users
    description: User operations
components:
  securitySchemes:
    BearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
  schemas:
    Show:
      type: object
      properties:
        id:
          type: string
          description: Show identifier
        theater_id:
          type: string
        movie_id:
          type: string
        start_time:
          type: string
          format: date-time
        end_time:
          type: string
          format: date-time
        price_class:
          type: string
          description: Price category (e.g., SILVER, GOLD)
        seats_available:
          type: integer
          description: Remaining seats
      required:
        - id
        - theater_id
        - movie_id
        - start_time
        - end_time
    BookingRequest:
      type: object
      properties:
        show_id:
          type: string
        user_id:
          type: string
        seats:
          type: array
          items:
            type: string
          description: List of seat identifiers
        payment_method:
          type: string
          description: Payment method (CARD, WALLET, UPI)
      required:
        - show_id
        - user_id
        - seats
        - payment_method
    BookingResponse:
      type: object
      properties:
        booking_id:
          type: string
        status:
          type: string
        total_price:
          type: number
        currency:
          type: string
        seats:
          type: array
          items:
            type: string
        show_id:
          type: string
        created_at:
          type: string
          format: date-time
      required:
        - booking_id
        - status
        - total_price
        - seats
        - show_id
        - created_at
    Error:
      type: object
      properties:
        code:
          type: string
        message:
          type: string
        details:
          type: object
  security:
    - BearerAuth: []
paths:
  /shows:
    get:
      tags: [shows]
      summary: List shows
      description: Retrieve list of shows with optional filters.
      parameters:
        - in: query
          name: theater_id
          required: false
          schema:
            type: string
        - in: query
          name: movie_id
          required: false
          schema:
            type: string
        - in: query
          name: date
          required: false
          schema:
            type: string
            format: date
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                type: object
                properties:
                  shows:
                    type: array
                    items:
                      $ref: '#/components/schemas/Show'
  /shows/{showId}:
    get:
      tags: [shows]
      summary: Get show details
      parameters:
        - in: path
          name: showId
          required: true
          schema:
            type: string
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Show'
  /bookings:
    post:
      tags: [bookings]
      summary: Create a new booking
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/BookingRequest'
      responses:
        '201':
          description: Created
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/BookingResponse'
        '409':
          description: Conflict (seats not available)
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
  /bookings/{bookingId}:
    get:
      tags: [bookings]
      summary: Get booking
      parameters:
        - in: path
          name: bookingId
          required: true
          schema:
            type: string
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                type: object
                properties:
                  booking_id:
                    type: string
                  status:
                    type: string
                  total_price:
                    type: number
                  seats:
                    type: array
                    items:
                      type: string
                  show_id:
                    type: string
                  created_at:
                    type: string
                    format: date-time
security:
- BearerAuth: []
```

Notes:

- The contract uses OpenAPI 3.0 for machine readability.
- The contract is versioned; future changes should be backward compatible or clearly versioned.

Endpoints details

- Shows
    - GET /shows: pagination support via query params? (page, size) optional
- Bookings
    - POST /bookings: expects seats array to reserve; returns booking_id and status.
- Security
    - Use JWT Bearer tokens; Authorization header: Bearer <token>

Data Model

- Entities
    - User(id, name, email, role)
    - Theater(id, name, location, city, state)
    - Movie(id, title, duration_minutes, language, age_rating)
    - Show(id, theater_id, movie_id, start_time, end_time, screen_number, price_class)
    - Seat(id, show_id, seat_label, row, number, type, status)
    - Booking(id, user_id, show_id, booking_time, status, total_price, currency)
    - BookingSeat(id, booking_id, seat_id, price)
    - Payment(id, booking_id, amount, currency, method, status, transaction_id, processed_at)

DB schema sketch

```sql
CREATE TABLE theatres (
  id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  city TEXT NOT NULL,
  state TEXT NOT NULL
);
CREATE TABLE movies (
  id UUID PRIMARY KEY,
  title TEXT NOT NULL,
  duration_minutes INT NOT NULL,
  language TEXT,
  age_rating TEXT
);
CREATE TABLE shows (
  id UUID PRIMARY KEY,
  theater_id UUID REFERENCES theatres(id),
  movie_id UUID REFERENCES movies(id),
  start_time TIMESTAMP WITH TIME ZONE NOT NULL,
  end_time TIMESTAMP WITH TIME ZONE NOT NULL,
  screen_number INT,
  price_class TEXT
);
CREATE TABLE seats (
  id UUID PRIMARY KEY,
  show_id UUID REFERENCES shows(id),
  seat_label TEXT,
  row TEXT,
  number INT,
  type TEXT,
  status TEXT
);
CREATE TABLE bookings (
  id UUID PRIMARY KEY,
  user_id UUID REFERENCES users(id),
  show_id UUID REFERENCES shows(id),
  booking_time TIMESTAMP WITH TIME ZONE NOT NULL,
  status TEXT,
  total_price DECIMAL(10,2),
  currency TEXT
);
CREATE TABLE booking_seats (
  id UUID PRIMARY KEY,
  booking_id UUID REFERENCES bookings(id),
  seat_id UUID REFERENCES seats(id),
  price DECIMAL(10,2)
);
CREATE TABLE payments (
  id UUID PRIMARY KEY,
  booking_id UUID REFERENCES bookings(id),
  amount DECIMAL(10,2),
  currency TEXT,
  method TEXT,
  status TEXT,
  transaction_id TEXT,
  processed_at TIMESTAMP WITH TIME ZONE
);
```

Platform & Deployment

- Architecture
    - API Gateway -> Service (Containerized Node.js/Express or FastAPI)
    - Microservice boundaries: core Booking service; separate Catalog service (Theaters, Movies, Shows) may be separate
    - Data store: PostgreSQL (Aurora compatible) with read replicas
    - Cache: Redis for seat map and available seats
    - Object storage: S3 for assets/logs
    - Observability: Prometheus + Grafana; OpenTelemetry traces
- Deployment
    - Dockerized service; Kubernetes or ECS
    - CI/CD: GitHub Actions; image scan; canary deployments
    - Security: JWT, OAuth2.0, least-privilege IAM
    - Backups: daily snapshots; point-in-time restore
- Non-functional requirements mapping
    - Performance: optimized queries; read replicas; pagination
    - Availability: multi-AZ; circuit breakers; idempotent endpoints
    - Consistency: strong consistency for Booking and Seat occupation
    - Observability: structured logs; trace IDs; metrics
    - Security: input validation; rate limiting; input sanitation

Design Patterns

- Hexagonal Architecture (Ports and Adapters)
- Repository pattern for data access
- Event-driven for booking success (domain events)
- Idempotent operations for bookings
- Anti-corruption layer between catalog and booking domains
- Data ownership: domain-driven design boundaries

Scenario Implementation

- Scenario: Book two seats for a Show

1) Client requests available shows with show_id and seats
2) Booking service validates seats availability using a read model and then locks seats in a transactional operation
3) Payment processed via Payment gateway; on success, booking status updates to CONFIRMED
4) If payment fails, seats are released

- Pseudo-code (high level)
    - lockSeats(show_id, seats) -> acquires DB row locks on seat rows
    - createBooking(user_id, show_id, seats, payment_method) -> creates booking and booking_seats
    - processPayment(booking_id, amount, method) -> external gateway call
    - onPaymentSuccess -> updateBookingStatus(booking_id, 'CONFIRMED')
    - onPaymentFailure -> releaseSeats(seats) and set booking status to 'CANCELLED'

Non-Functional Requirements coverage

- Consistency: cross-service seat locking; transactions with 2-phase commit or event-based eventual consistency
- Availability: read replicas; caching seat availability
- Security: OAuth2/JWT tokens; audit logging
- Observability: tracing, metrics, logs
- Resilience: retry policies; circuit breakers

Extensibility

- New payment methods; more price classes; dynamic pricing
- GraphQL layer optional for complex queries
- Additional endpoints for refunds, coupons, seating preferences
- Versioning strategy: v1 remains stable; v2 introduces new fields; deprecations announced

Glossary

- Booking: reservation of seats for a show
- Show: a screening of a movie in a theater
- Seat: a physical seat in a theater
- Price class: tier (SILVER, GOLD, PLATINUM)
- Read model: denormalized query-optimized projection

Next steps

- Add test suite for booking flows
- Create migrations for DB changes
- Add example client SDK
