from flask import Flask, make_response, render_template, request, redirect
from werkzeug.exceptions import HTTPException

app = Flask(__name__)

FLAG="\x00\x00réunion Hallebarde le 26 juin à 13h. Venez nombreux. N'oubliez pas le mot de passe qui vous permettra d'entrer dans la salle secrète : 404CTF{Dr0l3_D3_m0Y3N_d3_f41r3_P4sS3r_d3S_d0nN33s_n0N?}. Longue vie à Hallebarde !".encode("utf-8")

ENCODED_FLAG = "".join([oct(c)[2:].zfill(3) for c in FLAG])

@app.route("/")
def hello_world():
    resp = make_response(render_template("index.html", text="d'accueil"))
    return resp

@app.route("/admin")
def admin():
    resp = make_response(render_template("index.html", text="d'administration. On vient de vous le dire, il y a vraiment rien ici. Vous êtes déçus ?"))
    return resp

@app.route("/page/<n>")
def page(n):
    n = int(n)
    resp = make_response(render_template("index.html", text=str(n)))
    location = 2*n % len(ENCODED_FLAG)
    resp.status = 200 + int(ENCODED_FLAG[location:location+2])
    return resp

@app.errorhandler(Exception)
def handle_exception(e):
    message="Vous arrivez à provoquer une erreur même sur un site à peine en construction ? Vous êtes diaboliques :-o"
    return (message, 500)
