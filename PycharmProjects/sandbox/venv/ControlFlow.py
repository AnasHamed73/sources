

a, b = 0, 1
i = 0
while (i < 10) & (b < 1000):
    c = a + b
    print c,
    a = b
    b = c
    i = i + 1

print
if True & True:
    if 0:
        print 'No'
    elif 1:
        if 0:
            print "Nope"
        else:
            print "Yes"

hikken = ['Mat', 'the', 'hikken']
for i in hikken:
    print i, len(i)

for i in range(len(hikken)):
    print "iterating by index:", i, hikken[i],

# range outputs an arithmetic progression that   starts from 0 and ends to the specified number
print "0 to 10 range: ", range(10)
print "5 to 10 range: ", range(5, 10)
print "0 to 10 range with custom step: ", range(0, 10, 3)
print "-10 to -100 range with custom step: ", range(-10, -100, -40)

# an else clause can be used with a for loop. If the break condition of the for the loop was not met,
# the else block executes
for i in range(1, 10):
    for j in range(2, i):
        if (i % j) == 0:
            print i, " equals ", j, "*", i / j
            break
    # this else belongs to the for loop!!
    else:
        print str(i) + " is a prime number"

############### FUNCTIONS #################


# a pass is a no op
pass


# this is the function syntax. Docstrings are optional (equivalent to javadocs)
def fib(n):
    """returns the nth fibonacci term"""
    # ^ that is a docstring
    if n == 1:
        return 0
    if n == 2:
        return 1
    return fib(n - 1) + fib(n - 2)


# functions can be assigned aliases
fib2 = fib
print "invoking the alias of fib: ", fib2(10)


def useless_fun():
    pass


# functions that don't explicitly return a value return None
fun = useless_fun()
print fun

# the method raw_input can be used to prompt for a line of data
# user_input = raw_input("i am prompting you for some data: ")
# print "you gave me", user_input

opt_str = 'you dont need to provide this'


def optional_args_fun(mandatory, optional=opt_str):
    print "you gave", mandatory, "as mandatory and", optional, "as optional"


# default method arguments are only evaluated once. So this reassignment
# has no effect on the default value assigned to the method call below
opt_str = "Something else entirely"
optional_args_fun(a)

optional_args_fun(a, optional="use this instead")


def varargs_fun(mandatory, *varargs, **dikken):
    print "mandatory stuff:", mandatory
    print "now here are your varargs: "
    for o in varargs:
        print o,
    print "\nand now for your dictionary: "
    keys = sorted(dikken.keys())
    for key in keys:
        print key, ":", dikken[key]


varargs_fun("big stuff", "Mr.", "Bellport", "Your", "Legs", "So", "Swollen",
            its="Monty Python's flying circus",
            and_now="for something completely different")

# the * operator can be used on a list to enumerate its values and pass them as parameters to functions
limits = [1, 5]
print range(*limits)  # equal to range(1, 5)


# lambdas
def make_adder(n):
    return lambda x, y: x + y + " postfix; also original val is " + n


adder_lam = make_adder("73")
print adder_lam("Mr. husband", "asd")

# the __doc__ field can be used to print docstrings
print adder_lam.__doc__

