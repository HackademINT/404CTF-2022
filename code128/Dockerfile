FROM alpine/socat:latest

# Tous les paquets 'bizarres' sont là pour permettre l'installation de pillow
RUN apk add python3 py3-pip gcc python3-dev jpeg-dev zlib-dev musl-dev
RUN pip3 install pillow inputimeout
RUN adduser ctf --disabled-password
COPY chall.py /home/ctf

USER ctf
WORKDIR /home/ctf
EXPOSE 4000
CMD ["tcp-listen:4000,reuseaddr,fork", "exec:'python3 chall.py'"]
