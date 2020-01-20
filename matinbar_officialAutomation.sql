-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 20, 2020 at 09:24 AM
-- Server version: 10.3.18-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `matinbar_officialAutomation`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `personalId` int(20) NOT NULL,
  `personalIdmaster` int(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(40) NOT NULL,
  `nationalId` varchar(10) NOT NULL,
  `phonenumber` varchar(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL,
  `department` varchar(20) NOT NULL,
  `address` text NOT NULL,
  `forgetcode` varchar(8) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `personalId`, `personalIdmaster`, `password`, `firstname`, `lastname`, `nationalId`, `phonenumber`, `email`, `role`, `department`, `address`, `forgetcode`) VALUES
(1, 9537063, 9537063, '9537063', 'امیر مهدی', 'نصرآبادی', '0640548611', '09032159440', 'error97@yahoo.com', 'master', 'حراست', 'زاهدان-خیابان دانشگاه-دانشگاه25-خوابگاه پسرانه رضوان', '0f52cnks'),
(2, 9522443, 9537063, '9522443', 'امین الله', 'جانی پور', '3060432678', '09140672131', 'janipooraminallah@gmail.com', 'employee', 'باجه', 'زاهدان-دانشگاه 25--دانشگاه سیستان و بلوچستان - خوابگاه پسرانه رضوان', 'd5853db'),
(3, 9542853, 9537063, '9542853', 'مصطفی', 'درویشی', '3611183547', '09154656151', 'mostafadarvishi1234567890@gmail.com', 'guard', '', 'معلم -معلم 3 میلان 4 -بعد از تقاطع دومین خانه از سمت راست', 'dkmc239w'),
(4, 9525463, 9537063, '9525463', 'متین', 'دریانوش براهویی', '3611082521', '09363303477', 'matinbarahouei@yahoo.com', 'sportman', '', 'زاهدان -خیابان بدر-بدر 33-درب سمت راست میلان', 'f23xbne9'),
(5, 9595955, 9537063, '9595955', 'آقای', 'راننده', '3612345678', '09309309330', 'fsjklfjd@gmail.com', 'driver', 'none', 'زاهدان', '23974ued');

-- --------------------------------------------------------

--
-- Table structure for table `leaveArchive`
--

CREATE TABLE `leaveArchive` (
  `id` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `personalIdemployee` int(20) NOT NULL,
  `personalIdmaster` int(20) NOT NULL,
  `leavetype` varchar(10) NOT NULL,
  `starttime` varchar(20) NOT NULL,
  `timeleave` varchar(20) NOT NULL,
  `startdate` varchar(20) NOT NULL,
  `description` text NOT NULL,
  `descriptionLeave` text NOT NULL,
  `currentdate` text NOT NULL,
  `statusArchive` varchar(10) NOT NULL,
  `status` varchar(5) NOT NULL,
  `progressstatus` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `leaveArchive`
--

INSERT INTO `leaveArchive` (`id`, `fullname`, `personalIdemployee`, `personalIdmaster`, `leavetype`, `starttime`, `timeleave`, `startdate`, `description`, `descriptionLeave`, `currentdate`, `statusArchive`, `status`, `progressstatus`) VALUES
(6, 'امین الله جانی پور', 9522443, 9537063, 'اداری', '00:10:30', '01:00:00', '1398/10/29', 'تائید شد', '', '1398/10/29', 'true', 'true', 3),
(5, 'امین الله جانی پور', 9522443, 9537063, 'اداری', '00:14:20', '01:00:00', '1398/10/17', '', '', '1398/10/17', 'false', 'false', 3),
(7, 'امین الله جانی پور', 9522443, 9537063, 'اداری', '00:00:22', '00:18:00', '1398/10/30', 'تائید شد', '', '1398/10/30', 'true', 'true', 3),
(8, 'امین الله جانی پور', 9522443, 9537063, 'اداری', '00:00:22', '00:18:00', '1398/10/30', 'تائید شد', '', '1398/10/30', 'true', 'true', 3),
(9, 'امین الله جانی پور', 9522443, 9537063, 'اداری', '00:00:23', '00:18:00', '1398/10/30', 'تائید شد', '', '1398/10/30', 'true', 'true', 3),
(10, 'امین الله جانی پور', 9522443, 9537063, 'اداری', '00:00:22', '00:18:00', '1398/10/30', '', '', '1398/10/30', 'false', 'false', 3);

-- --------------------------------------------------------

--
-- Table structure for table `reqLeave`
--

CREATE TABLE `reqLeave` (
  `id` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `personalIdemployee` int(20) NOT NULL,
  `personalIdmaster` int(20) NOT NULL,
  `leavetype` varchar(10) NOT NULL,
  `starttime` varchar(20) NOT NULL,
  `timeleave` varchar(20) NOT NULL,
  `startdate` varchar(20) NOT NULL,
  `currentdate` varchar(12) NOT NULL,
  `descriptionLeave` text NOT NULL,
  `progressstatus` int(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `reqLeave`
--

INSERT INTO `reqLeave` (`id`, `fullname`, `personalIdemployee`, `personalIdmaster`, `leavetype`, `starttime`, `timeleave`, `startdate`, `currentdate`, `descriptionLeave`, `progressstatus`) VALUES
(126, 'امین الله جانی پور', 9522443, 9537063, 'اداری', '00:10:44', '01:00:00', '1398/10/30', '1398/10/30', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `sport`
--

CREATE TABLE `sport` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `type` varchar(15) NOT NULL,
  `time` varchar(20) NOT NULL,
  `date` varchar(100) NOT NULL,
  `personalid` text NOT NULL,
  `capacity` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL DEFAULT 'true'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sport`
--

INSERT INTO `sport` (`id`, `code`, `type`, `time`, `date`, `personalid`, `capacity`, `status`) VALUES
(35, '867beda4', 'volleyball', '12:12-12:12', '{\"day\":\"sun-\"}', '{\"sun\":\"\"}', '{\"sun\":\"12\"}', '{\"sun\":\"true\"}'),
(36, '765068cb', 'football', '03:04-07:04', '{\"day\":\"sat-sun-mon-tue-\"}', '{\"sat\":\"\",\"sun\":\"\",\"mon\":\"9522443>\\u0627\\u0645\\u06cc\\u0646 \\u0627\\u0644\\u0644\\u0647 \\u062c\\u0627\\u0646\\u06cc \\u067e\\u0648\\u0631-\",\"tue\":\"\"}', '{\"sat\":\"12\",\"sun\":\"12\",\"mon\":11,\"tue\":\"12\"}', '{\"sat\":\"true\",\"sun\":\"true\",\"mon\":\"true\",\"tue\":\"true\"}');

-- --------------------------------------------------------

--
-- Table structure for table `transport`
--

CREATE TABLE `transport` (
  `id` int(11) NOT NULL,
  `personalId` varchar(10) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `shift` varchar(10) NOT NULL,
  `date` varchar(10) NOT NULL,
  `address` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transport`
--

INSERT INTO `transport` (`id`, `personalId`, `fullname`, `shift`, `date`, `address`) VALUES
(3, '9537063', 'امیر مهدی نصرآبادی', 'morning', 'sun', 'Fy'),
(4, '9537063', 'امیر مهدی نصرآبادی', 'morning', 'mon', 'Fy'),
(5, '9537063', 'امیر مهدی نصرآبادی', 'afternoon', 'thu', 'بدر 33 '),
(6, '9537063', 'امیر مهدی نصرآبادی', 'afternoon', 'sun', 'ذذ'),
(7, '9537063', 'امیر مهدی نصرآبادی', 'afternoon', 'mon', 'زلاذ'),
(8, '9537063', 'امیر مهدی نصرآبادی', 'morning', 'tue', 'Morns'),
(9, '9522443', 'امین الله جانی پور', 'morning', 'sat', 'Xg'),
(10, '9522443', 'امین الله جانی پور', 'morning', 'tue', 'آدرس محل خانه ام '),
(11, '9522443', 'امین الله جانی پور', 'morning', 'mon', 'Khfghbffg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `leaveArchive`
--
ALTER TABLE `leaveArchive`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reqLeave`
--
ALTER TABLE `reqLeave`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sport`
--
ALTER TABLE `sport`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transport`
--
ALTER TABLE `transport`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `leaveArchive`
--
ALTER TABLE `leaveArchive`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `reqLeave`
--
ALTER TABLE `reqLeave`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;

--
-- AUTO_INCREMENT for table `sport`
--
ALTER TABLE `sport`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT for table `transport`
--
ALTER TABLE `transport`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
