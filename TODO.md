note on things todo, questions , points that are unclear or need more thought... 


# Consensus 

In many cases this will be easier to check for double spends - we simply  check to see if no new events 
have been added

"Issuance" events are possibly slightly different. Ideally they  "lock" the register to  confirm that
other attempts are made to issue  with the same id - 



# Combing contracts in a transaction

It is likely that in most cases a contract will manage a single concept, and that 
2 or more contracts must colloborate 

# Fungible States

These are still going to be a pain - long chains dont go away 


# Upgrading contracts 

todo - think through the scenarios 

## new event 

What are the rules here ?

## deprecating an event 

##


# What happens if the participants list change ?

The "Story Reading" contract 

Mary had a little lamb
Its fleece was white as snow 
And everywhere that Mary went 
The Lamb was sure to go 


Mary...           next line read event
Alice
Bob

Its....           next line read event
Alice
Bob

Change Reader     current line is event 
Alice
Charlie
Bob 

And....
Charlie
Bob

# How do we check for double spends 

## Linear states 
- this is easy, the Notary simply checks there were no events since ths last event 
(implies we always need to know the last event so we can chain)

## Fungible state 

Option A - simply chain all events 

Bank issues $10 to Alice           

Alice transfers $3 to Bob (alice 7, bob 3)


Alice 
issue event
transfer event 


Bob
request backchain (issue event)
transfer event 


# How do we stop other stealing the state ?

By implication all events must be linked to a contract (instance?) and 
therefore the ledger (notary) must protect against events from another party 
- probably want to make change the participants a set of explicit events, 

Do we want to make certain operation "validatable by the notary" - we could model 
this "assertion" events - we trade of some speed (and privacy) for more guarentese ? 
 - a 

InitialPartipants
ParticpantsAdded
ParticipantsRemoved








