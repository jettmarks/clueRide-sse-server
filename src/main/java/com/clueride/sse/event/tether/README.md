TODO: This appears to be out-of-date

The endpoint defined within this package listens for connection
requests on the `heartbeat` channel. 

This channel produces `SseFeature.SERVER_SENT_EVENTS` to 
establish an open session with its subscribers.

* Simple `GET` without an Outing ID subscribes to this channel.
Providing an Outing ID will include tethered position data for
that outing in the heartbeat messages. Heartbeats are provided
on an interval if no other message has been sent on the channel.
* A `POST` to the `tether` path is used to send coordinates to
listeners for a given outing ID.
* A `POST` to the `close` path with the outing ID notifies
subscribers that the channel is closing.
