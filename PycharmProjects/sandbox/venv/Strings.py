#!/usr/bin/python2.7
# A comment


############# STRINGS ##################
print r"this is a raw string. any special chars like \n\r\e will not be interpreted"
print """
\
(new lines can be escaped with a backslash)
This is a multi-line
string.
"""

print '''Another form
of multi-line strings, 
this time with single quotes
'''

rep = 3 * 'Pep'
print "strings can be multiplied in number by using *,\
 and concatenated with +! Viz, " + rep

mul_strs = 'This is ' 'my only line'
print "strings can be defined with multiple juxtaposed literals,\
 which are automatically concatenated, viz, " + mul_strs

print """strings can be treated as character arrays (there is no character type in python),
       viz, """ + mul_strs[0] + """. Indices are allowed to be negative. Negative indices 
       start with the end of the array and go back as the numbers get smaller, viz, """ + mul_strs[-1]

print """slicing works by specifying start and/or end indices, viz, """ + mul_strs[0:4] +  """
if only the begin index is specified, the end is assumed to be the end of the string, viz, """\
      + mul_strs[4:] + """
if only the end index is specified, the start is assumed to be the first character of the string, viz, """\
    + mul_strs[:4] + """
if no indices are specified, the entire thing is returned, viz, """ + mul_strs[:]

print "Strings are immutable in python, so trying to assign a specific char in a string is an error"
# mul_strs[0] = 'Q' # <-- error
print "nonsensical slices are handled gracefully: " + mul_strs[0:73]
print len(mul_strs)

split_str = "The split function splits the string according to the given separator".split(' ')
print split_str
print ' '.join(['The', 'join', 'function', 'does', 'the', 'opposite', 'of', 'split'])


