# OOP_1

Ex1 of OOP course.

## Observer Design Pattern of an UndoableStringBuilder

### GroupAdmin (Observable)

We implemented the Sender interface in our GroupAdmin class by using an ArrayList to keep track of all registered
Members, and an UndoableStringBuilder work on.

When making any change to the UndoableStringBuilder, the GroupAdmin updates ALL Members of the change.

### ConcreteMember (Observer)

We implemented the Member interface in our ConcreteMember class by using an UndoableStringBuilder to keep track of, and
when notified by GroupAdmin, updates it.

## Authors

Roey Feingold https://github.com/RF555

Gal Hilell https://github.com/GalHillel
