from flask import Blueprint, request
from db_conn import db

users_page = Blueprint('users', __name__)


@users_page.route('/users/register', methods=['POST'])
def register():
    """
    :return: success(true/false), msg
    """
    username = request.json.get('username')
    password = request.json.get('password')
    response = dict()

    try:
        success = db.add_user(username, password)
        response["response"] = success
    except Exception as e:
        raise e
    return response


@users_page.route('/users/login', methods=['POST'])
def login():
    """
    :return: id/ null
    """
    print("json:", request.json)
    username = request.json.get('username')
    password = request.json.get('password')
    response = dict()

    try:
        success = db.login_user(username, password)

        user_id = success["userId"]

        if user_id:
            response["userId"] = user_id[0]
        else:
            response["userId"] = -1

    except Exception as e:
        raise e
    return response


@users_page.route('/users/get_user_record', methods=['POST'])
def get_user_record():
    """
    :return: heroId
    """
    username = request.json.get('username')
    print("username api", username)

    try:
        hero_id = db.get_user_record(username)
        print(hero_id)

    except Exception as e:
        raise e
    return hero_id
