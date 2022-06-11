#!/usr/bin/python3.10
from scapy.all import *

flags = {
    0x01:"F",
    0x02:"S",
    0x04:"R",
    0x08:"P",
    0x10:"A",
    0x20:"U",
    0x40:"E",
    0x80:"C"
}

with open("flag.pdf", "rb") as f:
    data = f.read()

for byte in data:

    data_to_flags = ""
    for i in range(8):
        if byte & 2**i != 0:
            data_to_flags += flags[2**i]


    p = IP(dst="172.17.0.2") / TCP(dport=1337, flags=data_to_flags)
    send(p)
