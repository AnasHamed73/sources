
# iterables can be traversed in reverse order using the
# reversed() call
for i in reversed(xrange(1, 10)):
    print i,

# using enumerate, you can retrieve the index of the current
# element in the iteration, as well as the element itself
for i, v in enumerate(['val1', 'val2', 'val3']):
    print i, v

# to enumerate dictionary entries as opposed to just keys
# or just values, use the iteritems() method
diccus = dict(key1=1, key2=2, key3=3)
for k, v in diccus.iteritems():
    print k, v

# using zip, you can iterate over multiple iterables at once
questions = ['does it rain?', 'who is the walrus?', 'how high the moon?',
             'how about this question?']
answers = ['sometimes', 'Paul', 'pretty high']
for q, a in zip(questions, sorted(diccus.values())):
    print q, a


########## FUNCTIONAL STUFF ##########


def even_predicate(i):
    return i % 2 == 0


# filter can be given a predicate by which to filter items in an iterable
# with this, we obtain the list of even numbers from 1 to 10
print filter(even_predicate, range(1, 11))


def double_function(x):
    return x * 2


# with map you can apply a certain function to each element in an iterable
# with this, we obtain a list in which
# each item of the original list is doubled
print map(double_function, range(1, 11))


# reduce applies a bi-function, which produces a single value from two values
# with this, we are adding every element in the list
print reduce(lambda i, j: i + j, range(1, 11))


########## COMPREHENSIONS ##########

# this notation is called a comprehension. It is another way of creating lists
# here, we are initializing a list by squaring each element from 1 to 10
# this statement is identical to the one directly underneath it
print[x**2 for x in range(1, 11)]
print map(lambda x: x**2, range(1, 11))

# comprehensions can be nested like so
print[(x, y) for x in [1, 2, 3] for y in [4, 1, 2] if x != y]
# equivalent to:
combinations = []
for x in [1, 2, 3]:
    for y in [4, 1, 2]:
        if x != y:
            combinations.append((x, y))

print combinations


two_dim = [
    [1, 2, 3, 4],
    [5, 6, 7, 8],
    [9, 10, 11, 12]
]

# this comp transposes the matrix two_dim
# this is an example of a comprehension that has another
# comprehension as its expression
print[[row[i] for row in two_dim] for i in range(len(two_dim[0]))]
# equivalent to:
trans = []
for i in range(len(two_dim[0])):
    columns = []
    for j in range(len(two_dim)):
        columns.append(two_dim[j][i])
    trans.append(columns)

print trans


