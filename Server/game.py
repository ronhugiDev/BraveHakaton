from flask import Blueprint, request
from db_conn import db

game_page = Blueprint('game', __name__)


@game_page.route('/game/end_session', methods=['POST'])
def end_session():
    username = request.json.get('username')
    hero_id = request.json.get('heroId')
    answers = request.json.get('userAnswers')
    user_score = 0

    if hero_id is not None and answers:
        user_score = answers.count(1) / len(answers) * 100          # currect answers are answer1 always.
        db.update_user_score(username, hero_id)

    return str(user_score)
