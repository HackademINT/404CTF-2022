with open("test.zip", "wb") as file:
    with open("disk2.img", "wb") as disk2:
        with open("disk0.img", "rb") as disk0:
            with open("disk1.img", "rb") as disk1:
                pos = 2
                while True:
                    byte0 = disk0.read(1)
                    byte1 = disk1.read(1)
                    if not byte0 or not byte1:
                        break
                    byte2 = (int.from_bytes(byte0, "little") ^ int.from_bytes(byte1, "little")).to_bytes(1, "little")
                    disk2.write(byte2)
                    if pos == 0:
                            towrite = byte1 + byte2
                            pos = 2
                    elif pos == 1:
                            towrite = byte0 + byte2
                            pos -= 1
                    else:
                            towrite = byte0 + byte1
                            pos -=1
                    file.write(towrite)