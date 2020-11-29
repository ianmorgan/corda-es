# corda-with-actors

So what is this repo about? I work for R3 and while we have great DLT with Corda, its not always easy model 
business processes as smart contracts (CorDapps). This little experiment asks a fundemental question, 

**What happens if we drop the UTXO model?** 

There is a lengthy discussion on the [pros and cons](https://www.corda.net/blog/rationale-for-and-tradeoffs-in-adopting-a-utxo-style-model]) 
of this decision here. This is at the heart of the Corda programming model when implementing contracts
through not necessary at the heart of the ecoystem - a lot of functionality in Corda is 
there to "support" the contract and is not actually tightly coupled to the contract.  So in theory at least, if 
an alternative model has clear benefits over UTXO for some usecases, we should be able support both models on a common platform. 

The proposal is to drop UTXO in favour of a simple event log and derive all state from a reduction of the 
events associated with the contract. The principles borrow heavily from thinking behind the [Actor](https://en.wikipedia.org/wiki/Actor_model), 
[event sourcing]([https://martinfowler.com/eaaDev/EventSourcing.html) and [CQRS](https://martinfowler.com/bliki/CQRS.html) 
patterns. 

To explain a little further, take the [IOU example](https://docs.corda.net/docs/corda-os/4.6/hello-world-introduction.html). 
We think about the problem a little differently - there is now no IOUState as such. Instead the IOUContract has concepts. 
* command handlers to validate the request to update the contract, and if accepted generate signed event(s) with the 
update
* reducers that read the history of all events associated with the contract to build a view    


The diagram below hopefully makes it a little clear. I've skipped the distributed part for simplicity (moving data between nodes,
signing and agreeing concensus) to leave just a single node, and I've used the [event storming](https://github.com/wwerner/event-storming-cheatsheet) 
conventions for colouring. 


![IOU as Events](docs/images/iou-as-events.png)


 
   







 