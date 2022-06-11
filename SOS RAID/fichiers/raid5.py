from secrets import token_bytes


with open("SOS RAID.zip", "rb") as data:
    with open("disk0.img", "wb") as disk0:
        with open("disk1.img", "wb") as disk1:
            pos = 2
            while True:
                byte1 = data.read(1)
                if not byte1:
                    break
                byte2 = data.read(1)
                if not byte2:
                    byte2 = b"\x00"
                parityByte = (int.from_bytes(byte1, "little") ^ int.from_bytes(byte2, "little")).to_bytes(1, "little")
                if pos == 0:
                    disk0.write(parityByte)
                    disk1.write(byte1)
                    pos = 2
                elif pos == 1:
                    disk0.write(byte1)
                    disk1.write(parityByte)
                    pos -= 1
                else:
                    disk0.write(byte1)
                    disk1.write(byte2)
                    pos -=1
        
        