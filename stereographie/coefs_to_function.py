import math
import cmath


def parse_coefs(file):
    '''
    Parses Fourier coefficients from the text files
    '''
    with open(file, 'r') as f:
        data = f.readlines()
    data = [complex(data[i].split(':')[1]) for i in range(len(data))]
    return data


def create_f(coefs):
    '''
    Returns the function determined by the Fourier coefficients `coefs`

    Ugly but works fine
    '''
    n = (len(coefs)-1)//2
    neg, cc, pos = coefs[:n][::-1], coefs[n], coefs[n+1:]
    return lambda x: cc + sum((neg[i] * cmath.exp(x*2*math.pi*1j*(-i - 1)) + pos[i] * cmath.exp(x*2*math.pi*1j*(i + 1)) ) for i in range(len(neg))) #It's just the formula, nothing much to say.


# This example showcases the 1-periodicity of the created function

# for i in range(10):
#     c = 'A'
#     print(create_f(parse_coefs(f'coefs_chars/coefs_{c}.txt'))(i))
