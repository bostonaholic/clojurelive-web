# clojurelive-web

https://clojurelive.com

## Development

### Setting up Datomic

- Download version `0.9.5206` of Datomic Free [here](https://my.datomic.com/downloads/free).

- Install Datomic into `/opt`.

```
$ cd /opt/datomic-free-0.9.5206
```

OR you can install anywhere you like, just be sure to update `$DATOMIC_HOME` in `.env`.

- Copy the template config file for Datomic.

```
$ cp $DATOMIC_HOME/config/samples/free-transactor-template.properties $DATOMIC_HOME/config/free-transactor.properties
```

- Create the database

```
$ lein create-db
```

## Run the App

1. Set up environment variables

```
$ source .env
```

2. Start Datomic

```
$ ./bin/datomic
```

3. Start the app

```
$ lein run
```

## TODO

- Save :user/uuid in session instead of username (in case username is changed)
- TOS and Code of Conduct
- 404/500
- code highlighting
- integrated repl (clj, cljs)
- mark a post 'pending' for explicit language
- Function of the Month

## License

Copyright © 2015 Clojure Live

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
