from collections import deque

squares = [1, 4, 9, 16, 25]

# slice operators return a new copy of the list on which the operator was invoked
var = squares[:]
print "squares[0] was not changed: " + str(squares)

# the concat operator can be used to append a list to another list
l1 = [1, 2]
l2 = [3, 4]
print "concat lists: " + str(l1+l2)

# the method append can be used to add items to a list
squares.append(36)
print "squares with appended element: " + str(squares)

# objects of any type can be added to a list!
squares.append("hmmm")
squares.append(u"\u1234")
print "squares with new obj type: " + str(squares)

# len can also be applied to lists
print "length of squares is " + str(len(squares))

# lists can be nested to create a two-dimensional array like type
nest = [["baby bird 1", "baby bird 2"], ["baby bird 3", "baby bird 4"]]
print nest[0][1]

# extend appends the elements of a given list to the end of the list on which the method was invoked
squares.extend([121, 144])
print squares

# insert inserts an item at the specified index, shifting as necessary
squares.insert(0, 0)
print squares

# remove removes an item at the specified index, shifting as necessary
squares.remove(0)
print squares

# index returns the index of the first occurrence of the element given in the parameter,
# and raises an error if the element is not present
squares.append(4)
print squares
print "found 4 at", squares.index(4)

# pop removes AND returns the element at the specified index. If no index is given,
# the last item in the list is removed
squares.pop()
print squares

# count counts the number of occurrences of the parameter in the list
print squares.count(36)

temp = squares[0]
squares[0] = squares[4]
squares[4] = temp
print squares

squares.sort()
print squares

############## DEQUE ################

# deques have fast append and pop operation from both ends
queue = deque(["taco", "siesta", "trabajo", "tetas"])

# remove and get first item in the list
print queue.popleft()








