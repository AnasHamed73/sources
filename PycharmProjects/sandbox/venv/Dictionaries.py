
# here is how to initialize a dictionary
dictionary = {'Key': 'Value', "Shoelace": "Shoe"}

# another way to initialize is to use the dict() call
# using either a list of tuples, or keyword arguments
other_dict = dict([('Key', 1), ('Other key', 2)])
other_dict_kw = dict(Key=1, Other_key=2)
print other_dict
print other_dict_kw

# here is how to access the values from the keys.
print dictionary['Shoelace']
# if the key does not map to any value, a KeyError is raised
# (viz., the statement below)
# print dictionary['Bladamus']

# the get method return a value if it is mapped by the given,
# None if the key does not map to a value
# the second argument of get is the default value
# to return if the entry is not there
print "using get to retrieve the value for a non-existent key:",\
    dictionary.get("habadus", "HAHAHAH!")

# putting values to the dictionary
dictionary['Yuan'] = 'Gian'

print "dictionary after adding a new value", dictionary

# you are not limited to a single type of key-value mapping. you can have this:
dictionary[73] = "Cool number"

print "dictionary after adding an integer key", dictionary

# deleting records can be accomplished with the del operator
del dictionary["Yuan"]

print "dictionary after deleting a record", dictionary

# iterating over the entries
for key, value in dictionary.items():
    print "%s is mapped to %s" % (key, value)

# dictionaries can be created from comprehensions
# this comprehension creates a map of numbers and their
# squares in the 1, 2, 3 tuple
comp_dict = {x: x**2 for x in (1, 2, 3)}
print comp_dict



