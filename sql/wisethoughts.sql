-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 20, 2020 at 08:38 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wisethoughts`
--
CREATE DATABASE IF NOT EXISTS `wisethoughts` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `wisethoughts`;

-- --------------------------------------------------------

--
-- Table structure for table `admin_details`
--

CREATE TABLE `admin_details` (
  `id` int(11) NOT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL,
  `role` varchar(12) COLLATE utf8_bin NOT NULL,
  `username` varchar(64) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `admin_details`
--

INSERT INTO `admin_details` (`id`, `password`, `role`, `username`) VALUES
(2, '$2a$10$h9e8NNl2XopLu90qc0moR.dAjUVmxeUZjEvMzULhB0Cg6imKBGaL6', 'ROLE_ADMIN', 'nikola');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(18),
(18);

-- --------------------------------------------------------

--
-- Table structure for table `wise_thought`
--

CREATE TABLE `wise_thought` (
  `wisethoughtid` int(11) NOT NULL,
  `adminid` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(300) COLLATE utf8_bin NOT NULL,
  `text` varchar(20000) COLLATE utf8_bin NOT NULL,
  `title` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `wise_thought`
--

INSERT INTO `wise_thought` (`wisethoughtid`, `adminid`, `date`, `description`, `text`, `title`) VALUES
(13, 2, '2020-01-15 23:22:54', 'ovo je zamisljeno da bude kratki opis', 'Ko rano rano taj prvi doruckuje. ', 'ovo je zamisljeno da bude kratki naslov'),
(15, 2, '2020-01-15 23:24:09', 'ovo je zamisljeno da bude kratki opis', 'Ko rano rano taj prvi doruckuje. ', 'ovo je zamisljeno da bude kratki naslov'),
(16, 2, '2020-01-15 23:24:10', 'ovo je zamisljeno da bude kratki opis', 'Ko rano rano taj prvi doruckuje. ', 'ovo je zamisljeno da bude kratki naslov'),
(17, 2, '2020-01-15 23:26:14', 'ovo je zamisljeno da bude kratki opis', 'Ko rano rano taj prvi doruckuje. ', 'ovo je zamisljeno da bude kratki naslov');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_details`
--
ALTER TABLE `admin_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wise_thought`
--
ALTER TABLE `wise_thought`
  ADD PRIMARY KEY (`wisethoughtid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
