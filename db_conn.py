import sqlite3 as sq
from os.path import isfile


class DBConnection:
    db_name = "db.db"
    users_table_name = "users"

    def __init__(self):
        assert isfile(DBConnection.db_name), "Database doesn't exists!"

        self.conn = self.create_connection()
        self.cursor = self.conn.cursor()

    @staticmethod
    def create_connection():
        """ create a database connection to the SQLite database
            :return: Connection object or None
        """
        try:
            conn = sq.connect(DBConnection.db_name)
        except sq.Error as e:
            raise e

        return conn

    def login_user(self, username: str, password: str) -> (bool, dict):
        """
        safe login check
        :param username: username
        :param password: password
        :return: whether user exists with same username and password
                 user data (username, user_type)
        """
        try:
            result = self.cursor.execute("""
            SELECT id FROM users 
            WHERE username = ? AND password = ?
            """, (username, password,)).fetchone()
            output = result

        except sq.Error:
            output = False

        return output

    def add_user(self, username: str, password: str) -> (bool, str):
        """
        add user function
        :param username: username
        :param password: password
        :return: could add user
        """
        output, msg = True, "Success"

        try:
            self.cursor.execute("""
            INSERT INTO users(USERNAME, PASSWORD)
            VALUES (?, ?)
            """, (username, password,))
            self.conn.commit()

        except sq.IntegrityError:
            output, msg = False, "Username already exists"

        except sq.Error as e:
            output, msg = False, f"Unknown error: {e.__str__()}"
        return output, msg

    def close_db(self):
        """
        closes database
        """
        self.conn.close()

    def __del__(self):
        self.close_db()
        print("Database is closed")


db = DBConnection()
