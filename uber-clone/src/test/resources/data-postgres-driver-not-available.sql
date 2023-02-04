INSERT INTO public.vehicle(
    allows_baby, allows_pet, model, number_of_seats, type)
VALUES ( true, false, 'Audi A4', 4, 'PREMIUM');

INSERT INTO public.vehicle(
    allows_baby, allows_pet, model, number_of_seats, type)
VALUES ( false, false, 'Fiat Punto', 4, 'REGULAR');

INSERT INTO public.vehicle(
    allows_baby, allows_pet, model, number_of_seats, type)
VALUES ( false, false, 'BMW 730i', 3, 'ECO');
INSERT INTO public.vehicle(
    allows_baby, allows_pet, model, number_of_seats, type)
VALUES ( true, true, 'Volkwagen Golf 2 dizel ', 4, 'ECO');
INSERT INTO public.vehicle(
    allows_baby, allows_pet, model, number_of_seats, type)
VALUES ( true, true, 'Volvo XC90', 6, 'PREMIUM');
INSERT INTO public.vehicle(
    allows_baby, allows_pet, model, number_of_seats, type)
VALUES ( true, true, 'Renault Clio', 4, 'REGULAR');

INSERT INTO public.vehicle(
    allows_baby, allows_pet, model, number_of_seats, type)
VALUES ( true, true, 'Peugeot 206', 5, 'REGULAR');



INSERT INTO public.role(
    id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO public.role(
    id, name)
VALUES (2, 'ROLE_CLIENT');

INSERT INTO public.role(
    id, name)
VALUES (3, 'ROLE_DRIVER');


INSERT INTO public.location(
     latitude, longitude)
VALUES ( 45.246401, 19.833793);

INSERT INTO public.admin(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, reset_password_token, surname, social_login)
VALUES (1, false, 'Sremska Mitrovica', false, 'bubibubisa@gmail.com', true, null,'Bubi', '$2a$10$YlFmOPcg9JpJnTPQsJhsaO4t0MH/KAtWPD3dvWW2MO0ddBAkl4LVC', '066333555', '',null , 'Bubisa', false);

INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding,coins, social_login)
VALUES (2, false, 'Novi Sad', false, 'serfezev@gmail.com', true, null, 'Vanja', '$2a$10$YlFmOPcg9JpJnTPQsJhsaO4t0MH/KAtWPD3dvWW2MO0ddBAkl4LVC', '0665241322','', 'Serfeze', false, 10000, false);


INSERT INTO public.driver(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, reset_password_token, surname, active, available, current_location_id, vehicle_id, social_login)
VALUES (3, false, 'Novi Sad', false, 'stevaszumza@gmail.com', true, null,'Aleksa', '$2a$10$YlFmOPcg9JpJnTPQsJhsaO4t0MH/KAtWPD3dvWW2MO0ddBAkl4LVC', '0669087659', '', null,'Stevanovic', false,false ,1, 1, false);

INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding,coins, social_login)
VALUES (4, false, 'Novi Sad', false, 'esteban@gmail.com', true, null, 'Stefan', '$2a$10$YlFmOPcg9JpJnTPQsJhsaO4t0MH/KAtWPD3dvWW2MO0ddBAkl4LVC', '0665241322','', 'Milosevic', false, 0, false);



INSERT INTO public.user_role(
    user_id, role_id)
VALUES (1, 1);


INSERT INTO public.user_role(
    user_id, role_id)
VALUES (2, 2);


INSERT INTO public.user_role(
    user_id, role_id)
VALUES (3, 3);

INSERT INTO public.user_role(
    user_id, role_id)
VALUES (4, 2);


INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-01', true, 'Ova majmuncina od vozaca ne dolazi sta se desava?', 1, 2);

INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-02', true, 'Odmah cemo proveriti u cemu je problem', 2, 1);

INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-03', true, 'Pozurite moram u WC. Ako mu se uneredim po kolima nije moja krivica pozzz', 1, 2);

INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-04', true, 'Molim vas da ne cinite to. Stvarno bi bilo jako neprijatno', 2, 1);

INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-05', true, 'Nije do mene druze eksploditracu, a mogu to da obavim samo kuci i u Pogonu', 1, 2);

INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-06', true, 'Razumem vas u potpunosti. Pogon zaista ima lep WC. Ucinicu sve sto je u mojoj moci da vozilo dodje sto pre', 2, 1);





INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-01', true, 'Alo be bate ovaj ga nema.', 1, 3);

INSERT INTO public.message(
    date, read, text, recipient_id, sender_id)
VALUES ( '2022-11-02', true, 'Izvinite gospodine, ali ne mozemo tu nista', 3, 1);



INSERT INTO public.location(
     latitude, longitude)
VALUES ( 45.2464013, 19.8337933);

INSERT INTO public.location(
    latitude, longitude)
VALUES ( 45.2450937,19.8360421);


INSERT INTO public.route(
    dtype,  distance_in_km, estimated_time_in_minutes, name, start, end_coordinates_id, start_coordinates_id, customer_id)
VALUES ('Route',  0.3165, 1.266666, null, null, 2, 3, null);


INSERT into public.reservation(estimated_cost,has_baby,has_pet,reservation_time,status,type,vehicle_type,route_id) VALUES
    (137.98000000000002,false,false,'2023-02-04 06:21:59.010323','FINISHED','INSTANT','ANY',1);


INSERT INTO public.reservations(
    customer_id, reservation_id)
VALUES (2, 1);


INSERT INTO public.ride(
    end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES ( '2023-02-04 06:23:36.508963', 5, '2023-02-04 06:23:15.124699', 'FINISHED', 3, null,1);


INSERT INTO public.payment(
    amount, paid, customer_id, reservation_id)
VALUES ( 137.98, true, 2, 1);



INSERT into public.reservation(estimated_cost,has_baby,has_pet,reservation_time,status,type,vehicle_type,route_id) VALUES
    (100,false,false,'2023-02-04 06:21:59.010323','PAYMENT','INSTANT','ANY',1);


INSERT INTO public.reservations(
    customer_id, reservation_id)
VALUES (2, 2);
