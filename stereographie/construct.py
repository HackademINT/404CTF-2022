import wave
from pathlib import Path

# charset = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_{}' you can use any of these
flag = '404CTF{AV3Z_V0U5_U71L153_UN_VR41_05C1LL05C0P3}'

outfile = 'message.wav'

data = []
for c in flag:
    p = Path('letters_b16.wav', f'{c}.wav')
    print(p)
    w = wave.open(str(p), 'rb')
    data.append([w.getparams(), w.readframes(w.getnframes())])
    w.close()


output = wave.open(outfile, 'wb')
output.setparams(data[0][0])
for params,frames in data:
    output.writeframes(frames)
output.close()

print(f'File created : {Path(".", outfile).resolve()}')