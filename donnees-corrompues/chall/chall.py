import base64
import string
import random
from inputimeout import inputimeout, TimeoutOccurred
import io

alphabet = string.ascii_letters + string.digits
# Homoglyphes
badchar = {
    "a": "а",
    "A": "А", 
    "B": "В",
    "e": "е", 
    "H": "Н", 
    "K": "К",
    "o": "о", 
    "p": "р", 
    "c": "с", 
    "T": "Т", 
    "x": "х",
    "y": "у"
}

chartotransform = list(badchar.keys())
charentrop = string.punctuation.replace("=", "").replace("/", "").replace("+", "")

with open("flag.mp3", "rb") as f:
    flag = f.read()

lenpacket = len(flag) // 250
data = [flag[lenpacket*i:(i+1)*lenpacket] for i in range(249)]
data.append(flag[lenpacket*249:])
assert(flag == b"".join(data))

print("Oulah, ces données semblent bien étranges ! Pouvez-vous les décoder pour nous ?")
print("Il faut nous renvoyer les octets sous forme binaire (avec les zéros inutiles), tout collé, sans aucun autre caractère !")
print("Un exemple qu'a réussi à reconstituer notre groupe d'experts :")
print("L'entrée : Rmх%hZуА*6KQ")
print("Doit donner en sortie : 01000110011011000110000101100111001000000011101000101001")
print()
for nb_fois in range(250):
    print(f"[{nb_fois+1} / 250] Voilà les données : ", end="")
    packet = base64.b64encode(data[nb_fois]).decode()
    char_en_trop = random.random()
    char_nul = random.random()
    strip_egal = random.random()
    if char_en_trop < 0.5:
        n = random.randrange(1, 10)
        for i in range(n):
            r = random.randrange(len(packet)-1)
            packet = packet[:r] + charentrop[random.randrange(28)] + packet[r:]
    if char_nul < 0.6:
        n = random.randrange(10)
        for i in range(n):
            c = chartotransform[random.randrange(12)]
            packet = packet.replace(c, badchar[c])
    if strip_egal < 0.4:
        packet = packet.replace("=", "")
    print(packet)
    # l'utilisateur a 5 secondes pour répondre
    try:
        user_answer = inputimeout(prompt='>> ', timeout=5)
    except:
        print("Mmh, ça m'a pas l'air d'être ça... Réessaie !!")
        exit()
    # vérification de la réponse
    if user_answer == "".join([bin(c)[2:].zfill(8) for c in data[nb_fois]]):
        print("Ca semble bon ! Voilà la suite !")
    else:
        print("Aie, je pense qu'il y a un problème ! Réessaie !")
        exit()

# si on est là, c'est qu'on a réussi
print("Wouaouh, tu as réussi ! Tu peux maintenant utiliser ces données pour obtenir le flag ! Il suffit de les assembler... ;-)")



