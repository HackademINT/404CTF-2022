FROM bitnami/python

WORKDIR /app
COPY . .
RUN pip install -r requirements.txt
RUN chmod u+x server.sh

EXPOSE 80
CMD ["./server.sh"]
