# SpringBoot-R2DBC-PostGreSQL
POC for reactive programming using reactive jdbc driver for PostgreSQL

This project exposes a simple REST API with all the CRUD operations on product entity.

at startup it will load 3 products in DB.

in order to search one product you can use the URL http://localhost:8080/products/1 to see details of one product.

This application is build entirely on reactive stack using Spring Webflx and runs on Netty server.


