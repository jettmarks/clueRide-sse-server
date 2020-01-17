# Server-Sent Events for Clueride

This is a standalone Grizzly server that handles
HTML5's Server-Sent Events (SSE). 

The combination of filters in the main application 
appeared to be tangled up with providing an async 
channel, so to remove those complicating factors, the 
desired functionality was moved into this separate project.

## Components
These are the components that encompass the SSE
server.

- Main class that defines the endpoints' base URL and 
instantiates the Grizzly server.
- Shutdown response code that cleans up.
- A ServerSentEventChannel provides the link between
a "channel" (for a given Outing) and the EventOutput
instance used to provide the ChunkedOutput resource.
- GameStateBroadcastJersey2 which defines the
Jersey REST endpoints for subscribing to a channel.
- API Endpoint that clients (usually the back-end server)
can use to broadcast each of the event types, one endpoint
per event type.
- A KeepAlive thread for each channel which makes sure 
each connection receives periodic "comment" records that
let the channel clients know the connection is still active.
- CORSFilter provides the headers needed to 
negotiate CORS.

### Future
- Status Endpoint for reporting what outings each channel 
has and what clients are currently connected. This could 
serve as an overall connection state for say the Team Page.

## To Build
This project was established using a Maven archetype
suggested by Jersey, but the target hasn't yet been
configured to complete the build and deploy. I'm using
the IDE to create an artifact that includes all the
various JAR files required to run, and then copy the
resultant jar file onto the same server where the
main Tomcat container is running.

### Steps
1. From the IDE: Build -> Build Artifacts ...
2. JAR file is placed under ./out/artifacts/sse_server_jar/
3. Hand transfer the file to the location on the host where it runs.

## Gotchas / Couplings
- The port is hardcoded within the Main class.
- The Angular client expects exactly the text 
"message" although the example suggested 
"message-to-client". This mismatch prevents 
recognition of the inbound data, but it can keep
the connection alive.
