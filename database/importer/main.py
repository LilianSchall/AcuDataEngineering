import json
import time

from tools import get_db


def insert_exercises(db):
    with open("/app/resources/exercises.json") as f:
        data = json.load(f)
        with db.cursor() as cur:
            for exercise in data["exercises"]:
                cur.execute(
                    "INSERT INTO exercises (id, name, difficulty)" "VALUES (%s, %s, %s)",
                    (exercise["id"], exercise["name"], exercise["difficulty"]),
                )
            db.commit()

def insert_regions(db):
    with open("/app/resources/coords.json") as f:
        data = json.load(f)
        with db.cursor() as cur:
            for region in data["regions"]:
                cur.execute(
                    "INSERT INTO regions (id, name)" "VALUES (%s, %s)",
                    (region["region_id"], region["region"]),
                )
            db.commit()

def main():
    db = get_db()
    insert_exercises(db)
    print("Inserted exercises")
    insert_regions(db)
    print("Inserted regions")


if __name__ == "__main__":
    time.sleep(2)
    main()
