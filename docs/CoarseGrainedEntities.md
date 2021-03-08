Coarse Grained Entities 
======================

One key motivation for considering an ledger as a chain of events is difficulty in managing more 
complex, coarse grained entities. A typical example in e-Commerce space would be an Order. This holds
a number of fine grained concepts, for example: 
* the list of items ordered 
* customer details 
* delivery details 
* pricing  
* payment details 

Not only is the Order a fairly complex concept, it is dependant on other systems. For example it almost certainly  
needs to know about the status of payments and/or the credit worthiness of the customer, the available stock and 
so on. 

Many system have used the concept of "bounded contexts" and "root aggregates" to help manage this complexity. In 
this approach there are some important rules:  
* within a bounded context we can assume consistency *for the purposes of the services being modelled*, that is we may 
not know *exactly* what is in stock, but the information is good enough to provide an efficient and reliable service.
* we avoid updating the fine grained entities directly, we  route through the "root aggregate". With this approach its 
generally easier to keep the entire aggregate consistent, for example if the price of an item changes. 

Implementations often start to look a set of matching command and events. For example a `ShipOrder` command 
would be sent to Order aggregate. This command would check the current state of the order(does it has all the details
for shipping) and may also reach out to other bounded contexts (has the payment been taken, is that customer still credit 
worthy) and if all if ok the order is updated and a "OrderShipped" event generated as a notification. Some 
implementation even go for a pure a event sourcing solution whereby they simply write a log of the state change events. 


So what are the problems and benefits of modelling something like this on a DLT? 

The first question would be "why, what is the benefit?". And if all we wanted to do was build another Amazon, there is little 
benefit. All the data and rules live largely within a single organisation. But if we were idealistic and wanted to 
truely democratise online shopping, we might argue that if there are a standard set of DLT contracts, then sellers, buyers, 
financial institutions  and logicstics companies could all use then without the need for a central service. And so the 
great disruptor is itself disrupted. 

Having answered why, now the question is how. And the interesting thing is that this exposes many of current 
challenges we have. To pick a few:
* we are dealing with complex state plus complex and evolving rules business rules the contracts. These aren't 
easy to model on current DLTs, which have been designed around clearly bounded, stable concepts - Cash, DvP, Tokenisation, ...
* there are potentially many more actors and this hightens two of the hidden complexities in a DLT, backchain resolution 
and privacy leaks
* in order to write good contracts, its necessary to either bring external data into the ledger in a controlled way,
or accept dependency on external "Oracle like" systems. To give a slightly more real example ... (*think of good one*)
* the desire to minimize or ideally completely avoid transactions between bounded contexts. Payments for example would 
be broken out from orders. This gives the flexibility to manage different payment method (Credit, Crytpo, On Ledger 
Wallet...). But with the added complexity of some form of "coordinator" between the two   

       

   
