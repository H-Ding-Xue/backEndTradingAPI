CREATE TABLE IF NOT EXISTS `prices` (
  `ticker_symbol` varchar(10)  PRIMARY KEY,
  `bid_price` numeric(20,10) NOT NULL,
  `ask_price` numeric(20,10) NOT NULL,
  `created_at` date DEFAULT NULL,
   `created_by` varchar(20) DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `assets` (
  `user_id` varchar(20)   NOT NULL,
  `ticker_symbol` varchar(10) NOT NULL,
  `ticker_quantity` numeric(20,10) NOT NULL,
  PRIMARY KEY(user_id, ticker_symbol)
);

CREATE TABLE IF NOT EXISTS `trades` (
   `trade_id` int AUTO_INCREMENT  PRIMARY KEY,
  `trade_type` varchar(4) NOT NULL,
  `ticker_symbol` varchar(10) NOT NULL,
  `trade_quantity` numeric(20,10) NOT NULL,
    `average_price` numeric(20,10) NOT NULL,
   `traded_at` date NOT NULL,
    `traded_by` varchar(20) NOT NULL
);