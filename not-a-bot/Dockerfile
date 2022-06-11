FROM python:3.10
COPY ./src /src
WORKDIR /src

#install python and pip
RUN useradd -ms /bin/bash discord

RUN pip install discord.py mariadb

USER discord

ENTRYPOINT [ "/usr/bin/env", "python3", "/src/bot.py" ]