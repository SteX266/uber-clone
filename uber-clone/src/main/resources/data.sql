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



INSERT INTO public.role(
    id, name)
VALUES (1, 'ADMIN');

INSERT INTO public.role(
    id, name)
VALUES (2, 'CLIENT');

INSERT INTO public.role(
    id, name)
VALUES (3, 'DRIVER');


INSERT INTO public.customer(
    id, banned, city, deleted, email, enabled, last_password_reset_date, name, password, phone_number, profile_picture, surname, riding)
VALUES (1, false, 'Novi Sad', false, 'serfezev@gmail.com', true, null, 'Vanja', '$2a$10$YlFmOPcg9JpJnTPQsJhsaO4t0MH/KAtWPD3dvWW2MO0ddBAkl4LVC', '0665241322','picture 1', 'Serfeze', false);


INSERT INTO public.user_role(
    user_id, role_id)
VALUES (1, 2);
