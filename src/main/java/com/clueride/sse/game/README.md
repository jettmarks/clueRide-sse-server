The endpoint defined within this package listens for connection
requests on the `game-state-broadcast` channel. 

This channel produces `SseFeature.SERVER_SENT_EVENTS` to 
establish an open session with its subscribers.

* Simple `GET` passing the outing ID subscribes to this channel.
* A `POST` is used to send a string message (usually JSON) to
listeners for a given outing ID.
* A `POST` to the `close` path with the outing ID notifies
subscribers that the channel is closing.
