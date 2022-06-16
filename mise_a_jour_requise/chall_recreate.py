#!/usr/bin/python3.10
import random as rd

##
def a(c, r=True):
    n = ord(c)
    if r: rd.seed(n)
    match n:
        case 0:
            return dict.fromkeys(range(10), 0)
        case _:
            return (d:=a(chr(n - 1), False)) | {(m:=rd.randint(0, 9)): d[m] + rd.randint(0,2)}

##
def b(p, n):
    match list(p):
        case []:
            return []
        case [f, *rest]:
            l = list(a(f).values()) + b(''.join(rest), n*2)
            rd.seed(n)
            rd.shuffle(l)
            return l

##
def c(p, n=0):
    match p:
        case []:
            return n!=0
        case [f, *rest]:
            rd.seed(s[n])
            return rd.randint(0,30) == f and c(rest, n + 1)

##
def encode(s):
    e = b(s, 1)
    for c in e:
        i = 0
        while 1:
            rd.seed(i)
            if rd.randint(0,30) == c: break
            i += 1
        yield i
##
print(list(encode("404CTF{M3RC1_PY7H0N3.10_P0UR_L3_M47CH}")))