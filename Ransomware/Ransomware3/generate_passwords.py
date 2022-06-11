import csv
from string import printable
from random import randrange

f = open("pass.csv", "w")
with open("/opt/SecLists/Usernames/xato-net-10-million-usernames.txt", "r") as g:
	usernames = g.readlines()
with open("/opt/SecLists/Discovery/DNS/subdomains-top1million-5000.txt", "r") as g:
	domains = g.readlines()

extensions = ["com", "xyz", "fr", "org", "net", "uk", "bzh", "io", "ru"]

writer = csv.writer(f)
for i in range(15427):
	password = "".join([printable[:75][randrange(75)] for i in range(20)]).strip()
	url = f"https://{domains[randrange(len(domains))].strip()}.{extensions[randrange(len(extensions))].strip()}"
	user = usernames[randrange(len(usernames))].strip()
	writer.writerow([user, password, url])
