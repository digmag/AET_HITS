PGDMP                      |            AET_HITS    15.7    16.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16398    AET_HITS    DATABASE     ~   CREATE DATABASE "AET_HITS" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "AET_HITS";
                postgres    false            �            1259    16399    databasechangelog    TABLE     Y  CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);
 %   DROP TABLE public.databasechangelog;
       public         heap    postgres    false            �            1259    16404    databasechangeloglock    TABLE     �   CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);
 )   DROP TABLE public.databasechangeloglock;
       public         heap    postgres    false            �            1259    16414    employee    TABLE       CREATE TABLE public.employee (
    id uuid NOT NULL,
    fullname character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    status uuid NOT NULL,
    admin boolean NOT NULL,
    verification boolean NOT NULL
);
    DROP TABLE public.employee;
       public         heap    postgres    false            �            1259    16440    recovery_message    TABLE     {   CREATE TABLE public.recovery_message (
    id uuid NOT NULL,
    employee_id uuid NOT NULL,
    is_end boolean NOT NULL
);
 $   DROP TABLE public.recovery_message;
       public         heap    postgres    false            �            1259    16428    token    TABLE     r   CREATE TABLE public.token (
    id uuid NOT NULL,
    token_value text NOT NULL,
    employee_id uuid NOT NULL
);
    DROP TABLE public.token;
       public         heap    postgres    false            �            1259    16409    work_status    TABLE     f   CREATE TABLE public.work_status (
    id uuid NOT NULL,
    status character varying(255) NOT NULL
);
    DROP TABLE public.work_status;
       public         heap    postgres    false                      0    16399    databasechangelog 
   TABLE DATA           �   COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
    public          postgres    false    214   �                 0    16404    databasechangeloglock 
   TABLE DATA           R   COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
    public          postgres    false    215   �                 0    16414    employee 
   TABLE DATA           ^   COPY public.employee (id, fullname, email, password, status, admin, verification) FROM stdin;
    public          postgres    false    217   �                 0    16440    recovery_message 
   TABLE DATA           C   COPY public.recovery_message (id, employee_id, is_end) FROM stdin;
    public          postgres    false    219   �                 0    16428    token 
   TABLE DATA           =   COPY public.token (id, token_value, employee_id) FROM stdin;
    public          postgres    false    218   �                 0    16409    work_status 
   TABLE DATA           1   COPY public.work_status (id, status) FROM stdin;
    public          postgres    false    216   �       y           2606    16408 0   databasechangeloglock databasechangeloglock_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.databasechangeloglock DROP CONSTRAINT databasechangeloglock_pkey;
       public            postgres    false    215            }           2606    16422    employee employee_email_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_email_key UNIQUE (email);
 E   ALTER TABLE ONLY public.employee DROP CONSTRAINT employee_email_key;
       public            postgres    false    217                       2606    16420    employee employee_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.employee DROP CONSTRAINT employee_pkey;
       public            postgres    false    217            �           2606    16444 &   recovery_message recovery_message_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.recovery_message
    ADD CONSTRAINT recovery_message_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.recovery_message DROP CONSTRAINT recovery_message_pkey;
       public            postgres    false    219            �           2606    16434    token token_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.token DROP CONSTRAINT token_pkey;
       public            postgres    false    218            {           2606    16413    work_status work_status_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.work_status
    ADD CONSTRAINT work_status_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.work_status DROP CONSTRAINT work_status_pkey;
       public            postgres    false    216            �           2606    16435    token FK_employee_Token    FK CONSTRAINT        ALTER TABLE ONLY public.token
    ADD CONSTRAINT "FK_employee_Token" FOREIGN KEY (employee_id) REFERENCES public.employee(id);
 C   ALTER TABLE ONLY public.token DROP CONSTRAINT "FK_employee_Token";
       public          postgres    false    3199    217    218            �           2606    16423    employee FK_employee_status    FK CONSTRAINT     �   ALTER TABLE ONLY public.employee
    ADD CONSTRAINT "FK_employee_status" FOREIGN KEY (status) REFERENCES public.work_status(id);
 G   ALTER TABLE ONLY public.employee DROP CONSTRAINT "FK_employee_status";
       public          postgres    false    217    216    3195               �  x����n�0E��W�"�/����g ��@�!9t�PL�}Y
\�v�\0s��=��	�>Az��]�KqȾ�`_�ϻ�x{힡�b�m���t�Uy[c1@SN�̩ʩΘZ�r)MABFV?W�?֫;b�Ny���
�BX�A2Ƃ	T���q�#4x���t+yz$�ઠ���ӺdZV���t6/u7 ^�k�}��,� �8�v���R���8T�Z��2��f�ox�E�m�n��Ovm�,��_�W�����7���"��:���%��=��bE�2ԜN�1��o�DjAi��/�2R�p��������և����X_@�u�0�қuy�}�s+�8��%*ςVr�
��u`ZU�B��	���f�<��X, *k^9            x�3�L��"�=... U�         �   x��=N�0@g����7�7~$��@+�.�BD4-�̈́@�� ZU*���FXoyO�dԙ!8�%��,�81B�l�1��~����nG���;�±{�C~��?��!��_���r���ۛ������G1S1%�%���mOI�huU�t=���Yژ����|:!y.���^_�K㊇�n�M4�+�	�`΍h���4S�K�H��*���(���T�            x������ � �            x������ � �         J   x�? ��e3c1414a-7893-44da-90c2-c9c17ac49c39	Должность 1
\.


��t     