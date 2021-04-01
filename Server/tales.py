from flask import Blueprint, request
from db_conn import db

tales_page = Blueprint('tales', __name__)


@tales_page.route('/tales/preview', methods=['POST'])
def get_tales_preview():
    try:
        username = request.json.get('username')

    except Exception as e:
        raise e
    return db.get_tales_preview(username)


@tales_page.route('/tales/get_tales_by_id', methods=['POST'])
def get_next_tale_part():
    try:
        tale_id = request.json.get('taleId')

        return db.get_tales_by_hero_id(tale_id)

    except Exception as e:
        raise e

