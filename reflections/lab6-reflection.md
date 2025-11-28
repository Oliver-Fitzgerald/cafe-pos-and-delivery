Questions

    1. Where does Command decouple UI from business logic in your codebase? 
In the posRemote class.

    2. Why is adapting the legacy printer better than changing your domain or vendor class?
It prevents potential errors or unintentional changes in behaviour between the original legacy printer
and it's new implmentation. The use of adapater pattern also makes it easier to integrate further third
party or legacy printers in the future if it is required.
