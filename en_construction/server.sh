#!/bin/sh

gunicorn run:app -w 4 --threads 4 -b 0.0.0.0:80 -u nobody -g nogroup
