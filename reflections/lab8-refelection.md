# Lab 8 Reflection

## Questions

Finally, you reflect on layering versus partitioning. The purpose is to think ahead: why do
we keep everything in one layered monolith for now, and what seams (like Payments or
Notifications) might become separate services later. You capture this in a short README
note. Write a 150–200 word note in your README answering:

    1. Why did you choose a Layered Monolith (for now) rather than partitioning into multiple services?

    2. Which seams are natural candidates for future partitioning (e.g., Payments, Notifications)?

    3. What are the connectors/protocols you would define if splitting (events, REST APIs)? 
    Keep the system simple today, but be deliberate about future evolution. 

## Answer

A layered monolith is more suitable in the current projects state as it’s relativly compact meaning that it is still possible to reason about the system as a whole and would be easily maintainable by a single dev or even extensible by a single dev or small team. By nature of being a POS (Point Of Sale) system it will not be required to be deployed in a highly scalable/available enviornment meaning that modularizing the system for scalability would not be of any benefit. As there is no requirment on this system that calls for it to be modularized a layerd monolith provides a system that is simpler to deploy, debug and test and therfore develop for.
If the requirements for this system were to change in future in such a manner that would require it to be split up into different services, I beleive the following would be a good approach:

|    Service    |                   Description                     |                   Packages                         |
|---------------|---------------------------------------------------|----------------------------------------------------|
|  Domain/Core  | Common and core buisness logic                    | common, domain                                     |
|     Sales     | Critical services of high importance/consequence	| discount, payment, tax                             |
|      UI       | View and Controller grouping	                    | menu, printing, command, checkout, application, ui |
|    Product    | Services related to product creation/monitoring	| catalog, factory, decorator, state                 |
|    Events     | For tracking global events                        | observers, application.events                      |
| Infastructure | For underlying infastructure                      | infastructure                                      |

I would use the existing event queues within services and HTTP REST APIs for the currnet function calls between services this could be expanded on in future by using async calls where required and more sophisticated queueing systems in future such as rabbitMQ.
