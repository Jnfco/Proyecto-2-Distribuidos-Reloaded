PGDMP         8                x           Distribuidor2    12.2    12.2                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    24584    Distribuidor2    DATABASE     �   CREATE DATABASE "Distribuidor2" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Latin America.1252' LC_CTYPE = 'Spanish_Latin America.1252';
    DROP DATABASE "Distribuidor2";
                postgres    false            �            1259    24587    distribuidor    TABLE     �   CREATE TABLE public.distribuidor (
    iddistribuidor integer NOT NULL,
    preciodiesel numeric(6,2),
    preciokerosene numeric(6,2),
    precio93 numeric(6,2),
    precio95 numeric(6,2),
    precio97 numeric(6,2),
    factorutilidad numeric(3,1)
);
     DROP TABLE public.distribuidor;
       public         heap    postgres    false            �            1259    24585    distribuidor_iddistribuidor_seq    SEQUENCE     �   ALTER TABLE public.distribuidor ALTER COLUMN iddistribuidor ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.distribuidor_iddistribuidor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    203            �            1259    24594    surtidor    TABLE     �   CREATE TABLE public.surtidor (
    idsurtidor integer NOT NULL,
    iddistribuidor integer,
    tipocombustible character varying(11),
    preciolitro numeric(6,2)
);
    DROP TABLE public.surtidor;
       public         heap    postgres    false            �            1259    24592    surtidor_idsurtidor_seq    SEQUENCE     �   ALTER TABLE public.surtidor ALTER COLUMN idsurtidor ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.surtidor_idsurtidor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    205            �            1259    24601    venta    TABLE     �   CREATE TABLE public.venta (
    idsurtidor integer,
    cantidadlitros numeric(3,1),
    valorventa numeric(7,2),
    idventa integer NOT NULL,
    precioactual numeric(6,2),
    fecha timestamp without time zone
);
    DROP TABLE public.venta;
       public         heap    postgres    false            �            1259    24599    venta_idventa_seq    SEQUENCE     �   ALTER TABLE public.venta ALTER COLUMN idventa ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.venta_idventa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    207                      0    24587    distribuidor 
   TABLE DATA                 public          postgres    false    203                    0    24594    surtidor 
   TABLE DATA                 public          postgres    false    205   �                 0    24601    venta 
   TABLE DATA                 public          postgres    false    207   g                  0    0    distribuidor_iddistribuidor_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.distribuidor_iddistribuidor_seq', 1, true);
          public          postgres    false    202                       0    0    surtidor_idsurtidor_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.surtidor_idsurtidor_seq', 3, true);
          public          postgres    false    204                       0    0    venta_idventa_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.venta_idventa_seq', 3, true);
          public          postgres    false    206            �
           2606    24591    distribuidor distribuidor_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.distribuidor
    ADD CONSTRAINT distribuidor_pkey PRIMARY KEY (iddistribuidor);
 H   ALTER TABLE ONLY public.distribuidor DROP CONSTRAINT distribuidor_pkey;
       public            postgres    false    203            �
           2606    24598    surtidor surtidor_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.surtidor
    ADD CONSTRAINT surtidor_pkey PRIMARY KEY (idsurtidor);
 @   ALTER TABLE ONLY public.surtidor DROP CONSTRAINT surtidor_pkey;
       public            postgres    false    205               �   x�U�A� ����︁cXD� 	��t��࣑����06��>��Je�nA���)#���"]�P��m
S��C?�q���a����ތov���m
1'�����ZˋTW0Oӊ;t��C,k��Q��U�Qد�9��YU�'B��>[         �   x���M
�0�}O�vm!��hW�A�BSݦ��a4%?��z��0��>�������9J�S����պ	�R�C�8��~e���f�'���%��:~���(z����|����%�B���	������f|�>y>�Y�z�鶢C]��R��,��V��         �   x���=�0�὿�6+�rI��1		hmcZ1PZI?~�����&.w/���R�d �,�[w.���j�&o:ۚ����\��4���*k��#`�W�l�M�t۩����W5��(�Dnd����fb��� ���O	P�@��w��C�2� �`t��lIi0et4^y��vF�=��Mw������tϻ�{��     