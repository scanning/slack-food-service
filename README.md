# slack-food-service

A basic prototype for managing group food orders.  Users can submit orders via Slack and the person responsible for placing the order can collate them. Written in Java using Dropwizard.

1. Get your Dropwizard server running: `java -jar slack-food-service-0.0.1-SNAPSHOT.jar server`.
2. Ensure it is internet accessible via an https url (I used ngrok for testing).
3. Set up a Slack slash command (e.g. /food) that will make requests to the server at https://your-server-host/food
4. Manage meals hassle free via Slack.
 
A list of the basic operations/commands:

* `/food menu list` - list the available menus
* `/food menu set 1` - set the menu for the day
* `/food menu show` - show the current menu
* `/food order <item-id>` - order an item
* `/food order show` - show your current order
* `/food orders list` - list each of the orders by user
* `/food orders show` - show the orders (grouped by order id)

You can add your own restaurants by adding to `menus.json`.

Bon appetit!

