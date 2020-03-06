-- Table: public.distribuidor

-- DROP TABLE public.distribuidor;

CREATE TABLE public.distribuidor
(
    iddistribuidor integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    preciodiesel numeric(6,2),
    preciokerosene numeric(6,2),
    precio93 numeric(6,2),
    precio95 numeric(6,2),
    precio97 numeric(6,2),
    factorutilidad numeric(3,1),
    fecha timestamp without time zone,
    CONSTRAINT distribuidor_pkey PRIMARY KEY (iddistribuidor)
)

TABLESPACE pg_default;

ALTER TABLE public.distribuidor
    OWNER to postgres;

    -- Table: public.surtidor

-- DROP TABLE public.surtidor;

CREATE TABLE public.surtidor
(
    idsurtidor integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    iddistribuidor integer,
    tipocombustible character varying(11) COLLATE pg_catalog."default",
    preciolitro numeric(6,2),
    CONSTRAINT surtidor_pkey PRIMARY KEY (idsurtidor)
)

TABLESPACE pg_default;

ALTER TABLE public.surtidor
    OWNER to postgres;

    -- Table: public.venta

-- DROP TABLE public.venta;

CREATE TABLE public.venta
(
    idsurtidor integer,
    cantidadlitros numeric(3,1),
    valorventa numeric(7,2),
    idventa integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    precioactual numeric(6,2),
    fecha timestamp without time zone
)

TABLESPACE pg_default;

ALTER TABLE public.venta
    OWNER to postgres;