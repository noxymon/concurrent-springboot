--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.18
-- Dumped by pg_dump version 12.2 (Ubuntu 12.2-4)

-- Started on 2020-06-05 09:44:27 WIB

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: master
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO master;

--
-- TOC entry 2133 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: master
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

--
-- TOC entry 185 (class 1259 OID 42404)
-- Name: usage_counter; Type: TABLE; Schema: public; Owner: master
--

CREATE TABLE public.usage_counter (
    master_id bigint NOT NULL,
    max_counter integer,
    usage integer,
    version integer
);


ALTER TABLE public.usage_counter OWNER TO master;

--
-- TOC entry 186 (class 1259 OID 42407)
-- Name: usage_detail; Type: TABLE; Schema: public; Owner: master
--

CREATE TABLE public.usage_detail (
    master_id bigint NOT NULL,
    transcation_id character varying NOT NULL
);


ALTER TABLE public.usage_detail OWNER TO master;

--
-- TOC entry 2126 (class 0 OID 42404)
-- Dependencies: 185
-- Data for Name: usage_counter; Type: TABLE DATA; Schema: public; Owner: master
--

COPY public.usage_counter (master_id, max_counter, usage, version) FROM stdin;
\.


--
-- TOC entry 2127 (class 0 OID 42407)
-- Dependencies: 186
-- Data for Name: usage_detail; Type: TABLE DATA; Schema: public; Owner: master
--

COPY public.usage_detail (master_id, transcation_id) FROM stdin;
\.


--
-- TOC entry 2006 (class 2606 OID 42414)
-- Name: usage_counter usage_counter_pk; Type: CONSTRAINT; Schema: public; Owner: master
--

ALTER TABLE ONLY public.usage_counter
    ADD CONSTRAINT usage_counter_pk PRIMARY KEY (master_id);


--
-- TOC entry 2008 (class 2606 OID 42416)
-- Name: usage_detail usage_detail_pk; Type: CONSTRAINT; Schema: public; Owner: master
--

ALTER TABLE ONLY public.usage_detail
    ADD CONSTRAINT usage_detail_pk PRIMARY KEY (master_id, transcation_id);


-- Completed on 2020-06-05 09:44:27 WIB

--
-- PostgreSQL database dump complete
--

