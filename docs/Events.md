# Events 

## Introduction 

All ledger state is stored as a stream of events. To ensure the integrity of the ledger,
there are a number of essential characteristic for an event

* **It is linked to a contact** - similar to way a Corda state (using the UTXO model) 
has a [@BelongsToContract](https://api.corda.net/api/corda-os/4.6/html/api/kotlin/corda/net.corda.core.contracts/-belongs-to-contract/index.html)
annotation 
* **It links back to it predecessor** - the chain is verified by linking back to the immediate predecessor 
and including its hash in the event's own hash 
* **It is linked to a transaction**- it is only possible to add changes to the ledger by including them
within a transaction


A simple event is a Java class

- payload - IOU (alice,bob,10)
- hash - #42356
- version - optional ?

? Is the name the event just the java class name 

A "ledger event"

- contractInstanceId - eg a uuid 
- event
    - name
    - payload
    - hash
    - version 
- transactionHash - the link to transaction that wrote the change 
- partipants ?? 







