insert into auth(id, account_non_locked, email, enabled, identity_number, password, roles)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8138a' ,true, 'ataugurkaratas1@gmail.com', true, '11111111111',
        '$2a$10$rsMMnJh1BZCLOTMY0VLG6uou6LgOBeEzH36Ph5yTay.ybox8tvBkC', 'ROLE_ADMIN');
insert into auth(id, account_non_locked, email, enabled, identity_number, password, roles)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8138b' ,true, 'ataugurkaratas2@gmail.com', true, '22222222222',
        '$2a$10$rsMMnJh1BZCLOTMY0VLG6uou6LgOBeEzH36Ph5yTay.ybox8tvBkC', 'ROLE_EMPLOYEE');
insert into auth(id, account_non_locked, email, enabled, identity_number, password, roles)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8138c' ,true, 'ataugurkaratas3@gmail.com', true, '33333333333',
        '$2a$10$rsMMnJh1BZCLOTMY0VLG6uou6LgOBeEzH36Ph5yTay.ybox8tvBkC', 'ROLE_CUSTOMER');
insert into auth(id, account_non_locked, email, enabled, identity_number, password, roles)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8138d' ,true, 'ataugurkaratas4@gmail.com', true, '44444444444',
        '$2a$10$rsMMnJh1BZCLOTMY0VLG6uou6LgOBeEzH36Ph5yTay.ybox8tvBkC', 'ROLE_CUSTOMER');
insert into auth(id, account_non_locked, email, enabled, identity_number, password, roles)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8138e' ,true, 'ataugurkaratas5@gmail.com', true, '55555555555',
        '$2a$10$rsMMnJh1BZCLOTMY0VLG6uou6LgOBeEzH36Ph5yTay.ybox8tvBkC', 'ROLE_CUSTOMER');

insert into admin(id, created_by, created_date, last_modified_by, last_modified_date, birth_date, image_path, name, phone_number, surname, auth_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112a', '11111111111', now(), '11111111111', now(), '1980-01-01', 'profile.jpeg',
        'Ataugur', '905425428275', 'Karatas', '30fca376-9bd0-4e07-82f4-1b6be2c8138a');

insert into employee(id, created_by, created_date, last_modified_by, last_modified_date, birth_date, image_path, name, phone_number, surname, auth_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112b', '11111111111', now(), '11111111111', now(), '1980-01-01', 'profile.jpeg',
        'Ataugur', '905425428275', 'Karatas', '30fca376-9bd0-4e07-82f4-1b6be2c8138b');

insert into customer(id, created_by, created_date, last_modified_by, last_modified_date, birth_date, image_path, name, phone_number, monthly_salary, surname, auth_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112c', '11111111111', now(), '11111111111', now(), '1980-01-01', 'profile.jpeg',
        'Ataugur', '905425428275', '5000', 'Karatas', '30fca376-9bd0-4e07-82f4-1b6be2c8138c');

insert into customer(id, created_by, created_date, last_modified_by, last_modified_date, birth_date, image_path, name, phone_number, monthly_salary, surname, auth_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112d', '11111111111', now(), '11111111111', now(), '1980-01-01', 'profile.jpeg',
        'Ataugur', '905425428275', '5000', 'Karatas', '30fca376-9bd0-4e07-82f4-1b6be2c8138d');

insert into customer(id, created_by, created_date, last_modified_by, last_modified_date, birth_date, image_path, name, phone_number, monthly_salary, surname, auth_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112e', '11111111111', now(), '11111111111', now(), '1980-01-01', 'profile.jpeg',
        'Ataugur', '905425428275', '5000', 'Karatas', '30fca376-9bd0-4e07-82f4-1b6be2c8138e');

insert into customer_credit_rating(id, created_by, created_date, last_modified_by, last_modified_date, credit_product_payment_habit_score,  credit_usage_intensity_score,
                                   current_account_and_debit_status_score, new_credit_product_launches_score, customer_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112e', '11111111111', now(), '11111111111', now(), 50, 50, 50, 50, '30fca376-9bd0-4e07-82f4-1b6be2c8112c');

insert into customer_credit_rating(id, created_by, created_date, last_modified_by, last_modified_date, credit_product_payment_habit_score,  credit_usage_intensity_score,
                                   current_account_and_debit_status_score, new_credit_product_launches_score, customer_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112c', '11111111111', now(), '11111111111', now(), 50, 50, 50, 50, '30fca376-9bd0-4e07-82f4-1b6be2c8112d');

insert into customer_credit_rating(id, created_by, created_date, last_modified_by, last_modified_date, credit_product_payment_habit_score,  credit_usage_intensity_score,
                                   current_account_and_debit_status_score, new_credit_product_launches_score, customer_id)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8112a', '11111111111', now(), '11111111111', now(), 50, 50, 50, 50, '30fca376-9bd0-4e07-82f4-1b6be2c8112e');

insert into credit_rating(id, created_by, created_date, last_modified_by, last_modified_date, credit_product_payment_habits, credit_score_starting_value,
                          credit_usage_intensity, current_account_and_debit_status, new_credit_product_launches)
values ('30fca376-9bd0-4e07-82f4-1b6be2c8138c', '11111111111', now(), '11111111111', now(),  55, 250, 25, 10, 10);