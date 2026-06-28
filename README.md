# Postgraduate MCP Travel Agent — Booking MCP Server

Quarkus [MCP server](https://docs.quarkiverse.io/quarkus-mcp-server/dev/index.html) that exposes travel **booking tools** over HTTP/SSE. It holds booking lookup and cancellation logic that was extracted from the [travel-agency](../travel-agency/) AI agent so booking can run as a separate, reusable service.

This repository is part of a postgraduate course on integrating AI into Java applications with Quarkus and the Model Context Protocol (MCP).

## Related project

| Project | Role | Default port |
|---------|------|--------------|
| **booking-mcp-server** (this repo) | MCP server — booking tools (`getBookingDetails`, `cancelBooking`) | `8081` |
| [travel-agency](../travel-agency/) | AI travel agent — RAG + Ollama, consumes booking tools via MCP client | `8080` |

Clone both repositories side by side (or adjust the MCP URL in travel-agency if you deploy elsewhere):

```shell
git clone git@github.com:MatheusFilipeFreitas/Postgraduate-MCP-Travel-Agent-Quarkus-Java.git booking-mcp-server
git clone git@github.com:MatheusFilipeFreitas/Postgraduate-AI-Integration-Quarkus-Java.git travel-agency
```

## Tech stack

| Component | Version |
|-----------|---------|
| Java | 25 |
| Quarkus | 3.27.4.1 |
| MCP Server | `quarkus-mcp-server-sse` |
| Build tool | Maven |

## Prerequisites

- JDK 25+

## Getting started

### 1. Run the MCP server

```shell
./mvnw quarkus:dev
```

The server listens on **http://localhost:8081**. MCP SSE endpoint:

```text
http://localhost:8081/mcp/sse
```

Quarkus Dev UI (dev mode only): [http://localhost:8081/q/dev/](http://localhost:8081/q/dev/) — use the **MCP Server** card to inspect and call tools.

### 2. Connect travel-agency

Start [travel-agency](../travel-agency/) on port `8080`. It connects to this server via:

```properties
quarkus.langchain4j.mcp.booking.transport-type=http
quarkus.langchain4j.mcp.booking.url=http://localhost:8081/mcp/sse
```

Then ask the travel agent about bookings, for example:

```shell
curl -X POST http://localhost:8080/travel \
  -H "Content-Type: text/plain" \
  -d "What are the details of booking 12345?"
```

See the [travel-agency README](../travel-agency/README.md) for full setup (Ollama, PostgreSQL, RAG).

## Mock booking data

The server ships with in-memory sample bookings:

| ID | Customer | Destination | Status |
|----|----------|-------------|--------|
| `12345` | John Doe | Egyptian Treasures | CONFIRMED |
| `67890` | Jane Smith | Amazon Adventure | PENDING |

Cancellation requires the booking id and a last name that matches the end of the customer name on the booking (e.g. `Doe` for John Doe).

## MCP tools

| Tool | Description |
|------|-------------|
| `getBookingDetails` | Look up a booking by numeric id |
| `cancelBooking` | Cancel a booking after last-name verification |

## Project structure

```text
src/main/java/com/mathffreitas/booking/
├── mcp/
│   └── BookingTools.java              # MCP @Tool definitions
├── models/
│   ├── Booking.java
│   └── types/BookingStatus.java
├── repository/
│   ├── BookingRepository.java
│   └── implementation/BookingRepositoryImpl.java
└── service/
    ├── BookingService.java
    └── implementation/BookingServiceImpl.java
```

## Build and run

Package:

```shell
./mvnw package
```

Run the packaged JAR:

```shell
java -jar target/quarkus-app/quarkus-run.jar
```

Native executable:

```shell
./mvnw package -Dnative
```

## Related documentation

- [Quarkus MCP Server](https://docs.quarkiverse.io/quarkus-mcp-server/dev/index.html)
- [Quarkus LangChain4j — MCP client](https://docs.quarkiverse.io/quarkus-langchain4j/dev/mcp.html)
- [travel-agency README](../travel-agency/README.md)

## Author

Matheus Filipe Freitas
