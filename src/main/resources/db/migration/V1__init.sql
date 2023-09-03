CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS post_offices (
    indexx              INT UNIQUE      NOT NULL CONSTRAINT PK_post_offices PRIMARY KEY,
    name                VARCHAR(100)    NOT NULL
);

CREATE TABLE IF NOT EXISTS addresses (
    id                  UUID            NOT NULL CONSTRAINT PK_addresses PRIMARY KEY DEFAULT uuid_generate_v4(),
    description         VARCHAR(255)    NOT NULL,
    post_office_index   INT             NOT NULL,
    CONSTRAINT addresses_post_offices_FK FOREIGN KEY (post_office_index) REFERENCES post_offices (indexx)
);

CREATE TABLE IF NOT EXISTS persons (
    id                  UUID            NOT NULL CONSTRAINT PK_persons PRIMARY KEY DEFAULT uuid_generate_v4(),
    name                VARCHAR(100)    NOT NULL,
    address_id          UUID            NOT NULL,
    CONSTRAINT persons_addresses_FK FOREIGN KEY (address_id) REFERENCES addresses (id)
);

CREATE TABLE IF NOT EXISTS items (
    id                  UUID            NOT NULL CONSTRAINT PK_items PRIMARY KEY DEFAULT uuid_generate_v4(),
    type                VARCHAR(15)     NOT NULL,
    sender_id           UUID            NOT NULL,
    recipient_id        UUID            NOT NULL,
    destination_post_office_index   INT             NOT NULL,
    CONSTRAINT items_persons_sender_FK FOREIGN KEY (sender_id) REFERENCES persons (id),
    CONSTRAINT items_persons_recipient_FK FOREIGN KEY (recipient_id) REFERENCES persons (id),
    CONSTRAINT items_post_offices_FK FOREIGN KEY (destination_post_office_index) REFERENCES post_offices (indexx)
);

CREATE TABLE IF NOT EXISTS operations (
    id                  UUID            NOT NULL CONSTRAINT PK_postal_states PRIMARY KEY DEFAULT uuid_generate_v4(),
    item_id             UUID            NOT NULL,
    post_office_index   INT             NOT NULL,
    state               VARCHAR(15)     NOT NULL,
    is_destination      BOOLEAN         DEFAULT FALSE,
    date                TIMESTAMP       DEFAULT now(),
    CONSTRAINT operations_items_FK FOREIGN KEY (item_id) REFERENCES items (id),
    CONSTRAINT operations_post_offices_FK FOREIGN KEY (post_office_index) REFERENCES post_offices (indexx)
)