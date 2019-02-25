class TheClass(object):

    def __init__(self):
        print "init being called"
        self.tang = "Tang here"

    def fun(self, sth):
        print "You gave", sth, "... Quite a something it is"


class Sub(int):
    def __init__(self):
        print "sub init being called"


    def sub_met(self):
        print "I am a sub"


sub = Sub()
sub.sub_met()

# sub.__init__()

