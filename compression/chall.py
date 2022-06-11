import tarfile
import random
import os
import gc

FLAG = "404CTF{C0mPr3Ssi0n_m4X1m4L3_m41S_p4S_3ff1C4c3}"

filename = "flag.txt"
with open(filename, "w") as f:
    f.write(FLAG)

mode = ""
for i in range(1, 2501):
    r = random.randrange(4)
    match r:
        case 0:
            mode = "x:gz"
        case 1:
            mode = "x:bz2"
        case 2:
            mode = "x:xz"
        case 3:
            mode = "x"
    old = filename
    filename = f"flag{i}.tar{mode.replace(':', '.')[1:]}"
    with tarfile.open(filename, mode=mode) as f:
        f.add(old)
    os.remove(old)
    print(f"{i}/2500")
    if i % 100 == 0:
        gc.collect()
