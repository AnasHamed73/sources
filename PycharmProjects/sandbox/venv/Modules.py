###############################################################################
# a file that contains functions and offers services is called a module
# import statements are usually placed at the very first lines of the
# module. This is not required, but encouraged, as it makes the code more
# readable


# imported functions can be given aliases through which they can be referenced
# throughout the module
from os.path import exists as existsalias

# the * can be used in import statements to import all the module's names
# however, this is discouraged if the module is to be used as a script because
# it affects the script's readability
# if the module is to be used interactively, however, then it is fine because
# readability is not an issue
import sys


# utilizing the alias
print (existsalias("zabadab"))

# if the module is run as the main module, then a builtin variable
# called __name__ is set to __main__ this can be useful if the
# the script wants to find out if it has been passed command-line args
if __name__ == "__main__":
    the_args = sys.argv
    for a in the_args:
        print ("arg", a)

# the sys.path variable contains the list of directories in which the
# interpreter looks to locate modules to import
for i, p in enumerate(sys.path):
    print (i, p)

# the sys.path variable could be changed
old_path = set(sys.path)
sys.path.append("/new/dir/for/u")
print ("difference between the old path and the new one: ",  \
    set(sys.path) - old_path)

# using the dir(module_name) call, you get a list of all the
# names that were used in a certain module
# if used without an argument, it returns a list of the
# currently defined names
print (dir(  ))
