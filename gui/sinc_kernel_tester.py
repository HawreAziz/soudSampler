#import matplotlib.pyplot as plt

import math

import sinc_kernel as sk


def tester():
    print("tester")

    x = []
    M = 100

    #sinusvag
    for i in range(M):
        x.append(math.sin(2*math.pi * (i / M)))

    # Exempel pa anvandning.
    impulse_gen = sk.impulse_response(2000,10000,0,0,0)
    #x = impulse_gen.low_pass_kernel()
    x = impulse_gen.total_filter_kernel()
    #print(type(x[0]))
    #plt.plot(x)
    #plt.grid(True)
    #plt.show()

tester()