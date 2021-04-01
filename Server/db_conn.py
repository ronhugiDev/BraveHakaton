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
            conn = sq.connect(DBConnection.db_name, check_same_thread=False)
        except sq.Error as e:
            raise e

        return conn

    def login_user(self, username: str, password: str):
        """
        safe login check
        :param username: username
        :param password: password
        :return: whether user exists with same username and password
                 user data (username, user_type)
        """
        output = dict()

        try:
            user_id = self.cursor.execute("""
            SELECT userId FROM users 
            WHERE username = ? AND password = ?
            LIMIT 1
            """, (username, password,)).fetchone()
            output['userId'] = user_id

        except sq.Error:
            output = "Error"

        return output

    def add_user(self, username: str, password: str) -> (bool, str):
        """
        add user function
        :param username: username
        :param password: password
        :return: could add user
        """
        success, msg = True, "Success"

        try:
            self.cursor.execute("""
            INSERT INTO users(USERNAME, PASSWORD)
            VALUES (?, ?)
            """, (username, password,))
            self.conn.commit()

        except sq.IntegrityError:
            success, msg = False, "Username already exists"

        except sq.Error as e:
            success, msg = False, f"Unknown error: {e.__str__()}"
        return success, msg

    def get_tales_by_hero_id(self, hero_code: int) -> (bool, dict):
        response = dict()

        try:
            tales = self.cursor.execute("""
            SELECT levelNumber, story, qst, answer1, answer2, info FROM heroesTales 
            WHERE heroCode = ? ORDER BY levelNumber
              """, (hero_code, )).fetchall()

            for tale in tales:
                response[tale[0]] = {
                    "levelNumber": tale[0],
                    "story": tale[1],
                    "qst": tale[2],
                    "answer1": tale[3],
                    "answer2": tale[4],
                    "info": tale[5],
                }

        except sq.IntegrityError:
            response = "Error in server - IntegrityError"

        except TypeError:
            response = "404"

        return response

    def get_tales_preview(self, username: str):
        response = dict()

        try:
            all_heroes = self.cursor.execute("""
                        SELECT heroesInfo.name
                        FROM heroesInfo INNER JOIN heroesTales
                        ON heroesTales.heroCode = heroesInfo.heroCode
                        WHERE heroesTales.levelNumber = 0 
                          """).fetchall()

            passed_heroes = self.cursor.execute("""
            SELECT heroesInfo.name FROM heroesInfo
            INNER JOIN records
            ON records.heroId >= heroesInfo.heroCode
            INNER JOIN users
            ON users.userId = records.userId
            WHERE users.username = ?
            """, (username, )).fetchall()

            response["all_heroes"] = all_heroes
            response["passed_heroes"] = passed_heroes

        except sq.IntegrityError:
            response = "Error in server - IntegrityError"

        except TypeError:
            response = "404"

        return response

    def get_user_record(self, username: str):
        response = dict()
        try:
            record = self.cursor.execute("""
            SELECT records.heroId FROM records INNER JOIN users
            WHERE users.username = ? AND records.userId = users.userId
            LIMIT 1
              """, (username, )).fetchone()

            if hasattr(record, '__iter__'):
                response["record"] = record[0]
            else:
                response = '404'

        except sq.IntegrityError:
            response = "Error in server - IntegrityError"

        except TypeError:
            response = "Error in server - TypeError"

        return response

    ######
    #   TO DELETE?      |
    #                   v
    #####
    def check_current_answers_percentage(self, user_answers: list) -> bool:
        answer = False
        try:
            query_result = self.cursor.execute("""
            SELECT COUNT(*) FROM heroesTales WHERE answer1 IN ({seq})
            """.format(seq=','.join(['?']*len(user_answers))), (*user_answers, )).fetchone()

            print("query_result", query_result)

            if query_result:
                answer = query_result[0] / len(user_answers) * 100

        except Exception as e:
            raise e
        return answer

    def get_story_length(self, answer: str):
        try:
            amount = self.cursor.execute("""
                SELECT COUNT(*)
                FROM heroesTales
                WHERE heroCode = (
                    SELECT heroCode FROM heroesTales WHERE answer1 = ?)
            """, (answer, )).fetchone()[0]

        except Exception as e:
            raise e

        return amount

    def update_user_score(self, username: str, old_hero_id: int):
        try:
            self.cursor.execute("""
            UPDATE records 
            SET heroId = ?
            where exists (
                select userId from users
                 where username = ? And userId = records.userId
                 )
            """, (old_hero_id, username,))
            self.conn.commit()

        except Exception as e:
            raise e

    def close_db(self):
        """
        closes database
        """
        self.conn.close()

    def __del__(self):
        self.close_db()
        print("Database is closed")


db = DBConnection()
