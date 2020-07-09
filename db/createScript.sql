-- MySQL Script generated by MySQL Workbench
-- Thu Jul  9 22:01:22 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema knihovna
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema knihovna
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `knihovna` DEFAULT CHARACTER SET utf8 ;
USE `knihovna` ;

-- -----------------------------------------------------
-- Table `knihovna`.`ctenar`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `knihovna`.`ctenar` ;

CREATE TABLE IF NOT EXISTS `knihovna`.`ctenar` (
  `ctenar_id` INT NOT NULL AUTO_INCREMENT,
  `ctenar_jmeno` VARCHAR(45) NOT NULL,
  `ctenar_prijmeni` VARCHAR(45) NOT NULL,
  `ctenar_email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ctenar_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `knihovna`.`oblast_zamereni`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `knihovna`.`oblast_zamereni` ;

CREATE TABLE IF NOT EXISTS `knihovna`.`oblast_zamereni` (
  `oblast_id` INT NOT NULL AUTO_INCREMENT,
  `oblast_nazev` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`oblast_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `knihovna`.`autor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `knihovna`.`autor` ;

CREATE TABLE IF NOT EXISTS `knihovna`.`autor` (
  `autor_id` INT NOT NULL AUTO_INCREMENT,
  `autor_kod` VARCHAR(10) NOT NULL,
  `autor_jmeno` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`autor_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `knihovna`.`kniha`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `knihovna`.`kniha` ;

CREATE TABLE IF NOT EXISTS `knihovna`.`kniha` (
  `kniha_id` INT NOT NULL AUTO_INCREMENT,
  `kniha_nazev` VARCHAR(45) NOT NULL,
  `kniha_oblast_id` INT NOT NULL,
  `kniha_autor_id` INT NOT NULL,
  PRIMARY KEY (`kniha_id`, `kniha_oblast_id`, `kniha_autor_id`),
  INDEX `fk_kniha_oblast_zamereni_idx` (`kniha_oblast_id` ASC) ,
  INDEX `fk_kniha_autor1_idx` (`kniha_autor_id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `knihovna`.`vypujcka`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `knihovna`.`vypujcka` ;

CREATE TABLE IF NOT EXISTS `knihovna`.`vypujcka` (
  `vypujcka_id` INT NOT NULL AUTO_INCREMENT,
  `vypujcka_datum_pujceni` DATE NOT NULL,
  `vypujcka_datum_vraceni` DATE NULL,
  `vypujcka_predpokladane_datum_vraceni` DATE NOT NULL,
  `vypujcka_kniha_id` INT NOT NULL,
  `vypujcka_ctenar_id` INT NOT NULL,
  PRIMARY KEY (`vypujcka_id`, `vypujcka_kniha_id`, `vypujcka_ctenar_id`),
  INDEX `fk_vypujcka_kniha1_idx` (`vypujcka_kniha_id` ASC) ,
  INDEX `fk_vypujcka_ctenar1_idx` (`vypujcka_ctenar_id` ASC) )
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
