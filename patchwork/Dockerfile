FROM ghcr.io/google/nsjail/nsjail@sha256:e1565be1106c627436e8f7b286487d84c088048cd2a94c20398815a10566dbfc

RUN mkdir /app
WORKDIR /app

COPY data/ .

RUN chmod u+x /app/nsjail.sh && \
    chmod u+x /app/recrutement && \
    chmod u+r /app/flag.txt

EXPOSE 4444

ENTRYPOINT ["/app/nsjail.sh"]

CMD [ "/app/recrutement" ]

