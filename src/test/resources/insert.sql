TRUNCATE post_offices, addresses, persons, items, operations;

INSERT INTO post_offices (indexx, name)
VALUES (123456, 'Pushkino'),
       (753614, 'Ivanovo'),
       (567890, 'Novgorod');

INSERT INTO addresses (id, description, post_office_index)
VALUES ('246c1ba6-08e1-4fcb-b617-29b27f987b6a', 'Spring Street, 22', 123456),
       ('4a91338b-53c9-4276-a4bf-36ea9d444bb1', 'Red Square, 1', 753614);

INSERT INTO persons (id, name, address_id)
VALUES ('1a511344-5755-4510-b739-a66cd59c144e', 'Elena', '246c1ba6-08e1-4fcb-b617-29b27f987b6a'),
       ('b5333b38-8c72-4c7f-8a6c-1adfa3f67f4b', 'Sender', '4a91338b-53c9-4276-a4bf-36ea9d444bb1');

INSERT INTO items (id, type, sender_id, recipient_id, destination_post_office_index)
VALUES ('c8cd3aee-62af-4239-a5da-23965734a733', 'PARCEL', 'b5333b38-8c72-4c7f-8a6c-1adfa3f67f4b',
        '1a511344-5755-4510-b739-a66cd59c144e', 123456),
       ('7af49324-d3a3-4550-9448-38f00103565b', 'LETTER', 'b5333b38-8c72-4c7f-8a6c-1adfa3f67f4b',
        '1a511344-5755-4510-b739-a66cd59c144e', 123456);

INSERT INTO operations (id, item_id, post_office_index, state, is_destination, date)
VALUES ('29423095-ffd3-497c-8b2d-6635ce9355fa', 'c8cd3aee-62af-4239-a5da-23965734a733', 753614, 'REGISTERED', false,
        '2023-08-22 17:55:19.568556'),
       ('9a7b2b40-1db2-4fa5-81b0-2cae94826b58', '7af49324-d3a3-4550-9448-38f00103565b', 567890, 'ARRIVED', false,
        '2023-08-28 18:50:57.523104'),
       ('d3c7d87a-f231-4cd7-8ab6-08a2ce786630', '7af49324-d3a3-4550-9448-38f00103565b', 753614, 'REGISTERED', false,
        '2023-08-22 18:48:45.775095');