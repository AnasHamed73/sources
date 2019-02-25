
# sets can be instantiated with the set() call
# the set() method takes an iterable
the_set = set(["Special", "Snowflake", "With", "Wings"])

# given a string argument to the set() method, the string
# gets broken down to its constituent characters, and only
# unique characters are retained
char_setz = set('Zabadab')
char_setl = set('hibilib')
print char_setz
print char_setl

print the_set

# although membership testing is not unique to sets,
# it is particularly fast with sets
print "do we have that special snowflake?", "Snowflake" in the_set

# operations can be done on sets to extract elements that are
# unique to one set compared to another, or those common
# between two different sets
# a - b: retains the members in a, but not in b
print char_setz - char_setl

# a | b: elements in a or b
print char_setz | char_setl

# a & b: elements both in a and b
print char_setz & char_setl

# a ^ b: elements in a or b, but not in both
print char_setz ^ char_setl

print[x for x in 'zabadab' if x not in 'hibilib']


