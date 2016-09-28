# Run eventstore

docker run --name eventstore-node -it -p 2113:2113 -p 1113:1113 eventstore/eventstore

curl -i -d @contratCommand.json http://127.0.0.1:4567/commands/contrats/createContrat
