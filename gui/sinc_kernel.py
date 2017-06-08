

import math, sys

Pi = math.pi


class impulse_response:
    Fs = 48000
    M = 50

    def __init__(self,_bass_high,_treble_low,bass_multi,middle_multi,treble_multi):

        self.bass_high = float(_bass_high)
        self.treble_low = float(_treble_low)
        self.bass = float(bass_multi)
        self.middle = float(middle_multi)
        self.treble = float(treble_multi)

        self.bfc = (float(self.bass_high) / self.Fs)
        self.tfc = float(self.treble_low) / self.Fs

    def sinc_kernel(self,fc,LOW):
        kernel = [0] * self.M

        for i in range(self.M):
            if i == self.M/2:
                kernel[i] = 2*Pi*fc
            else:
                # Magical formula from 16-4 in the Digital Signal Processing book.
                kernel[i] = (math.sin(2*Pi*fc*(i-(self.M/2))) / (i - self.M/2)) \
                            * (0.42-(math.cos(2*Pi*i/self.M) / 2) + (0.08*math.cos(4*Pi*i/self.M)))

        if sum(kernel):
            kernel = [x / sum(kernel) for x in kernel]   # Dividerar F(x) till 1.

        # Invertering
        if not LOW:
            kernel = [x * (-1) for x in kernel]
            kernel[int(self.M/2)] += 1

        return kernel

    def low_pass_kernel(self):
        # multiply by bass factor
        return [self.bass * x for x in self.sinc_kernel(self.bfc,True)]

    def band_pass_kernel(self):
        # Subtract 10k filter from 2k filter
        low_pass = self.sinc_kernel(self.bfc,True)
        high_pass_inverted = self.sinc_kernel(self.tfc,True)  # Inverterad ty LOW = True

        band_pass_kernel = []

        for i in range(low_pass.__len__()):
            band_pass_kernel.append(high_pass_inverted[i] - low_pass[i])

        # Multiply by middle pass factor
        return [self.middle * x for x in band_pass_kernel]

    def high_pass_kernel(self):
        # Multiply by high pass factor
        return [self.treble * x for x in self.sinc_kernel(self.tfc,False)]

    def total_filter_kernel(self):

        total_filter_kernel = []
        for i in range(self.low_pass_kernel().__len__()):
            total_filter_kernel.append(self.low_pass_kernel()[i] + self.band_pass_kernel()[i] + self.high_pass_kernel()[i])

        return total_filter_kernel



# low_pass_kernel() ger liknande plot som matlab.

# band_pass_kernel() ger liknande plot som matlab.

# high_pass_kernel() ger liknande plot som matlab.

# total_filter_kernel() ger liknande plot som i matlab.

