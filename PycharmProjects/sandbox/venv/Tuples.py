
string = 'hello'
print "plain old string whose length is", len(string)
print string

# this is a tuple containing a single element.
# the comma at the end is what indicates that this is a tuple,
# and not a regular string
string_tup = 'hello',
print "but this is a tuple whose length is", len(string_tup)
print string_tup

# tuples differ from lists in that the latter is immutable
# and usually contains a heterogeneous mix of items
# tuples can be distinguished from lists in the output
# from the parentheses enclosing them, as opposed to the
# square brackets that enclose lists
tuple_ex = "Elephant", 73, lambda x: x, ["trabajo", "guillermo"]
print "this is a tuple:", tuple_ex
print "this is a list:", [1, 2, 3, 4, 5]
