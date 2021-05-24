CREATE TABLE public.car
(
    id                  bigserial             NOT NULL,
    registration character varying(20) NOT NULL,
    including_personal_use       boolean               NOT NULL DEFAULT false,
    PRIMARY KEY (id)
);
