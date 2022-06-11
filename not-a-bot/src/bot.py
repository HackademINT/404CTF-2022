#!/usr/bin/env python3

import os
import mariadb
import discord

#get the token
TOKEN=os.getenv("DISCORD_TOKEN")
MARIADB_URL=os.getenv("MARIADB_URL")
MARIADB_USERNAME=os.getenv("MARIADB_USERNAME")
MARIADB_PASSWORD=os.getenv("MARIADB_PASSWORD")
MARIADB_DATABASE=os.getenv("MARIADB_DATABASE")

#Help message
HELP = "Je ne suis pas un automate, juste un humain qui veut aider :-)\n```Commandes disponibles :\n!chercher argument -> rechercher argument dans la base de données\n!authentification motdepasse -> authentifiez vous pour accéder au mode privilégié\n!drapeau -> obtenez un mystérieux drapeau```"
HELP_LOGGED = "Je ne suis pas un automate, juste un humain qui veut aider :-)\n```Commandes disponibles :\n!chercher argument -> rechercher argument dans la base de données\n!authentification motdepasse -> authentifiez vous pour accéder au mode privilégié\n!drapeau -> obtenez un mystérieux drapeau\n!debug -> debug command```"

#connect to mariadb
conn = mariadb.connect(
                user=MARIADB_USERNAME,
                password=MARIADB_PASSWORD,
                host=MARIADB_URL,
                port=3306,
                database=MARIADB_DATABASE
            )

# Disable Auto-Commit
conn.autocommit = False

#discord bot connection
client = discord.Client()

@client.event
async def on_ready():
    print('We have logged in as {0.user}'.format(client))


#handle incoming message
@client.event
async def on_message(message):
    #avoid the bot responds to itself
    if message.author == client.user:
        return

    #Private messages
    if message.channel.type == discord.ChannelType.private:
        if "!drapeau" in message.content:
            cur = conn.cursor()
            cur.execute(f"SELECT user from {MARIADB_DATABASE}.Privileged_users where user LIKE {str(message.author.id)}")
            result = cur.fetchall()
            if len(result) > 0:
                    await message.channel.send("404CTF{D1sc0rd_&_injection_SQL}")
                    return
            await message.channel.send("Vous devez être authentifiés :-)")

        elif "!authentification" in message.content:
            if message.content == "!authentification 404CTF{D1sc0rd_&_injection_SQL}":
                cur = conn.cursor()
                cur.execute(f"INSERT INTO {MARIADB_DATABASE}.Privileged_users(user) VALUES ({message.author.id})")   #register user as logged in
                conn.commit()
                await message.channel.send("Bravo ! Vous pouvez valider le challenge avec le mot de passe")
            elif message.content == "!authentification":
                await message.channel.send("Usage : `!authentification motDePasse`")
            else:
                await message.channel.send("Mauvais mot de passe")

        elif "!aide" in message.content:
            cur = conn.cursor()
            req = f"SELECT user from {MARIADB_DATABASE}.Privileged_users where user LIKE {str(message.author.id)}"
            cur.execute(req)
            result = cur.fetchall()
            if len(result) > 0:
                    await message.channel.send(HELP_LOGGED)
                    return
            await message.channel.send(HELP)
                    
        elif "!chercher" in message.content:
            tab = message.content.split(" ")
            req = f"SELECT message FROM {MARIADB_DATABASE}.data WHERE message LIKE \"%"
            for i in range(1, len(tab)):
                req += tab[i] + " "
            req = req[:-1]
            req += "%\""
            if "insert" in req.lower() or "drop" in req.lower() or "sleep" in req.lower() or len(tab) == 1:
                req = f"SELECT message FROM {MARIADB_DATABASE}.data WHERE message LIKE \"\""
            cur = conn.cursor()
            cur.execute(req)    #do sql request
            answer = "```Results:\n"
            i = 1
            row = cur.fetchone()
            while row is not None:    #treat answer
                answer += "Result #" + str(i) + ":\n"
                answer += ">" + str(row[0]) + "\n"
                i += 1
                row = cur.fetchone()
            answer += "```"
            await message.channel.send(answer)
            
        elif "!debug" in message.content:
            cur = conn.cursor()
            req = f"SELECT user from {MARIADB_DATABASE}.Privileged_users where user LIKE {str(message.author.id)}"
            cur.execute(req)
            result = cur.fetchall()
            if len(result) > 0:
                    await  message.channel.send("Debug déployé sur le port 31337 ! Mot de passe : `p45_uN_4uT0m4t3`")
                    return
            await message.channel.send("Bonjour ! Tape `!aide` pour obtenir de l'aide.")
        else:
            await message.channel.send("Bonjour ! Tape `!aide` pour obtenir de l'aide.")
    
    #Server channel
    else:
        if str(client.user.id) in message.content:
            await message.channel.send(message.author.mention + " envoie-moi un message privé, je ne fonctionne que comme ça :-)")


#Connect to discord
client.run(TOKEN)
conn.close()