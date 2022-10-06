--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

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
-- Name: appeals_details(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.appeals_details(myid integer) RETURNS TABLE(address character varying, emp character varying, man character varying, client character varying, com text, task_date date, task_time character varying)
    LANGUAGE sql
    AS $$
SELECT a.city||', '||a.street||', '||a.build||', ╨┐. '||a.ent||', ╨║╨▓. '||ap.appart as "╨Р╨┤╤А╨╡╤Б",
		e.last_name||' '||e.first_name||' '||e.up_name as "╨Ь╨░╤Б╤В╨╡╤А",
		me.last_name||' '||me.first_name||' '||me.up_name as "╨Ф╨╕╤Б╨┐╨╡╤В╤З╨╡╤А",
		c.last_name||' '||c.first_name||' '||c.up_name||', ╤В╨╡╨╗. '||c.phone as "╨Ъ╨╗╨╕╨╡╨╜╤В",
		ap.comment, ap.task_date, t.time
FROM public.appeals ap
join public.addresses a
on a.id = ap.address_id
join public.employees e
on e.id = ap.employee_id
join public.employees me 
on me.id = ap.manager_id
join public.clients c
on c.id = ap.client_id
join public.times t
on t.id = ap.time_id
where ap.id = myid and is_done = false and ap.employee_id is not null;
$$;


ALTER FUNCTION public.appeals_details(myid integer) OWNER TO postgres;

--
-- Name: check_client(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_client() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
        IF NEW.last_name IS NULL THEN
            RAISE EXCEPTION '╨Э╨╡╨╛╨▒╤Е╨╛╨┤╨╕╨╝╨╛ ╨▓╨▓╨╡╤Б╤В╨╕ ╤Д╨░╨╝╨╕╨╗╨╕╤О ╨║╨╗╨╕╨╡╨╜╤В╨░';
        END IF;
		IF NEW.first_name IS NULL THEN
            RAISE EXCEPTION '╨Э╨╡╨╛╨▒╤Е╨╛╨┤╨╕╨╝╨╛ ╨▓╨▓╨╡╤Б╤В╨╕ ╨╕╨╝╤П ╨║╨╗╨╕╨╡╨╜╤В╨░';
        END IF;
        IF NEW.phone IS NULL THEN
            RAISE EXCEPTION '╨Э╨╡╨╛╨▒╤Е╨╛╨┤╨╕╨╝╨╛ ╨▓╨▓╨╡╤Б╤В╨╕ ╤В╨╡╨╗╨╡╤Д╨╛╨╜ ╨║╨╗╨╕╨╡╨╜╤В╨░';
        END IF;
        RETURN NEW;
    END;
$$;


ALTER FUNCTION public.check_client() OWNER TO postgres;

--
-- Name: check_done_appeal(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_done_appeal() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
        IF NEW.is_done IS NULL THEN
            RAISE EXCEPTION '╨У╨╛╤В╨╛╨▓╨╜╨╛╤Б╤В╤М ╨╖╨░╤П╨▓╨║╨╕ ╨┤╨╛╨╗╨╢╨╜╨░ ╨▒╤Л╤В╤М ╨╛╨┐╤А╨╡╨┤╨╡╨╗╨╡╨╜╨░';
        END IF;
        RETURN NEW;
    END;
$$;


ALTER FUNCTION public.check_done_appeal() OWNER TO postgres;

--
-- Name: check_taskdate(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_taskdate() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
        IF NEW.task_date <= current_date THEN
            RAISE EXCEPTION '╨Ф╨░╤В╨░ ╨▓╨╕╨╖╨╕╤В╨░ ╨┤╨╛╨╗╨╢╨╜╨░ ╨▒╤Л╤В╤М ╨┐╨╛╨╖╨╢╨╡ ╤В╨╡╨║╤Г╤Й╨╡╨╣ ╨┤╨░╤В╤Л: %', current_date;
        END IF;
        RETURN NEW;
    END;
$$;


ALTER FUNCTION public.check_taskdate() OWNER TO postgres;

--
-- Name: contract_details(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.contract_details(nd integer) RETURNS TABLE("╨Э╨╛╨╝╨╡╤А ╨┤╨╛╨│╨╛╨▓╨╛╤А╨░" character varying, "╨Ш╨╜╤В╨╡╤А╨╜╨╡╤В" boolean, "╨в╨Т" boolean, "╨в╨╡╨╗╨╡╤Д╨╛╨╜" boolean, "╨Р╨║╤В╨╕╨▓╨╜╤Л╨╣ ╨┤╨╛╨│╨╛╨▓╨╛╤А" boolean, "╨Ъ╨╗╨╕╨╡╨╜╤В" character varying, "╨Р╨┤╤А╨╡╤Б" character varying)
    LANGUAGE sql
    AS $$
SELECT co.id, co.has_internet, co.has_tv, co.has_phone, co.is_active, 
		cl.last_name||' '||cl.first_name||' '||cl.up_name||', ╤В╨╡╨╗. '||cl.phone as "╨Ъ╨╗╨╕╨╡╨╜╤В",
		a.city||', '||a.street||', '||a.build||', ╨┐. '||a.ent||', ╨║╨▓. '||co.appartments as "╨Р╨┤╤А╨╡╤Б"
FROM public.contracts co
join public.clients cl
on cl.id = co.client_id
join public.addresses a
on a.id = co.address_id
where co.id = nd;
$$;


ALTER FUNCTION public.contract_details(nd integer) OWNER TO postgres;

--
-- Name: count_appeals_group(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.count_appeals_group() RETURNS TABLE("╨Ь╨░╤Б╤В╨╡╤А" character varying, "╨Ъ╨╛╨╗╨╕╤З╨╡╤Б╤В╨▓╨╛ ╨╖╨░╤П╨▓╨╛╨║" integer)
    LANGUAGE sql
    AS $$
select e.last_name||' '||e.first_name||' '||e.up_name as "╨Ь╨░╤Б╤В╨╡╤А", count(a.id) as "╨Ъ╨╛╨╗╨╕╤З╨╡╤Б╤В╨▓╨╛"
from public.appeals a
join public.employees e
on e.id = a.employee_id
where is_done = false
group by "╨Ь╨░╤Б╤В╨╡╤А";
$$;


ALTER FUNCTION public.count_appeals_group() OWNER TO postgres;

--
-- Name: count_appeals_group(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.count_appeals_group(myname character varying) RETURNS TABLE("╨Ъ╨╗╨╕╨╡╨╜╤В" character varying, "╨в╨╡╨╗╨╡╤Д╨╛╨╜" character varying, "Email" character varying)
    LANGUAGE sql
    AS $$
select * from 
	(select last_name||' '||first_name||' '||up_name as client_name, phone, email
	from public.clients) as d
where client_name ilike '%'||myname||'%';
$$;


ALTER FUNCTION public.count_appeals_group(myname character varying) OWNER TO postgres;

--
-- Name: done_appeal(integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.done_appeal(IN appeal_id integer)
    LANGUAGE sql
    AS $$
update public.appeals
set is_done = true
where id = appeal_id;
$$;


ALTER PROCEDURE public.done_appeal(IN appeal_id integer) OWNER TO postgres;

--
-- Name: emp_appeals_for_week(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.emp_appeals_for_week(myid integer) RETURNS TABLE("╨Э╨╛╨╝╨╡╤А ╨╖╨░╨┤╨░╤З╨╕" integer, "╨Ф╨░╤В╨░ ╨╖╨░╤П╨▓╨║╨╕" date, "╨Р╨┤╤А╨╡╤Б" character varying, "╨Ф╨░╤В╨░ ╨▓╨╕╨╖╨╕╤В╨░" date, "╨Т╤А╨╡╨╝╤П ╨▓╨╕╨╖╨╕╤В╨░" character varying, "╨Ф╨╕╤Б╨┐╨╡╤В╤З╨╡╤А" character varying)
    LANGUAGE sql
    AS $$
select a.id, a.date, ad.city||', '||ad.street||', '||ad.build||', ╨┐. '||ad.ent||', ╨║╨▓. '||a.appart as "╨Р╨┤╤А╨╡╤Б",
		a.task_date, t.time, e.last_name||' '||e.first_name||' '||e.up_name as "╨Ф╨╕╤Б╨┐╨╡╤В╤З╨╡╤А"
from public.appeals a
join public.employees e
on e.id = a.manager_id
join public.addresses ad
on ad.id = a.address_id
join public.times t
on t.id = a.time_id
where date between date_trunc('week', current_date) 
		and current_timestamp 
and is_done = true and employee_id = myid;
$$;


ALTER FUNCTION public.emp_appeals_for_week(myid integer) OWNER TO postgres;

--
-- Name: old_appeal(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.old_appeal() RETURNS TABLE(address character varying, man character varying, client character varying, com text, task_date date, task_time character varying)
    LANGUAGE sql
    AS $$
SELECT a.city||', '||a.street||', '||a.build||', ╨┐. '||a.ent||', ╨║╨▓. '||ap.appart as "╨Р╨┤╤А╨╡╤Б",
		me.last_name||' '||me.first_name||' '||me.up_name as "╨Ф╨╕╤Б╨┐╨╡╤В╤З╨╡╤А",
		c.last_name||' '||c.first_name||' '||c.up_name||', ╤В╨╡╨╗. '||c.phone as "╨Ъ╨╗╨╕╨╡╨╜╤В",
		ap.comment, ap.task_date, t.time
FROM public.appeals ap
join public.addresses a
on a.id = ap.address_id
join public.employees me 
on me.id = ap.manager_id
join public.clients c
on c.id = ap.client_id
join public.times t
on t.id = ap.time_id
where date < date_trunc('week', current_date) and ap.employee_id is null;
$$;


ALTER FUNCTION public.old_appeal() OWNER TO postgres;

--
-- Name: security_constraint(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.security_constraint() RETURNS event_trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  RAISE EXCEPTION '╨г ╨▓╨░╤Б ╨╜╨╡╤В ╨┐╤А╨░╨▓ ╤Г╨┤╨░╨╗╤П╤В╤М ╤Н╤В╤Г ╤В╨░╨▒╨╗╨╕╤Ж╤Г';
END;
$$;


ALTER FUNCTION public.security_constraint() OWNER TO postgres;

--
-- Name: set_employee_to_appeal(integer, integer); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.set_employee_to_appeal(IN appeal_id integer, IN emp_id integer)
    LANGUAGE sql
    AS $$
update public.appeals
set employee_id = emp_id
where id = appeal_id;
$$;


ALTER PROCEDURE public.set_employee_to_appeal(IN appeal_id integer, IN emp_id integer) OWNER TO postgres;

--
-- Name: show_new_address(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.show_new_address() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    DECLARE
		new_row public.addresses%ROWTYPE;
	BEGIN
        SELECT *
		FROM public.addresses
		WHERE id = NEW.id
		into new_row;
		RAISE NOTICE '╨Ф╨╛╨▒╨░╨▓╨╗╨╡╨╜ ╨╜╨╛╨▓╤Л╨╣ ╨░╨┤╤А╨╡╤Б: %', new_row;
        RETURN NEW;
    END;
$$;


ALTER FUNCTION public.show_new_address() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: addresses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.addresses (
    id bigint NOT NULL,
    build character varying(255),
    city character varying(255),
    ent character varying(255),
    street character varying(255),
    latitude character varying(255),
    long character varying(255)
);


ALTER TABLE public.addresses OWNER TO postgres;

--
-- Name: addresses_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.addresses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.addresses_id_seq OWNER TO postgres;

--
-- Name: addresses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.addresses_id_seq OWNED BY public.addresses.id;


--
-- Name: appeals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appeals (
    id bigint NOT NULL,
    appart character varying(255),
    comment character varying(255),
    date timestamp without time zone,
    is_done boolean,
    task_date timestamp without time zone,
    address_id bigint,
    client_id bigint,
    contract_id bigint,
    employee_id bigint,
    manager_id bigint,
    time_id bigint
);


ALTER TABLE public.appeals OWNER TO postgres;

--
-- Name: appeals_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appeals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.appeals_id_seq OWNER TO postgres;

--
-- Name: appeals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appeals_id_seq OWNED BY public.appeals.id;


--
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients (
    id bigint NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone character varying(255),
    up_name character varying(255)
);


ALTER TABLE public.clients OWNER TO postgres;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clients_id_seq OWNER TO postgres;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id;


--
-- Name: contracts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contracts (
    id bigint NOT NULL,
    is_active boolean,
    appartments character varying(255),
    has_internet boolean,
    has_phone boolean,
    has_tv boolean,
    address_id bigint,
    client_id bigint
);


ALTER TABLE public.contracts OWNER TO postgres;

--
-- Name: contracts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contracts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contracts_id_seq OWNER TO postgres;

--
-- Name: contracts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contracts_id_seq OWNED BY public.contracts.id;


--
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    id bigint NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone character varying(255),
    up_name character varying(255),
    post_id bigint
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employees_id_seq OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employees_id_seq OWNED BY public.employees.id;


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: posts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.posts (
    id bigint NOT NULL,
    post_name character varying(255)
);


ALTER TABLE public.posts OWNER TO postgres;

--
-- Name: posts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.posts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.posts_id_seq OWNER TO postgres;

--
-- Name: posts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.posts_id_seq OWNED BY public.posts.id;


--
-- Name: times; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.times (
    id bigint NOT NULL,
    "time" character varying(255)
);


ALTER TABLE public.times OWNER TO postgres;

--
-- Name: usr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usr (
    id integer NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    employee_id bigint
);


ALTER TABLE public.usr OWNER TO postgres;

--
-- Name: usr_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usr_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usr_id_seq OWNER TO postgres;

--
-- Name: usr_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usr_id_seq OWNED BY public.usr.id;


--
-- Name: addresses id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses ALTER COLUMN id SET DEFAULT nextval('public.addresses_id_seq'::regclass);


--
-- Name: appeals id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals ALTER COLUMN id SET DEFAULT nextval('public.appeals_id_seq'::regclass);


--
-- Name: clients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients ALTER COLUMN id SET DEFAULT nextval('public.clients_id_seq'::regclass);


--
-- Name: contracts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contracts ALTER COLUMN id SET DEFAULT nextval('public.contracts_id_seq'::regclass);


--
-- Name: employees id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees ALTER COLUMN id SET DEFAULT nextval('public.employees_id_seq'::regclass);


--
-- Name: posts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts ALTER COLUMN id SET DEFAULT nextval('public.posts_id_seq'::regclass);


--
-- Name: usr id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr ALTER COLUMN id SET DEFAULT nextval('public.usr_id_seq'::regclass);


--
-- Name: addresses addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (id);


--
-- Name: appeals appeals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals
    ADD CONSTRAINT appeals_pkey PRIMARY KEY (id);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: contracts contracts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT contracts_pkey PRIMARY KEY (id);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- Name: times times_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.times
    ADD CONSTRAINT times_pkey PRIMARY KEY (id);


--
-- Name: usr uk_dfui7gxngrgwn9ewee3ogtgym; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr
    ADD CONSTRAINT uk_dfui7gxngrgwn9ewee3ogtgym UNIQUE (username);


--
-- Name: usr usr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr
    ADD CONSTRAINT usr_pkey PRIMARY KEY (id);


--
-- Name: clients check_client; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_client BEFORE INSERT ON public.clients FOR EACH ROW EXECUTE FUNCTION public.check_client();


--
-- Name: appeals check_done_appeal; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_done_appeal BEFORE UPDATE ON public.appeals FOR EACH ROW EXECUTE FUNCTION public.check_done_appeal();


--
-- Name: appeals check_taskdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_taskdate AFTER INSERT ON public.appeals FOR EACH ROW EXECUTE FUNCTION public.check_taskdate();


--
-- Name: addresses show_new_address; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER show_new_address AFTER INSERT ON public.addresses FOR EACH ROW EXECUTE FUNCTION public.show_new_address();


--
-- Name: contracts fk2sjtp98jjnbsq5wk6kjaj1gbq; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT fk2sjtp98jjnbsq5wk6kjaj1gbq FOREIGN KEY (address_id) REFERENCES public.addresses(id);


--
-- Name: employees fk3mxi6om2wmkx2o6w778va4uux; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fk3mxi6om2wmkx2o6w778va4uux FOREIGN KEY (post_id) REFERENCES public.posts(id);


--
-- Name: usr fk4ai1jlx9uhxqigmfhxoewmav1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usr
    ADD CONSTRAINT fk4ai1jlx9uhxqigmfhxoewmav1 FOREIGN KEY (employee_id) REFERENCES public.employees(id);


--
-- Name: appeals fk5w1c6qksctr0wnmr7pq921s0d; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals
    ADD CONSTRAINT fk5w1c6qksctr0wnmr7pq921s0d FOREIGN KEY (address_id) REFERENCES public.addresses(id);


--
-- Name: appeals fk6dh70gt28jwtdmgrhxvqmlnx0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals
    ADD CONSTRAINT fk6dh70gt28jwtdmgrhxvqmlnx0 FOREIGN KEY (time_id) REFERENCES public.times(id);


--
-- Name: appeals fkdtyf0nj1s9l7l8ltb1on4pilu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals
    ADD CONSTRAINT fkdtyf0nj1s9l7l8ltb1on4pilu FOREIGN KEY (contract_id) REFERENCES public.contracts(id);


--
-- Name: appeals fkfpr7jhbg4ch9wt10rc090kg8c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals
    ADD CONSTRAINT fkfpr7jhbg4ch9wt10rc090kg8c FOREIGN KEY (employee_id) REFERENCES public.employees(id);


--
-- Name: appeals fkq58wmljxnb8cxrxdr452xscs2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals
    ADD CONSTRAINT fkq58wmljxnb8cxrxdr452xscs2 FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- Name: contracts fkrqssit79jdlx2ch8ubajt6w4y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT fkrqssit79jdlx2ch8ubajt6w4y FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- Name: appeals fkt1bbiqm5n5nstoulst45og08p; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appeals
    ADD CONSTRAINT fkt1bbiqm5n5nstoulst45og08p FOREIGN KEY (manager_id) REFERENCES public.employees(id);


--
-- Name: security_constraint; Type: EVENT TRIGGER; Schema: -; Owner: postgres
--

CREATE EVENT TRIGGER security_constraint ON sql_drop
   EXECUTE FUNCTION public.security_constraint();


ALTER EVENT TRIGGER security_constraint OWNER TO postgres;

--
-- PostgreSQL database dump complete
--

