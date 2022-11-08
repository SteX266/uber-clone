INSERT INTO public.vehicle(
    id, allows_baby, allows_pet, model, number_of_seats, type)
VALUES (1, true, false, 'Audi A4', 4, 'PREMIUM');

INSERT INTO public.vehicle(
    id, allows_baby, allows_pet, model, number_of_seats, type)
VALUES (2, false, false, 'Fiat Punto', 4, 'REGULAR');
INSERT INTO public.vehicle(
    id, allows_baby, allows_pet, model, number_of_seats, type)
VALUES (3, false, false, 'BMW 730i', 3, 'ULTRA_PREMIUM');
INSERT INTO public.vehicle(
    id, allows_baby, allows_pet, model, number_of_seats, type)
VALUES (4, true, true, 'Volkwagen Golf 2 dizel ', 4, 'ULTRA_PREMIUM');
INSERT INTO public.vehicle(
    id, allows_baby, allows_pet, model, number_of_seats, type)
VALUES (5, true, true, 'Volvo XC90', 6, 'PREMIUM');
INSERT INTO public.vehicle(
    id, allows_baby, allows_pet, model, number_of_seats, type)
VALUES (6, true, true, 'Renault Clio', 4, 'REGULAR');




INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (1, false, 'Novi Sad', false, 'serfezev@gmail.com', true, null, 'Vanja', '12312321', '0665241322','picture 1', 'Serfeze', false);


INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (2, false, 'Novi Sad', false, 'vserfeze@gmail.com', true, null, 'Bane', '12312321', '0622209871','picture 2', 'Geric', false);

INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (3, false, 'Novi Sad', false, 'petkoMoron@gmail.com', true, null, 'Bojan', '12312321', '060567003','picture 3', 'Petkovic', false);

INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (4, false, 'Novi Sad', false, 'malamaca@gmail.com', true, null, 'Tamara', '12312321', '0698701322','picture 4', 'Popov', false);

INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (5, false, 'Novi Sad', false, 'vajagicdj@gmail.com', true, null, 'Djordje', '12312321', '0665242012','picture 5', 'Vajagic', false);

INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (6, false, 'Novi Sad', false, 'sicvajanbo@gmail.com', true, null, 'Bojan', '12312321', '0608240098','picture 6', 'Vasic', false);

INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (7, false, 'Novi Sad', false, 'lepimica@gmail.com', true, null, 'Lepi', '12312321', '0665098760','picture 7', 'Mica', false);



INSERT INTO public.driver(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, available, current_location, vehicle_id)
VALUES (8, false, 'Novi Sad', false, 'stevaszumza@gmail.com', true, null,'Aleksa', '122133', '0669087659', 'picture 8', 'Stevanovic', true, null, 1);

INSERT INTO public.driver(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, available, current_location, vehicle_id)
VALUES (9, false, 'Novi Sad', false, 'zeka_12@gmail.com', true, null,'Petar', '122133', '0669080095', 'picture 9', 'Zekic', true, null, 2);
INSERT INTO public.driver(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, available, current_location, vehicle_id)
VALUES (10, false, 'Novi Sad', false, 'kuzmanara@gmail.com', true, null,'Marko', '122133', '0669123095', 'picture 10', 'Kuzman', true, null, 3);
INSERT INTO public.driver(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, available, current_location, vehicle_id)
VALUES (11, false, 'Novi Sad', false, 'lazarpavlovic@gmail.com', true, null,'Lazar', '122133', '0602384095', 'picture 11', 'Pavlovic', false, null, 4);
INSERT INTO public.driver(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, available, current_location, vehicle_id)
VALUES (12, false, 'Novi Sad', false, 'mirkomirko@gmail.com', true, null,'Mirko', '122133', '06000394095', 'picture 12', 'Markovic', false, null, 5);
INSERT INTO public.driver(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, available, current_location, vehicle_id)
VALUES (13, false, 'Novi Sad', false, 'uspavanalepotica@gmail.com', true, null,'Uspavana', '122133', '069696969699', 'picture 69', 'Lepotica', true, null, 6);





INSERT INTO public.role(
    id, name)
VALUES (1, 'ROLE_ADMIN');

INSERT INTO public.role(
    id, name)
VALUES (2, 'ROLE_CUSTOMER');

INSERT INTO public.role(
    id, name)
VALUES (3, 'DRIVER');



