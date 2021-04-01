import flask
from users import users_page
from tales import tales_page
from game import game_page

app = flask.Flask(__name__)
app.config["DEBUG"] = True

# adding files
app.register_blueprint(users_page)
app.register_blueprint(tales_page)
app.register_blueprint(game_page)

app.run()
