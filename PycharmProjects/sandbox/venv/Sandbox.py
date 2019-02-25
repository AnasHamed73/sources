inp = [11, 18, 17, 15, 9, 5, 12, 19, 22, 25]
out = [6, 8, 10, 4, 9, 6, 3, 5, 2, 10]


def cost(w0, w1, w2):
    err = 0
    for i in range(0, len(inp)):
        pred = w0 + (w1*inp[i]) + (w2*(inp[i]**2))
        err += abs(pred - out[i])**2
    return err


print "error for w1 is: ", cost(0.3, 0.5, 0)
print "error for w2 is: ", cost(5.75, 0.04, 0)
print "error for w3 is: ", cost(3.2, 0.2, 0)
print "error for w4 is: ", cost(8.75, -0.5, 0.02)
