from sys import argv
from os.path import exists

script_name = argv[0]

filename = "/home/kikuchio/Desktop/sandbox/zabadab"

# open returns a file object through which all the read/write operations can be performed
the_file = open(filename)

# read reads the entire file
print "here are the contents of the file\n", the_file.read()

# readline reads a single line from the file
the_file = open(filename)
print "here is a single line from the file\n", the_file.readline()
the_file.close()
# truncate deletes the contents of the file
# the_file.truncate()

# opening files should be done by using the correct modifier:
# w          : EMPTIES the file and opens it for writing
# r (default): opens the file for reading only
# a          : opens the file for appending
# w+, r+, a+ : open the file for updating (read and write). (w+ also empties the file)
the_file = open(filename, 'a+')
the_file.write("stuff A\n")
the_file.write("stuff B")

# exists check if the file exists in the current directory
print "checking if file \"nameless_hound\" exists: ", exists("nameless_hound")

print "here are the contents of the file\n", the_file.read()
the_file.close()


def cpy(src_file_path, dest_file_path):
    cpy_file = open(src_file_path)
    other_file = open(dest_file_path, 'w')
    other_file.write(cpy_file.read())


def rewind(f):
    f.seek(0)


rewindable_file = open("/home/kikuchio/Desktop/sandbox/zabadab")
print "first line", rewindable_file.readline()
print "second line", rewindable_file.readline()
rewind(rewindable_file)
print "line after rewind", rewindable_file.readline()





