import wave
import matplotlib.pyplot as plt
from matplotlib.widgets import Slider
import numpy as np

file = 'message.wav'

with wave.open(file, 'r') as wav_file:
    #Extract Raw Audio from Wav File
    signal = wav_file.readframes(-1)
    signal = np.frombuffer(signal, np.int16)

    #Split the data into channels
    num_channels = wav_file.getnchannels()
    channels = [signal[channel::num_channels] for channel in range(num_channels)]

    nb_sec = wav_file.getnframes()//wav_file.getframerate()


letters_r = np.array_split(channels[1], nb_sec)
letters_l = np.array_split(channels[0], nb_sec)

fig = plt.figure()
ax = fig.add_subplot(111)
ax.set(xlim=(min(channels[0]), max(channels[0])), ylim=(min(channels[1]), max(channels[1])))
plt.subplots_adjust(left=0.25, bottom=0.25)

frame = 0
ln, = ax.plot(letters_l[frame], letters_r[frame], '.')

axframe = plt.axes([0.25, 0.1, 0.65, 0.03])
sframe = Slider(axframe, 'Frame', 0, nb_sec - 1, valinit = 0, valfmt='%d')


def update(val):
    frame = int(round(np.floor(sframe.val)))
    ln.set_xdata(letters_l[frame])
    ln.set_ydata(letters_r[frame])
    plt.draw()

sframe.on_changed(update)
plt.show()