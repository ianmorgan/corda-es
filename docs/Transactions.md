# Transactions 

Just as in the Corda UTXO model, nothing can be written to the ledger with including it within a transaction. However 
the model is setup differently 

## Inputs

The input is now the current head (the most recent events) for each of Contracts Instances. So for each "input" 
we supply: 
* the contract class name
* the instance id (aggregate id) - for example, the linearId 
* the hash of the event 


## Outputs
The outputs are simply the new events for each of the contracts that will be changed 






