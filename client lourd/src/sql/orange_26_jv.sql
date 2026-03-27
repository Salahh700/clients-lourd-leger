drop database if exists orange_26_jv; 
create database orange_26_jv; 
use orange_26_jv;
 
create table client (
	idclient int (5) not null auto_increment, 
	nom varchar(50),
	prenom varchar(50),
	adresse varchar(50),
	email varchar(50),
	statut varchar(50),
	primary key(idclient) 
);
 
create table technicien (
	idtechnicien int (5) not null auto_increment, 
	nom varchar(50),
	prenom varchar(50),
	specialite varchar(50),
	email varchar(50),
	mdp varchar(200),
	primary key(idtechnicien) 
);
 
create table objet (
	idobjet int (5) not null auto_increment, 
	designation varchar(50),
	dateAchat date,
	etat text,
	idclient  int (5) not null,
	primary key(idobjet) , 
	foreign key(idclient) references client(idclient)
);
 
create table intervention (
	idinter int (5) not null auto_increment, 
	panne text,
	dateDepot date,
	prix float,
	idtechnicien  int (5) not null,
	idobjet int (5) not null,
	primary key(idinter) , 
	foreign key(idtechnicien) references technicien(idtechnicien),
	foreign key(idobjet) references objet(idobjet)
);

insert into technicien values(null,"Florien","Val","BOX","a@gmail.com","123");