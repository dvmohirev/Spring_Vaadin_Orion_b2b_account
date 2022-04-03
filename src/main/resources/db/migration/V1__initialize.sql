drop table if exists products cascade;
create table products (id bigserial, title varchar(255), description varchar(5000), price numeric(8, 2), primary key(id));
insert into products
(title, description, price) values
/*Товары для Еды*/
('Cheese', 'Fresh cheese', 320.0),
('Milk', 'Fresh milk', 80.0),
('Apples', 'Fresh apples', 80.0),
('Bread', 'Fresh bread', 30.0),
/*Товары для Электроники*/
('Iphone 14', 'New model of Iphone', 100000.0),
('Iphone 13', 'Thirdteen model of Iphone', 70000.0),
('Playstation 5', 'New Playstation', 80000.0),
('Playstation 4', 'Old Playstation', 35000.0),
/*Товары для Транспорт*/
('Электробайк', 'Электровелосипед с двойной цельной алюминиевой рамой.', 115292.0),
('Кроссовый мотоцикл Arcadaios', 'Сниженная масса и увеличенная мощность для комфортной езды и трюков.', 190000.0),
('Горный велосипед PROF', 'Горный велосипед PROF для профессионалов и ценителей умной техники.', 11292.0),
('Дорожный мотоцикл Amintas Z1000RW', 'Благодаря усовершенствованным тормозным системам мотоцикл разгоняется за 4 секунды и с легкостью остановится без потери управляемости.', 180000.0),
/*Товары для Мебель*/
('Полка навесная', 'Навесной модуль с закрытым и открытым типом хранения.', 5000.0),
('Шкаф двустворчатый', 'Простой и практичный шкаф-гардероб.', 20700.0),
('Пенал с открытыми полками', 'Модуль сочетает в себе открытое и закрытое хранение.', 10000.0),
('Тумба напольная', 'Удобный и лаконичный модуль может разместить на себе телевизор с большой диагональю.', 5000.0),
/*Товары для Обувь*/
('Сандалии для мальчиков', 'Поперечные ремешки на липучках позволят быстро надевать обувь.', 2000.0),
('Кроссовки для девочек', 'Кожаные кроссовки обладают высокой прочностью и хорошими водоотталкивающими свойствами.', 5000.0),
('Ботинки для девочек', 'Обувь сделана по специальной технологии, которая повторяет естественные изгибы стопы и обеспечивает плотное прилегание.', 3000.0),
('Ботинки для мальчиков', 'Прокладка между подошвой и стелькой сделана на основе шерсти, а сама стелька — 100% шерсть.', 5000.0),
/*Товары для Одежда*/
('Жилет для мальчиков', 'Простеганный жилет сохраняет тепло и не задерживает влагу.', 2500.0),
('Брюки прямые мужские', 'Ищите универсальные брюки для создания разных образов?', 3800.0),
('Джинсы для мальчиков', 'Джинсы для мальчиков — универсальная одежда в гардеробе.', 2450.0),
('Мужской свитшот с принтом', 'Свитшот — удобная верхняя одежда.', 1200.0),
/*Товары для Спортивные товары*/
('Гексагональные гантели Fairs', 'Гексагональные гантели Fairs созданы для комфортных силовых тренировок как для профессионалов, так и любителей.', 4000.0),
('Гандбольный мяч Ferics', 'Мяч Shison выбирают профессиональные игроки.', 1699.0),
('Бейсбольный мяч Shison', 'text', 1521.0),
('Детский мяч Kasa', 'Мяч отлично развивает координацию малышей и способствует общему физическому развитию.', 699.0);

drop table if exists categories cascade;
create table categories (id bigserial, title varchar(255), primary key(id));
insert into categories
(title) values
('Food'),
('Elektronika'),
('Transport'),
('Mebel'),
('Shoes'),
('Clothes'),
('Sports');

drop table if exists products_categories cascade;
create table products_categories (product_id bigint not null, category_id bigint not null, primary key(product_id, category_id),
foreign key (product_id) references products(id), foreign key (category_id) references categories(id));
insert into products_categories (product_id, category_id) values
(1, 1), (2, 1), (3, 1), (4, 1),
(5, 2), (6, 2), (7, 2), (8, 2),
(9, 3), (10, 3), (11, 3), (12, 3),
(13, 4), (14, 4), (15, 4), (16, 4),
(17, 5), (18, 5), (19, 5), (20, 5),
(21, 6), (22, 6), (23, 6), (24, 6),
(25, 7), (26, 7), (27, 7), (28, 7);

drop table if exists users;
create table users (
  id                    bigserial,
  phone                 VARCHAR(30) not null UNIQUE,
  password              VARCHAR(80) not null,
  email                 VARCHAR(50) UNIQUE,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  age                   INT,
  money                 INT,
  PRIMARY KEY (id)
);

drop table if exists roles;
create table roles (
  id                    serial,
  name                  VARCHAR(50) not null,
  primary key (id)
);

drop table if exists users_roles;
create table users_roles (
  user_id               INT NOT NULL,
  role_id               INT NOT NULL,
  primary key (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

insert into roles (name)
values
('ROLE_CUSTOMER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

insert into users (phone, password, first_name, last_name, email, age, money)
values
('123456','123','admin','admin','admin@gmail.com', 20, 123123);

insert into users (phone, password, first_name, last_name, email, age, money)
values
('123','123','customer','customer','customer@gmail.com', 20, 0);

insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2),
(1, 3),
(2, 1);

drop table if exists orders cascade;
create table orders (id bigserial, user_id bigint not null, price numeric(8, 2) not null, status varchar(32), address varchar (255) not null, phone_number varchar(30) not null, primary key(id), constraint fk_user_id foreign key (user_id) references users (id));

drop table if exists items cascade;
create table items (id bigserial, product_id bigint not null, quantity int, price numeric(8, 2), primary key(id), constraint fk_prod_id foreign key (product_id) references products (id));

drop table if exists orders_items cascade;
create table order_items(id bigserial, order_id bigint not null, item_id bigint not null, primary key(id), constraint fk_order_items_id foreign key(order_id) references orders(id), constraint fk_item_id foreign key (item_id) references items(id));