INSERT INTO public.user_role(
    user_id, role_id)
VALUES (1, 2);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (2, 2);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (3, 2);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (4, 2);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (5, 2);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (6, 2);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (7, 2);


INSERT INTO public.user_role(
    user_id, role_id)
VALUES (8, 3);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (9, 3);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (10, 3);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (11, 3);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (12, 3);
INSERT INTO public.user_role(
    user_id, role_id)
VALUES (13, 3);







INSERT INTO public.message(
    id, date, read, text, recipient_id, sender_id)
VALUES (1, '2022-11-01', true, 'Ova majmuncina od vozaca ne dolazi sta se desava?', 1, 3);

INSERT INTO public.message(
    id, date, read, text, recipient_id, sender_id)
VALUES (2, '2022-11-01', true, 'Odmah cemo proveriti u cemu je problem', 3, 1);

INSERT INTO public.message(
    id, date, read, text, recipient_id, sender_id)
VALUES (3, '2022-11-01', true, 'Pozurite moram u WC. Ako mu se uneredim po kolima nije moja krivica pozzz', 1, 3);

INSERT INTO public.message(
    id, date, read, text, recipient_id, sender_id)
VALUES (4, '2022-11-01', true, 'Molim vas da ne cinite to. Stvarno bi biloj jako neprijatno', 3, 1);

INSERT INTO public.message(
    id, date, read, text, recipient_id, sender_id)
VALUES (5, '2022-11-01', true, 'Nije do mene druze eksploditracu, a mogu to da obavim samo kuci i u Pogonu', 1, 3);

INSERT INTO public.message(
    id, date, read, text, recipient_id, sender_id)
VALUES (6, '2022-11-01', false, 'Razumem vas u potpunosti. Pogon zaista ima lep WC. Ucinicu sve sto je u mojoj moci da vozilo dodje sto pre', 3, 1);




INSERT INTO public.notification(
    id, date, read, text, type, recipient_id)
VALUES (1, '2022-11-11', false, 'vozilo je stiglo ', 'INFORMATIONAL', 4);

INSERT INTO public.notification(
    id, date, read, text, type, recipient_id)
VALUES (2, '2022-11-11', false, 'vozilo je stiglo ', 'INFORMATIONAL', 5);
INSERT INTO public.notification(
    id, date, read, text, type, recipient_id)
VALUES (3, '2021-11-12', false, 'vozilo je stiglo ', 'INFORMATIONAL', 2);
INSERT INTO public.notification(
    id, date, read, text, type, recipient_id)
VALUES (4, '2021-12-11', false, 'vozilo je stiglo ', 'INFORMATIONAL', 1);
INSERT INTO public.notification(
    id, date, read, text, type, recipient_id)
VALUES (5, '2022-10-10', false, 'vozilo je stiglo ', 'INFORMATIONAL', 1);



INSERT INTO public.rejection(
    id, reason)
VALUES (1, 'Boli me glava');

INSERT INTO public.rejection(
    id, reason)
VALUES (2, 'Crko auto');

INSERT INTO public.rejection(
    id, reason)
VALUES (3, 'Daleko mi da se njakam do Klise');




INSERT INTO public.report(
    id, comment, recipient_id, reporter_id)
VALUES (1, 'Odlican batica ', 9, 2);

INSERT INTO public.report(
    id, comment, recipient_id, reporter_id)
VALUES (2, 'Jaako smara lik i prica sta radi ribama kad nemaju da plate. Teska seljacina', 8, 3);

INSERT INTO public.report(
    id, comment, recipient_id, reporter_id)
VALUES (3, 'Odlican,brz,zabavan i pouzdan. ', 10, 1);

INSERT INTO public.report(
    id, comment, recipient_id, reporter_id)
VALUES (4, 'Kraljina prica sta radi kamenjarkama kad nemaju da plate. Ziva legenda lik', 8, 1);





INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (1,4, 'divno iskustvo', 5, 8, 2,null);

INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (2,3, 'Ocajan lik', 1, 8, 3,null);

INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (3,5, 'divno iskustvo', 4, 8, 1,null);

INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (4,3, 'Carina teska', 5, 8, 6,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (5,2, 'Lik teski bolid manijak', 2, 8, 5,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (6,3, 'Prosecno. Sporo vozi lik i smara al nisam imala da platim pa smo se dogovorili', 3, 8, 4,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (7,5, 'divno iskustvo', 5, 9, 2,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (8,4, 'Odlican vozac!', 5, 9, 3,null);

INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (9,3, 'Solidno.', 3, 9, 5,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (10,5, 'Doktorcina brza', 5, 10, 2,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (11, 4,'Lik vozi ko divljak. Tri puta za malo da umremo', 2, 10, 3,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (12,2, 'Trauma za ceo zivot', 1, 10, 4,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (13,4, 'Jako brzo smo stigli svaka cast', 5, 10, 5,null);
INSERT INTO public.review(
    id, car_rating, comment, driver_rating, recipient_id, reviewer_id, ride_id)
VALUES (14,2, 'Prosecno. Malo smrde kola al OK', 3, 11, 2,null);





INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 1, null, null, null, null, null);

INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 2, null, null, null, null, null);
INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 3, null, null, null, null, null);
INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 4, null, null, null, null, null);
INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 5, null, null, null, null, null);
INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 6, null, null, null, null, null);
INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 7, null, null, null, null, null);
INSERT INTO public.route(
    dtype, id, distance_in_km, estimated_time_in_minutes, name, start, customer_id)
VALUES (1, 8, null, null, null, null, null);


INSERT INTO public.stops(
    route_id, stops)
VALUES (1, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (1, 'Nigde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (2, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (2, 'Nigde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (3, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (3, 'Nigde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (4, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (4, 'Nigde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (5, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (5, 'Nigde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (6, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (6, 'Nigde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (7, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (7, 'Nigde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (8, 'Negde');
INSERT INTO public.stops(
    route_id, stops)
VALUES (8, 'Nigde');

INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (1, 540, false, false, '2022-10-10 12:10', 'FINISHED', 'INSTANT', 'ULTRA_PREMIUM', 1);

INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (2, 242, false, true, '2022-10-11 17:10', 'FINISHED', 'INSTANT', 'REGULAR', 2);

INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (3, 520, false, false, '2022-10-11 11:54', 'FINISHED', 'SCHEDULED', 'REGULAR', 3);
INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (4, 820, true, true, '2022-10-12 10:30', 'FINISHED', 'INSTANT', 'PREMIUM', 4);
INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (5, 1120, false, false, '2022-10-13 11:29', 'FINISHED', 'INSTANT', 'REGULAR', 5);
INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (6, 1420, true, false, '2022-10-14 19:12', 'FINISHED', 'SCHEDULED', 'ULTRA_PREMIUM', 6);
INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (7, 920, false, false, '2022-10-14 22:12', 'FINISHED', 'INSTANT', 'PREMIUM', 7);
INSERT INTO public.reservation(
    id, estimated_cost, has_baby, has_pet, reservation_time, status, type, vehicle_type, route_id)
VALUES (8, 400, false, true, '2022-10-14 11:43', 'FINISHED', 'SCHEDULED', 'REGULAR', 8);



INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (1, '2022-10-10 12:30', 7, '2022-10-10 12:17', 'FINISHED',null ,null, 1);

INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (2, '2022-10-11 17:30', 4, '2022-10-11 17:15', 'FINISHED',null ,null, 2);
INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (3, '2022-10-11 12:13', 10, '2022-10-11 11:59', 'FINISHED',null ,null, 3);
INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (4, '2022-10-12 10:42', 3, '2022-10-12 10:33', 'FINISHED',null ,null, 4);
INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (5,  '2022-10-13 11:51', 11,  '2022-10-13 11:37', 'FINISHED',null ,null, 5);
INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (6, '2022-10-14 19:26', 5, '2022-10-14 19:18', 'FINISHED',null ,null, 6);
INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (7, '2022-10-14 22:32', 7, '2022-10-14 22:19', 'FINISHED',null ,null, 7);
INSERT INTO public.ride(
    id, end_time, estimated_arrival_time_in_minutes, start_time, status, driver_id, rejection_id, reservation)
VALUES (8, '2022-10-14 12:10', 8, '2022-10-14 11:52', 'FINISHED',null ,null, 8);


INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (1, 1);


INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (1, 2);

INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (2, 3);


INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (2, 4);

INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (3, 5);


INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (3, 6);
INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (4, 7);


INSERT INTO public.rides(
    customer_id, ride_id)
VALUES (5, 8);
















