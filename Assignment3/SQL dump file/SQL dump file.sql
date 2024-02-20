
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema schooldb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema schooldb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS schooldb;
CREATE SCHEMA IF NOT EXISTS `schooldb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `schooldb` ;

-- -----------------------------------------------------
-- Table `schooldb`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schooldb`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `stock` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `schooldb`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schooldb`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idClient` INT NULL DEFAULT NULL,
  `idProduct` INT NULL DEFAULT NULL,
  `quantity` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `idClient` (`idClient` ASC) VISIBLE,
  INDEX `idProduct` (`idProduct` ASC) VISIBLE,
  CONSTRAINT `orders_ibfk_1`
    FOREIGN KEY (`idClient`)
    REFERENCES `schooldb`.`client` (`id`),
  CONSTRAINT `orders_ibfk_2`
    FOREIGN KEY (`idProduct`)
    REFERENCES `schooldb`.`product` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into client values (1, 'Pop Ionut', 'Cluj-Napoca, Cluj', 'pop.ionut@yahoo.com'), (2, 'Matei Andrei', 'Cluj-Napoca, Cluj', 'matei.andrei20@yahoo.com'), 
(3, 'Caba Stefan', 'Iasi', 'caba.stefan@yahoo.com'), (4, 'Alexandru Vasile', 'Iasi', 'alex.vasile@yahoo.com'); 
insert into product values (1, 'Water', 10, 50), (2, 'Chocolate', 8, 20), (3, 'Bread', 5, 40), 
(4, 'Chips', 12, 100), (5, 'Soda', 14, 60);
insert into orders values (1, 1, 2, 10);