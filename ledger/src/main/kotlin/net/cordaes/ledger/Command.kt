package net.cordaes.ledger

// simply a marker interface
interface Command {}

// convenience if we want to combine smaller commands into a bigger command
abstract class MultiCommand (val commands : List<Command>)

// Provide a dummy command for unit tests
class NoopCommand : Command
