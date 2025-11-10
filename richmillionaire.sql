--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5 (Ubuntu 17.5-1.pgdg24.04+1)
-- Dumped by pg_dump version 17.5 (Ubuntu 17.5-1.pgdg24.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: hugo
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO hugo;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: hugo
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: article_categories; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.article_categories (
    article_id uuid NOT NULL,
    category_id uuid NOT NULL
);


ALTER TABLE public.article_categories OWNER TO hugo;

--
-- Name: articles; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.articles (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    description character varying(1000),
    name character varying(255) NOT NULL,
    photo_url text,
    price double precision NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.articles OWNER TO hugo;

--
-- Name: block; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.block (
    id uuid NOT NULL,
    curr_hash character varying(255) NOT NULL,
    data character varying(255) NOT NULL,
    mined_by character varying(255) NOT NULL,
    nonce integer NOT NULL,
    previous_hash character varying(255) NOT NULL,
    time_stamp bigint NOT NULL
);


ALTER TABLE public.block OWNER TO hugo;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.categories (
    id uuid NOT NULL,
    description character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.categories OWNER TO hugo;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.roles (
    id uuid NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.roles OWNER TO hugo;

--
-- Name: transactions; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.transactions (
    id uuid NOT NULL,
    from_address oid NOT NULL,
    from_string character varying(2048) NOT NULL,
    signature oid,
    signature_string character varying(2048) NOT NULL,
    "timestamp" character varying(255) NOT NULL,
    to_address oid NOT NULL,
    to_string character varying(2048) NOT NULL,
    value integer NOT NULL,
    block_id uuid NOT NULL
);


ALTER TABLE public.transactions OWNER TO hugo;

--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.user_roles (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);


ALTER TABLE public.user_roles OWNER TO hugo;

--
-- Name: users; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO hugo;

--
-- Name: wallets; Type: TABLE; Schema: public; Owner: hugo
--

CREATE TABLE public.wallets (
    id uuid NOT NULL,
    balance double precision NOT NULL,
    private_key character varying(2048) NOT NULL,
    public_key character varying(2048) NOT NULL
);


ALTER TABLE public.wallets OWNER TO hugo;

--
-- Name: 33249..33250; Type: BLOB METADATA; Schema: -; Owner: hugo
--

SELECT pg_catalog.lo_create('33249');
SELECT pg_catalog.lo_create('33250');

ALTER LARGE OBJECT 33249 OWNER TO hugo;
ALTER LARGE OBJECT 33250 OWNER TO hugo;

--
-- Data for Name: article_categories; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.article_categories (article_id, category_id) FROM stdin;
17196381-e88d-4fad-8394-e011077b572b	d2bcd01e-2fa9-48cf-b1c1-b97de3222c2c
12a0a953-9226-4cb4-85b8-98034b585141	d2bcd01e-2fa9-48cf-b1c1-b97de3222c2c
\.


--
-- Data for Name: articles; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.articles (id, created_at, description, name, photo_url, price, updated_at) FROM stdin;
17196381-e88d-4fad-8394-e011077b572b	2025-11-08 20:48:05.300343	super coca	coca	upload/articles/17196381-e88d-4fad-8394-e011077b572b/image_1762707593443.png	99.99	2025-11-09 17:59:53.45723
12a0a953-9226-4cb4-85b8-98034b585141	2025-11-09 18:32:16.887869	string	string	upload/articles/12a0a953-9226-4cb4-85b8-98034b585141.png	0.1	2025-11-09 18:32:16.898491
\.


--
-- Data for Name: block; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.block (id, curr_hash, data, mined_by, nonce, previous_hash, time_stamp) FROM stdin;
\.


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.categories (id, description, name) FROM stdin;
d2bcd01e-2fa9-48cf-b1c1-b97de3222c2c	boisson fraiche ou pas	boisson
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.roles (id, name) FROM stdin;
\.


--
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.transactions (id, from_address, from_string, signature, signature_string, "timestamp", to_address, to_string, value, block_id) FROM stdin;
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.user_roles (user_id, role_id) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.users (id, created_at, email, password, updated_at, username) FROM stdin;
6859268e-2f14-4b25-8619-d85b8443c1e2	2025-10-14 11:41:06.872452	string	$2a$10$82ET8xsrZ4A/xK467sTA3uFGELV0.8sgeM6gVK4pO5vXMEZpeB55C	2025-10-14 11:41:06.872452	string
1f637c1d-51a8-42ed-aa01-9107ac3264f8	2025-11-07 20:34:25.971809	kikipaul	$2a$10$9nTC.OyRGw5s6tsNXvPTzeq10S7K62k/Xn525e1IHkeLTFKueC0Aq	2025-11-07 20:34:25.971809	kikipaul
a8203eec-e06c-43e5-836a-806f207af33c	2025-11-07 20:45:39.600081	lucille	$2a$10$Coxpr5Hlw8/h3JnOY1.pee3YtCifemNxJElUS6.kKP78jzVort.6e	2025-11-07 20:45:39.600081	lucille
\.


--
-- Data for Name: wallets; Type: TABLE DATA; Schema: public; Owner: hugo
--

COPY public.wallets (id, balance, private_key, public_key) FROM stdin;
\.


--
-- Data for Name: 33249..33250; Type: BLOBS; Schema: -; Owner: hugo
--

BEGIN;

SELECT pg_catalog.lo_open('33249', 131072);
SELECT pg_catalog.lowrite(0, '\x68747470733a2f2f7777772e676f6f676c652e636f6d2f696d677265733f713d63616e65747465253230636f636126696d6775726c3d68747470732533412532462532467777772e6c65636861697072756c696572652e667225324677702d636f6e74656e7425324675706c6f616473253246323032312532463031253246636f63612d636f6c612e6a706726696d6772656675726c3d68747470732533412532462532467777772e6c65636861697072756c696572652e667225324670726f64756974253246636f63612d6f6c612d33332d636c25324626646f6369643d36673930425f5138577149786c4d2674626e69643d384276766f3836414c7a665f444d267665743d31326168554b45776a3133376246357543514178565a62454541486651474b674d514d336f454343595141412e2e6926773d36303026683d363030266863623d32267665643d326168554b45776a3133376246357543514178565a62454541486651474b674d514d336f45434359514141');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('33250', 131072);
SELECT pg_catalog.lowrite(0, '\x68747470733a2f2f7777772e676f6f676c652e636f6d2f696d677265733f713d63616e65747465253230636f636126696d6775726c3d68747470732533412532462532467777772e6c65636861697072756c696572652e667225324677702d636f6e74656e7425324675706c6f616473253246323032312532463031253246636f63612d636f6c612e6a706726696d6772656675726c3d68747470732533412532462532467777772e6c65636861697072756c696572652e667225324670726f64756974253246636f63612d6f6c612d33332d636c25324626646f6369643d36673930425f5138577149786c4d2674626e69643d384276766f3836414c7a665f444d267665743d31326168554b45776a3133376246357543514178565a62454541486651474b674d514d336f454343595141412e2e6926773d36303026683d363030266863623d32267665643d326168554b45776a3133376246357543514178565a62454541486651474b674d514d336f45434359514141');
SELECT pg_catalog.lo_close(0);

COMMIT;

--
-- Name: article_categories article_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.article_categories
    ADD CONSTRAINT article_categories_pkey PRIMARY KEY (article_id, category_id);


--
-- Name: articles articles_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.articles
    ADD CONSTRAINT articles_pkey PRIMARY KEY (id);


--
-- Name: block block_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.block
    ADD CONSTRAINT block_pkey PRIMARY KEY (id);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: transactions transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: block ukanp4lh0i1kmpnsxi7ymnxrkjm; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.block
    ADD CONSTRAINT ukanp4lh0i1kmpnsxi7ymnxrkjm UNIQUE (curr_hash);


--
-- Name: roles ukofx66keruapi6vyqpv6f2or37; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT ukofx66keruapi6vyqpv6f2or37 UNIQUE (name);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: categories ukt8o6pivur7nn124jehx7cygw5; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT ukt8o6pivur7nn124jehx7cygw5 UNIQUE (name);


--
-- Name: user_roles user_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: wallets wallets_pkey; Type: CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.wallets
    ADD CONSTRAINT wallets_pkey PRIMARY KEY (id);


--
-- Name: article_categories fk8lwbpiccld0fsuhw5xab62vl0; Type: FK CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.article_categories
    ADD CONSTRAINT fk8lwbpiccld0fsuhw5xab62vl0 FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- Name: user_roles fkh8ciramu9cc9q3qcqiv4ue8a6; Type: FK CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: user_roles fkhfh9dx7w3ubf1co1vdev94g3f; Type: FK CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: article_categories fkiaa1jged75v2k1r2ongjm9hko; Type: FK CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.article_categories
    ADD CONSTRAINT fkiaa1jged75v2k1r2ongjm9hko FOREIGN KEY (article_id) REFERENCES public.articles(id);


--
-- Name: transactions fkofk62difnkhucoyyjf9qtjg1t; Type: FK CONSTRAINT; Schema: public; Owner: hugo
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT fkofk62difnkhucoyyjf9qtjg1t FOREIGN KEY (block_id) REFERENCES public.block(id);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: hugo
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


--
-- PostgreSQL database dump complete
--

