# State Transition Tables

## Allowed State Table

| State     | pay | prepare | markReady | deliver | cancel |
|-----------|-----|---------|-----------|---------|--------|
|    New    |  ✓  |    ✗    |     ✗     |    ✗    |   ✓    |
| Preparing |  ✗  |    ✗    |     ✓     |    ✗    |   ✓    |
|   Ready   |  ✗  |    ✗    |     ✗     |    ✓    |   ✗    |
| Delivered |  ✗  |    ✗    |     ✗     |    ✗    |   ✗    |
| Cancelled |  ✗  |    ✗    |     ✗     |    ✗    |   ✗    |


## Target State Table

|   State   |  Target   |
|-----------|-----------|
|    New    | Preparing |
| Preparing |   Ready   |
|   Ready   | Delivered |
| Delivered |    N/A    |
| Cancelled |    N/A    |

# Questions

    1. Where did you choose **safety** over **transparency** in your Composite API and why?
In the menu builder composite API I allowed leaf nodes (MenuItem) I implmented the itterator method
despite leaf nodes not being capable of containg further items allowing access to all MenuComponent Classes
but possible leading to errors with empty lists

    2. What new behavior becomes easy with State that was awkward with conditionals?
With the state design pattern it becomes easier to handle state changes as the method calls stay consistent between
states due to a common interface (State.java) with appropriate underlying state specific logic being handled by the
OrderFSM class removing the need for conditionals to check the current state.
