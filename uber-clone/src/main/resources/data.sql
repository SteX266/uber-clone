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
