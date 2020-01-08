CREATE TABLE IF NOT EXISTS resources (

    resource_id UUID NOT NULL PRIMARY KEY,
    metal INTEGER NOT NULL,
    cristal INTEGER NOT NULL,
    deuterium INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS user_instance (

    user_id UUID NOT NULL REFERENCES users(user_id),
    resource_id UUID NOT NULL REFERENCES resources(resource_id),
    UNIQUE (user_id, resource_id)
);