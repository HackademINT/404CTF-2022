from flask import Flask, make_response, render_template, request, redirect
from werkzeug.exceptions import HTTPException
from hashlib import sha512
import base64

ADMIN_PASSWORD="UltraSecret123456$!?"
ADMIN_COOKIE="dXNlcm5hbWU9YWRtaW47cGFzc3dvcmQ9NjY2NTEwMTM5MzViNGMyYzMxZDliYWJhOGZhNWQzN2I4MDliMTBkYTQ1M2YyOTNlYzhmOWE3ZmJiMmFiMmUyYzFkNjlkYzhkODA5Njk1MDgwMjhiNWVjMTRlOWQxZGU1ODU5MjlhNGMwZDUzNDk5Njc0NGI0OTVjMzI1ZTNmM2Q="
app = Flask(__name__)

def parse_cookie(cookie):
    decoded = base64.b64decode(cookie).decode()
    t = decoded.split(";")
    data = []
    for elmt in t:
        elmt = elmt.split("=")
        data.append((elmt[0], elmt[1]))
    for ident, val in data:
        if ident == "username":
            username = val
        if ident == "password":
            password = val
    return username, password

def check_cookie(cookie):
    username = None
    decoded = base64.b64decode(cookie).decode()
    t = decoded.split(";")
    data = []
    for elmt in t:
        elmt = elmt.split("=")
        data.append((elmt[0], elmt[1]))
    for ident, val in data:
        if ident == "username":
            username = val
            break
    if username == "admin":
        return False
    else:
        return True

def check_cookie2(cookie):
    decoded = base64.b64decode(cookie).decode()
    t = decoded.split(";")
    return len(t) == 2

def check_cookie3(cookie):
    return cookie != ADMIN_COOKIE

@app.route("/")
def hello_world():
    resp = make_response(render_template("index.html"))
    return resp

@app.route("/connect", methods=["POST", "GET"])
def connect():
    user = "CeciNestPasUnUsername75264"
    cookie = None
    if request.method == "POST":
        user = request.form["username"]
        password = sha512(request.form["password"].encode()).hexdigest()
        cookie = base64.b64encode(f"username={user};password={password}".encode())
        if user == "admin":
            resp = make_response(render_template("error.html", error_message="Je sais bien que tu n'es pas l'admin ! 😛"))
            resp.status = 403
            return resp
    elif "auth" in request.cookies:
        user, password = parse_cookie(request.cookies.get("auth"))
    resp = make_response(render_template("connect.html", user=user))
    if cookie:
        resp.set_cookie("auth", cookie)
    return resp

@app.route("/disconnect", methods=["POST"])
def disconnect():
    resp = make_response(render_template("disconnect.html"))
    resp.delete_cookie("auth")
    return resp

@app.route("/admin")
def admin():
    resp = ("Ca c'était pas prévu... Contactez un organisateur du CTF.", 500)
    if "auth" in request.cookies:
        username, password = parse_cookie(request.cookies.get("auth"))
        if username != "admin":
            resp = make_response(render_template("error.html", error_message="Mauvais nom d'utilisateur !"))
            resp.status = 403
        elif sha512(ADMIN_PASSWORD.encode()).hexdigest() != password:
            resp = make_response(render_template("error.html", error_message="Mauvais mot de passe !"))
            resp.status = 403
        elif not check_cookie2(request.cookies.get("auth")):
            resp = make_response(render_template("error.html", error_message="Filtre de sécurité infaillible ! Il y a plus de deux champs dans le cookie, qu'est-ce qui se passe ??"))
            resp.status = 403
        elif not check_cookie3(request.cookies.get("auth")):
            resp = make_response(render_template("error.html", error_message="Filtre de sécurité infaillible ! Le cookie exact de l'admin a été temporairement bloqué suite à une activité suspecte détectée sur le site !"))
            resp.status = 403

        else:
            resp = make_response(render_template("admin.html"))
    else:
        resp = make_response(render_template("error.html", error_message="Vous n'êtes pas authentifié !"))
        resp.status = 403

    return resp

@app.route("/forgot_password")
def forgot_password():
    username, password = "", ""
    if "auth" in request.cookies:
        username, password = parse_cookie(request.cookies.get("auth"))
        return redirect(f"/forgot_password/{username}")
    else:
        resp = make_response(render_template("forgot_password.html", user=""))
        resp.status = 500
        return resp

@app.route("/forgot_password/<username>")
def forgot_pw_username(username):
    if "auth" in request.cookies:
        user, password = parse_cookie(request.cookies.get("auth"))
        if user == username:
            if username == "admin":
                password = sha512(ADMIN_PASSWORD.encode()).hexdigest()
            # filtre claqué no 1
            if check_cookie(request.cookies.get("auth")):
                resp = make_response(render_template("forgot_password.html", user=username, password=password))
                return resp
            else:
                resp = make_response(render_template("error.html", error_message="Filtre de sécurité infaillible ! Personne ne peut jouer avec le mot de passe de l'admin !"))
                resp.status = 403
                return resp
    resp = make_response(render_template("error.html", error_message="Tu serais pas en train de jouer avec l'URL par hasard ?"))
    return resp
    
@app.errorhandler(Exception)
def handle_exception(e):
    message=""
    if isinstance(e, HTTPException):
        message=e
    else:
        message="Ca c'était pas prévu... Mais qu'est ce que vous avez bien pu toucher pour tout casser ?!"
    resp = make_response(render_template("error.html", error_message=message))
    if isinstance(e, HTTPException):
        resp.status = e.code
    else:
        print(e)
        resp.status = 500
    return resp

