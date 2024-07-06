use teaFactory;


CREATE TABLE `User` (
                        `id` varchar(30) NOT NULL,
                        `name` varchar(40) DEFAULT NULL,
                        `password` varchar(10) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `Field` (
                         `field_id` varchar(30) NOT NULL,
                         `name` varchar(40) DEFAULT NULL,
                         `address` varchar(100) NOT NULL,
                         PRIMARY KEY (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `Employee` (
                            `employee_id` varchar(30) NOT NULL,
                            `name` varchar(40) DEFAULT NULL,
                            `contact` int(10) DEFAULT NULL,
                            `status` varchar(100) DEFAULT NULL,
                            `id` varchar(30) NOT NULL,
                            `field_id` varchar(30) NOT NULL,
                            PRIMARY KEY (`employee_id`),
                            FOREIGN KEY (`id`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                            FOREIGN KEY (`field_id`) REFERENCES `Field` (`field_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `Salary` (
                          `no` varchar(30) NOT NULL,
                          `amount` decimal(10,2) DEFAULT NULL,
                          `date` date DEFAULT NULL,
                          `employee_id` varchar(30) NOT NULL,
                          PRIMARY KEY (`no`),
                          FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `Harvest` (
                           `harvest_no` varchar(30) NOT NULL,
                           `qty` int(10) DEFAULT NULL,
                           `date` date DEFAULT NULL,
                           `field_id` varchar(30) NOT NULL,
                           PRIMARY KEY (`harvest_no`),
                           FOREIGN KEY (`field_id`) REFERENCES `Field` (`field_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




CREATE TABLE `Inventory` (
                             `category_id` varchar(30) NOT NULL,
                             `category_name` varchar(40) DEFAULT NULL,
                             `qty` int(10) DEFAULT NULL,
                             `unitPrice` decimal(10,2) DEFAULT NULL,
                             `harvest_no` varchar(30) NOT NULL,
                             PRIMARY KEY (`category_id`),
                             FOREIGN KEY (`harvest_no`) REFERENCES `Harvest` (`harvest_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `Customer` (
                            `customer_id` varchar(30) NOT NULL,
                            `name` varchar(40) DEFAULT NULL,
                            `email` varchar(30) NOT NULL,
                            `contact` int(10) DEFAULT NULL,
                            PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



CREATE TABLE `Orders` (
                          `order_id` varchar(30) NOT NULL,
                          `date` date DEFAULT NULL,
                          `customer_id` varchar(30) NOT NULL,
                          PRIMARY KEY (`order_id`),
                          FOREIGN KEY (`customer_id`) REFERENCES `Customer` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `OrderDetails` (
                                `order_id` varchar(255) NOT NULL,
                                `category_id` varchar(255) NOT NULL,
                                `qty` int(10) DEFAULT NULL,
                                `unitPrice` decimal(10,2) DEFAULT NULL,
                                PRIMARY KEY (`order_id`,`category_id`),
                                KEY `category_id` (`category_id`),
                                CONSTRAINT `OrderDetails_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `Orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `OrderDetails_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `Inventory` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;





