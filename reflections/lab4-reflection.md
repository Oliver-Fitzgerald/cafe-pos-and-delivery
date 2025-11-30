Questions

1. Explain why you chose your pricing interface, identify one place where you preserved the Open/Closed Principle, and describe what would be required to add a new add-on next week.

I chose the ProductDecorator as the price interface for decorator as it allowed for the price of products
to be added on one anoter recursivly without tampering with the already implmented basePrice. This also
preserved the open/closed principle by not modifying the basePrice method of Product.

2. If you added any new tokens to the factory during experimentation, explain how your change preserved the Open/Closed Principle. 

It would not require any change to how any other product is implmented just the creation of a new
class for your token and a new case in the relevant switch statment

3. write three to four sentences explaining which construction approach you would expose to application developers and why?

For a developer I would choose the Decorator approach as it is more expressive and leaves less room
for syntax errors by misspelling a token in the recipe.
