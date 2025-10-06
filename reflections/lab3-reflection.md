Questions

    1. How does the Observer pattern improve decoupling in the Café POS system?
It allows for sperate services within the system from the publisher to hook into it's events without
having to modify the publisher directly. Meaning that a service/observer can have functions that are dependant
on a publisher without requiring the publisher to be dependant on them.

    2. Why is it beneficial that new observers can be added without modifying the Order class?
Because it means that when a new service is required in the future that will use some information from/about
the Order class it will only require the new functionality to be implmented without having to also implment some
new logic in the publisher to facilitate the communication of this information.

    3. Can you think of a real-world system (outside cafés) where Observer is used (e.g., push notifications, GUIs)
Following/Followers feed on socail media
