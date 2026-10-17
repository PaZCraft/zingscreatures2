
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1] / "definitions"

ERAS = {
    "origin", "legend", "genesis",
    "division", "infinity", "eternity"
}

CATEGORIES = {
    "classical", "vanilla", "time", "holiday",
    "fantasy", "natural", "sound", "hobby"
}

SHAPES = {
    "overworld", "void", "end", "nether", "sift",
    "classic", "island", "sky", "geometry", "space"
}

ID_PATTERN = re.compile(r"^[a-z0-9_]+$")
COLOR_PATTERN = re.compile(r"^#[0-9A-Fa-f]{6}$")


def load_json_files(folder):
    result = []

    if not folder.exists():
        return result

    for path in sorted(folder.glob("*.json")):
        try:
            with path.open(encoding="utf-8") as file:
                result.append((path, json.load(file)))
        except (json.JSONDecodeError, OSError) as error:
            print(f"ERROR {path}: {error}")

    return result


def validate_classes():
    classes = load_json_files(ROOT / "classes")
    seen_ids = set()
    seen_numbers = set()
    errors = 0

    def error(path, message):
        nonlocal errors
        errors += 1
        print(f"ERROR {path}: {message}")

    for path, data in classes:
        if not isinstance(data, dict):
            error(path, "Root JSON value must be an object")
            continue

        for field in ("class_name", "class_id", "class_number",
                      "era_tier", "class_category", "class_color"):
            if field not in data:
                error(path, f"Missing required field: {field}")

        class_id = data.get("class_id")

        if isinstance(class_id, str):
            if not ID_PATTERN.fullmatch(class_id):
                error(path, "class_id must be lowercase letters, "
                            "numbers, and underscores")
            if class_id in seen_ids:
                error(path, f"Duplicate class_id: {class_id}")
            seen_ids.add(class_id)

        number = data.get("class_number")

        if type(number) is not int or number < 0:
            error(path, "class_number must be a nonnegative integer")
        elif number in seen_numbers:
            error(path, f"Duplicate class_number: {number}")
        else:
            seen_numbers.add(number)

        if data.get("era_tier") not in ERAS:
            error(path, "Invalid era_tier")

        if data.get("class_category") not in CATEGORIES:
            error(path, "Invalid class_category")

        if data.get("world_gen_shape_dimension") not in SHAPES:
            error(path, "Invalid world_gen_shape_dimension")

        color = data.get("class_color")
        if not isinstance(color, str) or not COLOR_PATTERN.fullmatch(color):
            error(path, "class_color must use #RRGGBB")

    return errors


if __name__ == "__main__":
    error_count = validate_classes()

    if error_count:
        print(f"\nValidation failed: {error_count} error(s).")
        sys.exit(1)

    print("Class definitions passed basic validation.")
