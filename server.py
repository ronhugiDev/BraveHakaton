import flask
from flask import request
from db_conn import db

app = flask.Flask(__name__)
app.config["DEBUG"] = True


@app.route('/users/register', methods=['POST'])
def register():
    username = request.json.get('username')
    password = request.json.get('password')
    response = dict()

    try:
        success = db.add_user(username, password)
        response["response"] = success
    except Exception as e:
        raise e
    return response


app.run()
