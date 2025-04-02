CREATE TABLE users (
id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
username varchar(64)  NOT NULL,
email varchar(64) UNIQUE NOT NULL,
age INT CHECK (age >= 12 AND age <= 100),
weight INT CHECK (weight >= 30),
height DOUBLE PRECISION CHECK (height >= 120),
goal INT NOT NULL,
gender INT NOT NULL, -- Также ENUM
daily_calorie_limit DOUBLE PRECISION NOT NULL DEFAULT 0
);
CREATE TABLE dish (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    name VARCHAR(64) NOT NULL,
    calories INT CHECK (calories >= 1),
    proteins DOUBLE PRECISION CHECK (proteins >= 1),
    fats DOUBLE PRECISION CHECK (fats >= 1),
    carbs DOUBLE PRECISION CHECK (carbs >= 1)
);
CREATE TABLE meal (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    user_id BIGINT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE meal_dish (
    meal_id BIGINT NOT NULL,
    dish_id BIGINT NOT NULL,
    PRIMARY KEY (meal_id, dish_id),
    FOREIGN KEY (meal_id) REFERENCES meal(id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE CASCADE
);
