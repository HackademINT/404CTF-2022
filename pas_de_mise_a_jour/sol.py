coded = [292, 194, 347, 382, 453, 276, 577, 434, 183, 295, 318, 196, 482, 325, 412, 502, 396, 402, 328, 194, 473, 490, 299, 503, 386, 215, 263, 211, 318, 206, 533]
#404CTF{R34D1NG_PYTH0N_BYT3C0D3}
key = 'd1j#H(&Ja1_2\x2061fG&'

def decode(l):
    match l:
        case [el, *rest]:
            y = ord(key[len(rest) % len(key)])
            return chr(((el ^ y)//5)) + decode(rest)
        case []:
            return ''

print(decode(coded))  