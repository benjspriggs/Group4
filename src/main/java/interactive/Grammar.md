# Grammar for Interactive Mode

So the grammar for the Interactive mode is rather simple.
The intention is to make rather complex inserts and queries easy to
see and understand.

It's described below in [Extended Backus-Naur form](http://www.garshol.priv.no/download/text/bnf.html#id2.4.):
````
<S> ::= <stop>
| <help> <request>?
| <request> <object>+
| <request> <superobject>
| 'SQL' <sSQL>

<sSQL> ::= (Grammar for a valid SQL statement)

<stop> ::= 'quit'
| 'bye'
| 'EOF'

<help> ::= 'help'
| '?'

<request> ::= 'create'
| 'show'
| 'update'
| 'delete'
| 'write'

<object> ::= <type> <payload>

<superobject> ::= <types> <payload>
| 'all' <types>

<type> ::= 'user'
| 'member' 
| 'provider' 
| 'service' 

<types> ::= users
| 'members' 
| 'providers' 
| 'services' 

<payload> ::= <Sj>

<Sj> ::= (Grammar for a valid JSON object)
```