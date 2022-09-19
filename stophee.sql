-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 19, 2022 at 11:37 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stophee`
--

-- --------------------------------------------------------

--
-- Table structure for table `detailtransaction`
--

CREATE TABLE `detailtransaction` (
  `TransactionID` int(11) NOT NULL,
  `ProductID` int(11) NOT NULL,
  `Quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detailtransaction`
--

INSERT INTO `detailtransaction` (`TransactionID`, `ProductID`, `Quantity`) VALUES
(3, 1, 4),
(4, 1, 4),
(5, 1, 1),
(6, 1, 2),
(7, 1, 3),
(10, 1, 4),
(11, 1, 2),
(12, 22, 8),
(13, 1, 2),
(14, 1, 2),
(15, 19, 2),
(15, 21, 3),
(15, 25, 5);

-- --------------------------------------------------------

--
-- Table structure for table `headertransaction`
--

CREATE TABLE `headertransaction` (
  `ID` int(11) NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  `TransactionDate` date DEFAULT NULL,
  `PaymentType` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `headertransaction`
--

INSERT INTO `headertransaction` (`ID`, `UserID`, `TransactionDate`, `PaymentType`) VALUES
(1, 2, '2022-01-10', 'Cash'),
(2, 2, '2022-01-10', 'Cash'),
(3, 2, '2022-01-10', 'Cash'),
(4, 2, '2022-01-10', 'Cash'),
(5, 2, '2022-01-10', 'Cash'),
(6, 2, '2022-01-10', 'Cash'),
(7, 2, '2022-01-10', 'Cash'),
(8, 2, '2022-01-10', 'Cash'),
(9, 2, '2022-01-10', 'Cash'),
(10, 2, '2022-01-11', 'Cash'),
(11, 2, '2022-01-11', 'Cash'),
(12, 2, '2022-01-18', 'Debit/Credit'),
(13, 2, '2022-09-05', 'Cash'),
(14, 2, '2022-09-05', 'Debit/Credit'),
(15, 2, '2022-09-05', 'Debit/Credit');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `ID` int(11) NOT NULL,
  `ProductTypeID` int(11) DEFAULT NULL,
  `ProductName` varchar(255) DEFAULT NULL,
  `ProductPrice` int(11) DEFAULT NULL,
  `ProductQuantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`ID`, `ProductTypeID`, `ProductName`, `ProductPrice`, `ProductQuantity`) VALUES
(1, 1, 'Fried Rice', 8000, 10),
(15, 3, 'Cheetos', 10000, 10),
(19, 4, 'Spiderman', 40000, 8),
(21, 2, 'Sprite', 8000, 7),
(22, 5, 'Surya', 20000, 10),
(24, 2, 'Energen', 5000, 10),
(25, 6, 'Duckbill', 10000, 5);

-- --------------------------------------------------------

--
-- Table structure for table `producttype`
--

CREATE TABLE `producttype` (
  `ID` int(11) NOT NULL,
  `ProductTypeName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `producttype`
--

INSERT INTO `producttype` (`ID`, `ProductTypeName`) VALUES
(1, 'Food'),
(2, 'Drinks'),
(3, 'Snacks'),
(4, 'Toy'),
(5, 'Rokok'),
(6, 'Mask'),
(7, 'Bottle');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Phone` varchar(255) DEFAULT NULL,
  `Gender` varchar(255) DEFAULT NULL,
  `Role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `Name`, `Email`, `Password`, `Phone`, `Gender`, `Role`) VALUES
(1, 'Admin', 'admin@mail.com', '123123', '123123123123', 'Male', 'Admin'),
(2, 'Hadi Handoyo', 'hadihandoyo21@gmail.com', '123321', '081277334237', 'Male', 'user'),
(3, 'Angelia CJ', 'angel@mail.com', 'angel123', '081288889999', 'Female', 'user'),
(4, 'Hadi', 'hadihandoyo@mail.com', 'hadi123', '081277334237', 'Male', 'user'),
(5, 'elvin', 'elvin@mail.com', 'el1234', '123123123132', 'Male', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD PRIMARY KEY (`TransactionID`,`ProductID`),
  ADD KEY `FK_Product` (`ProductID`);

--
-- Indexes for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_User` (`UserID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_ProductType` (`ProductTypeID`);

--
-- Indexes for table `producttype`
--
ALTER TABLE `producttype`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `headertransaction`
--
ALTER TABLE `headertransaction`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `producttype`
--
ALTER TABLE `producttype`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detailtransaction`
--
ALTER TABLE `detailtransaction`
  ADD CONSTRAINT `FK_Product` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ID`),
  ADD CONSTRAINT `FK_Transaction` FOREIGN KEY (`TransactionID`) REFERENCES `headertransaction` (`ID`);

--
-- Constraints for table `headertransaction`
--
ALTER TABLE `headertransaction`
  ADD CONSTRAINT `FK_User` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK_ProductType` FOREIGN KEY (`ProductTypeID`) REFERENCES `producttype` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
